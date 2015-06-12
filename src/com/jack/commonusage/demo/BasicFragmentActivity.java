package com.jack.commonusage.demo;

import com.jack.commonusage.demo.utils.AppUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class BasicFragmentActivity extends FragmentActivity {
	protected Context context;

	protected void onCreate(Bundle savedInstanceState, int LayouResID) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(LayouResID);
		
		context = getApplicationContext();
		AppUtils.init(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.github:
			AppUtils.urlOpen(context, getString(R.string.github_trinea));
			return true;
		}
		return false;		
	}
	
}
