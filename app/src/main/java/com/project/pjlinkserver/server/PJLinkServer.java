package com.project.pjlinkserver.server;

import android.util.Log;

import com.project.pjlinkserver.constant.Status;
import com.project.pjlinkserver.entity.ServerStatus;
import com.project.pjlinkserver.repository.ServerRepository;
import com.project.pjlinkserver.tools.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.regex.Pattern;

import androidx.annotation.WorkerThread;

public class PJLinkServer {
  private static final String TAG = "myDebug";
  private static final int PORT = 4352;
  private SearchStatusListener mSearchStatusListner;
  private ServerRepository mServerRepository;
  private HandleSearchThread mHandleSearchThread;
  private CommandHandleThread mCommandHandleThread;
  private StringBuilder searchStatusBuilder;

  public PJLinkServer() {
	mHandleSearchThread = new HandleSearchThread(PORT);
	mCommandHandleThread = new CommandHandleThread(PORT);
	searchStatusBuilder = new StringBuilder();
	mHandleSearchThread.start();
	mCommandHandleThread.start();
  }

  public void setRepository(ServerRepository serverRepository) {
	this.mServerRepository = serverRepository;
  }

  public void setSearchStatusListner(SearchStatusListener listener) {
	mSearchStatusListner = listener;
  }

  private void appendSearchStatus(String status) {
	searchStatusBuilder.append(status);
  }

  private class HandleSearchThread extends Thread {
	private int mPort;
	private byte[] buffer;
	private static final String SEND = "%2SRCH";
	private final byte[] SEND_COMMAND = new byte[]{(byte) 0x25, (byte) 0x32, (byte) 0x53, (byte) 0x52, (byte) 0x43, (byte) 0x48, (byte) 0x0d};
	private static final String RESPONSE = "%2ACKN=40:4e:36:Ce:e5:9c";
	private int counter = 0;
	private DatagramSocket server;

	public HandleSearchThread(int port) {
	  mPort = port;
	  buffer = new byte[1024];
	}

	@Override
	public void run() {
	  while (true) {
		try {
		  server = new DatagramSocket(mPort);
		  DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		  Log.i(TAG, "Waiting client search command");
		  server.receive(receivePacket);
		  Log.i(TAG, "Get client search command");
		  String command = new String(receivePacket.getData(), 0, receivePacket.getLength());
		  Log.i(TAG, "Receive : " + command);
		  appendSearchStatus("Receive:" + command + "\n");
		  String hexString = StringUtils.StringToHexString(RESPONSE);
		  byte[] hexBytes = StringUtils.HexStringToByte(hexString);
		  DatagramPacket sendPacket = new DatagramPacket(hexBytes, 0, hexBytes.length, receivePacket.getAddress(), mPort);
		  server.send(sendPacket);
		  appendSearchStatus("Send:" + RESPONSE + "\n");
		  String status = searchStatusBuilder.toString();
		  if (mSearchStatusListner != null) {
			mSearchStatusListner.updateStatus(status);
		  }
		} catch (IOException exception) {
		  Log.i(TAG, exception.getMessage());
		} finally {
		  server.close();
		}
	  }
	}
  }

  private class CommandHandleThread extends Thread {
	private ServerSocket mServer;
	private Socket mSocket;
	private boolean serverInit = false;
	private byte[] commandBuffer;
	private BufferedReader socketReader;
	private PrintWriter socketWriter;

	public CommandHandleThread(int port) {
	  commandBuffer = new byte[136];
	  try {
		mServer = new ServerSocket(port);
		serverInit = true;
	  } catch (IOException exception) {

	  }
	}

	@Override
	public void run() {
	  if (serverInit) {
		try {
		  while (true) {
			mSocket = mServer.accept();
			Log.i(TAG, "Receive client");
			socketWriter = new PrintWriter(mSocket.getOutputStream());
			String greeting = StringUtils.StringToHexString("PJLINK 0");
			socketWriter.print("PJLINK 0\r");
			socketWriter.flush();
			Log.i(TAG, "greeting finish");
			socketReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			String command = socketReader.readLine();
			Log.i(TAG, "start read...");
			Log.i(TAG, "read command " + command);
			parseCommand(command);
			mSocket.close();
		  }
		} catch (IOException exception) {
		  Log.i(TAG, exception.getMessage());
		}
	  }
	}


	private void parseCommand(String command) {
	  if (command.length() < 7 || socketWriter == null)
		return;
	  String instruction = command.substring(2, 6);
	  switch (instruction) {
		case "POWR":
		  commandPOWR(command);
		  break;
		case "INPT":
		  commandINPT(command);
		  break;
		case "AVMT":
		  commandAVMT(command);
		  break;

	  }
	}
	private void commandAVMT(String command) {
	  String cls = command.substring(1, 2);
	  ServerStatus status = mServerRepository.getStatus(1);
	  String response = "%1AVMT=";
	  Pattern patternTen = Pattern.compile("[1-3]");
	  Pattern patternUnit = Pattern.compile("[0-1]");
	  if(command.endsWith("?"))
	  {
	    response = response + status.getAvmtStatus() + "\r";
	    socketWriter.print(response);
	    socketWriter.flush();
	  }else{
	    if(command.length() ==9)
		{
		  String tenString = String.valueOf(command.charAt(7));
		  String unitString = String.valueOf(command.charAt(8));
		  if(patternTen.matcher(tenString).find() && patternUnit.matcher(unitString).find())
		  {
		    response = response + status.getAvmt() + "\r";
		    Log.i(TAG,response);
		    sendResponse(response);
		    String avmtValue = tenString+unitString;
		    status.setAvmtStatus(avmtValue);
		    mServerRepository.updateStatus(status);
		  }
		}
	  }
	}
	private void commandPOWR(String command) {
	  String cls = command.substring(1, 2);
	  ServerStatus status = mServerRepository.getStatus(1);
	  String response;
	  if (cls.equals("1")) {
		response = "%1POWR=";
	  } else {
		response = "%2POWR=";
	  }
	  if (command.endsWith("?")) {
		response = response + status.getPowerStatus() + "\r";
		sendResponse(response);
	  } else if (command.endsWith("1")) {
		response = response + status.getPower() + "\r";
		sendResponse(response);
		status.setPowerStatus(Status.POWER_LAMPON);
		mServerRepository.updateStatus(status);
	  } else if (command.endsWith("0")) {
		response = response + status.getPower() + "\r";
		sendResponse(response);
		status.setPowerStatus(Status.POWER_STANDBY);
		mServerRepository.updateStatus(status);
	  }

	}
	private void commandINPT(String command) {
	  String cls = command.substring(1, 2);
	  ServerStatus status = mServerRepository.getStatus(1);
	  String response;
	  Pattern patternTen;
	  Pattern patternUnit;
	  if(cls.equals("1"))
	  {
	    response = "%1INPT=";
	    patternTen = Pattern.compile("[1-6]");
	    patternUnit = Pattern.compile("[1-9]");
	  }else{
	    response = "%2INPT=";
	    patternTen = Pattern.compile("[1-6]");
	    patternUnit = Pattern.compile("[1-9]|[A-Z]");
	  }
	  if (command.endsWith("?")) {
		response = response + status.getInputStatus() + "\r";
		sendResponse(response);
	  } else {
		if (command.length() == 9) {
		  String tenString = String.valueOf(command.charAt(7));
		  String unitString = String.valueOf(command.charAt(8));
		  if (patternTen.matcher(tenString).find() && (patternUnit.matcher(unitString).find())) {
			response = response + status.getInput() + "\r";
			sendResponse(response);
			status.setInputStatus(tenString + unitString);
			mServerRepository.updateStatus(status);
		  }

		}
	  }
	}


	private void sendResponse(String response) {
	  if (socketWriter != null) {
		socketWriter.print(response);
		socketWriter.flush();
	  }
	}
  }

  public interface SearchStatusListener {
	void updateStatus(String status);
  }
}
