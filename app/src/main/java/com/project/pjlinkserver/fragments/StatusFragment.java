package com.project.pjlinkserver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pjlinkserver.databinding.FragmentStatusBinding;
import com.project.pjlinkserver.entity.ServerStatus;
import com.project.pjlinkserver.viewmodels.PJLinkViewModel;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class StatusFragment extends Fragment {
	private FragmentStatusBinding binding;
	private PJLinkViewModel mPJLinkViewModel;

  @Nullable
  @org.jetbrains.annotations.Nullable
  @Override
  public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
	binding = FragmentStatusBinding.inflate(inflater,container,false);
	return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	mPJLinkViewModel = new ViewModelProvider(getActivity()).get(PJLinkViewModel.class);
	mPJLinkViewModel.serverStatus.observe(getViewLifecycleOwner(), new Observer<ServerStatus>() {
	  @Override
	  public void onChanged(ServerStatus serverStatus) {
			updateStatus(serverStatus);
	  }
	});
  }
  private void updateStatus(ServerStatus status)
  {
  	String powerStatus = status.getPowerStatus();
  	String inputStatus = status.getInputStatus();
  	String avmtStatus = status.getAvmtStatus();
  	binding.powerStatus.setText(powerStatus);
  	binding.inputStatus.setText(inputStatus);
  	binding.avmtStatus.setText(avmtStatus);
  }


}
