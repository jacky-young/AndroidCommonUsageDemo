package com.jack.commonusage.demo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ViewPagerDemo extends BasicFragmentActivity {
	private static final int TOTAL_COUNT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.view_pager_demo);
		ViewPager vp = (ViewPager)findViewById(R.id.view_pager);
		List<String> titleList = new ArrayList<String>();
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		for (int i = 0; i < TOTAL_COUNT; i++) {
			ViewPagerFragment vpFragment = new ViewPagerFragment();
			Bundle b = new Bundle();
			b.putString("text", "Page " + i);
			b.putInt("upImageId", 0);
			vpFragment.setArguments(b);
			titleList.add("title " + i);
			fragmentList.add(vpFragment);
		}
		vp.setAdapter(new pagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
	}
	
	private class pagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;
		private List<String> titleList;

		public pagerAdapter(FragmentManager fm, List<Fragment> fmList, List<String> titleList) {
			super(fm);
			this.fragmentList = fmList;
			this.titleList = titleList;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return (fragmentList == null | fragmentList.size()==0) ? null : fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList == null ? 0 : fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return (titleList.size() > position) ? titleList.get(position) : "";
		}

	}
	
}
