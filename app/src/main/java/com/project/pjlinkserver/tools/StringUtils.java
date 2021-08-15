package com.project.pjlinkserver.tools;


import android.util.Log;

public class StringUtils {


  public static String StringToHexString(String s)
  {
    StringBuilder builder = new StringBuilder();
    for(int i=0; i< s.length(); i++)
    {
      int chr = s.charAt(i);
      builder.append(Integer.toHexString(chr));
    }
    return builder.toString();
  }
  public static byte[] HexStringToByte(String s)
  {
      int length = s.length()/2+1; //+1 for CR
      char[] hex = s.toCharArray();
      byte[] result = new byte[length];
      for(int i=0; i< length-1; i++)
      {
        int pos = i*2;
        int high = Character.digit(hex[pos],16);
        int low = Character.digit(hex[pos+1],16);
        int value = (high << 4) | low;
        result[i] = (byte)value;
      }
      result[length-1] = (byte)0x0d;
      return result;
  }
}
