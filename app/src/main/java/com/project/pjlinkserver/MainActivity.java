package com.project.pjlinkserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.project.adapter.ViewPageAdapter;
import com.project.pjlinkserver.databinding.ActivityMainBinding;
import com.project.pjlinkserver.server.PJLinkServer;
import com.project.pjlinkserver.viewmodels.PJLinkViewModel;
import com.project.pjlinkserver.viewmodels.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
  private PJLinkServer mPJLinkServer;
  private ActivityMainBinding binding;
  private PJLinkViewModel mPJLinkViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    ViewModelFactory factory = new ViewModelFactory(ServiceLocator.getInstance(),getApplication());
    mPJLinkViewModel = new ViewModelProvider(this,factory).get(PJLinkViewModel.class);
    setupComponent();
  }

  private void setupComponent(){
		ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(),getLifecycle());
		binding.viewPager.setAdapter(adapter);
		binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
		  @Override
		  public void onPageSelected(int position) {
			super.onPageSelected(position);
			binding.mainTabsHolder.getTabAt(position).select();
		  }
		});
  }
}