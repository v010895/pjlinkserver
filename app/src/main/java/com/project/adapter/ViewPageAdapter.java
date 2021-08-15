package com.project.adapter;

import com.project.pjlinkserver.fragments.SearchFragment;
import com.project.pjlinkserver.fragments.StatusFragment;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPageAdapter extends FragmentStateAdapter {
  private HashMap<Integer, Fragment> mFragments = new HashMap<>();
  public ViewPageAdapter(FragmentManager fm, Lifecycle lifecycle){
    super(fm,lifecycle);

  }

  @NonNull
  @NotNull
  @Override
  public Fragment createFragment(int position) {
	Fragment fragment = getFragment(position);
	mFragments.put(position,fragment);
	return fragment;
  }
  private Fragment getFragment(int position)
  {
    switch(position)
	{
	  case 0: return new StatusFragment();
	  case 1: return new SearchFragment();
	  default: throw new RuntimeException("Unknown fragment position");
	}
  }
  @Override
  public int getItemCount() {
	return 2;
  }
}
