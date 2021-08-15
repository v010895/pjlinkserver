package com.project.pjlinkserver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pjlinkserver.databinding.FragmentSearchBinding;
import com.project.pjlinkserver.viewmodels.PJLinkViewModel;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SearchFragment extends Fragment {
  	private FragmentSearchBinding binding;
  	private PJLinkViewModel mPJLinkViewModel;

  @Nullable
  @org.jetbrains.annotations.Nullable
  @Override
  public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
	binding = FragmentSearchBinding.inflate(inflater,container,false);
	return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);
	mPJLinkViewModel = new ViewModelProvider(getActivity()).get(PJLinkViewModel.class);
	mPJLinkViewModel.searchStatus.observe(getViewLifecycleOwner(),status->{
	  binding.status.setText(status);
	});
  }
}
