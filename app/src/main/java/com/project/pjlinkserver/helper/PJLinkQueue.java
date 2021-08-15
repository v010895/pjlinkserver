package com.project.pjlinkserver.helper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PJLinkQueue {
  private Queue<String> commandQueue;
  public PJLinkQueue(){
      commandQueue = new LinkedList<>();

  }

  private class Worker implements Runnable{
    @Override
    public void run() {
      while(true)
      {
        if(!commandQueue.isEmpty())
        {

        }
      }
    }
  }
}
