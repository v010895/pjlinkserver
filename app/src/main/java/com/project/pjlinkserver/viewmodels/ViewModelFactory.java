package com.project.pjlinkserver.viewmodels;

import android.app.Application;

import com.project.pjlinkserver.server.PJLinkServer;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
  private PJLinkServer mPJLinkServer;
  private Application mApplication;
  public ViewModelFactory(PJLinkServer server, Application application)
  {
    mPJLinkServer = server;
    mApplication = application;
  }
  @NonNull
  @org.jetbrains.annotations.NotNull
  @Override
  public <T extends ViewModel> T create(@NonNull @org.jetbrains.annotations.NotNull Class<T> modelClass) {
    if(modelClass.isAssignableFrom(PJLinkViewModel.class))
    {
      return (T) new PJLinkViewModel(mPJLinkServer,mApplication);
    }
    else{
      throw new IllegalArgumentException();
    }
  }
}
