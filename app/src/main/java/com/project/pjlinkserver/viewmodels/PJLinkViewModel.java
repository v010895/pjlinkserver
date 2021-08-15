package com.project.pjlinkserver.viewmodels;

import android.app.Application;

import com.project.pjlinkserver.entity.ServerStatus;
import com.project.pjlinkserver.repository.ServerRepository;
import com.project.pjlinkserver.server.PJLinkServer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PJLinkViewModel extends AndroidViewModel {
  public MutableLiveData<String> searchStatus = new MutableLiveData<>();
  public LiveData<ServerStatus> serverStatus;
  private PJLinkServer mPJLinkServer;
  private ServerRepository mServerRepository;
  public PJLinkViewModel(PJLinkServer server, @NonNull  Application application) {
    super(application);
    mServerRepository = new ServerRepository(application);
    mPJLinkServer = server;
    mPJLinkServer.setRepository(mServerRepository);
    mPJLinkServer.setSearchStatusListner(status->{
      searchStatus.postValue(status);
    });
    serverStatus = mServerRepository.getLiveStatus(1);
  }
}
