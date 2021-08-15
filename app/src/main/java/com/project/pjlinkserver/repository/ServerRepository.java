package com.project.pjlinkserver.repository;

import android.content.Context;

import com.project.pjlinkserver.database.ServerDatabase;
import com.project.pjlinkserver.entity.ServerStatus;
import com.project.pjlinkserver.interfaces.ServerDao;

import androidx.lifecycle.LiveData;

public class ServerRepository {
  private ServerDatabase mServerDatabase;
  public ServerRepository(Context context){
    mServerDatabase = ServerDatabase.getInstance(context);

  }
  public ServerStatus getStatus(int id)
  {
      ServerStatus status = mServerDatabase.serverDao().getStatus(id);
      return status;
  }
  public LiveData<ServerStatus> getLiveStatus(int id)
  {
      LiveData<ServerStatus> status = mServerDatabase.serverDao().getLiveStatus(id);
      return status;
  }
  public void updateStatus(ServerStatus status)
  {
    ServerDatabase.mExecutorService.execute(()->{
      mServerDatabase.serverDao().updateStatus(status);
    });

  }
}
