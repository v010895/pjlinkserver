package com.project.pjlinkserver;

import android.content.Context;

import com.project.pjlinkserver.repository.ServerRepository;
import com.project.pjlinkserver.server.PJLinkServer;

public class ServiceLocator {
  private static PJLinkServer mPJLinkServer;
  public static PJLinkServer getInstance(){
    if(mPJLinkServer == null)
    {
      mPJLinkServer = new PJLinkServer();
    }
    return mPJLinkServer;
  }
}
