package com.jack.commonusage.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class SearchViewDemo extends BasicActivity {

	private SearchView searchView;
	private InputMethodManager inputMethodManager;
	private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);
	
	private TextView searchInfo;
	private String currentTip;
	
	private MyHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.searchview_demo);
		
		searchInfo = (TextView)findViewById(R.id.searchview_tip);
		handler = new MyHandler();
		inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		ActionBar bar = getActionBar();
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP
								| ActionBar.DISPLAY_SHOW_CUSTOM);
		setTitle(" ");
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customActionBarView = inflater.inflate(R.layout.search_view_demo_title, null);
		searchView = (SearchView)customActionBarView.findViewById(R.id.search_view);
		searchView.setVisibility(View.VISIBLE);
		searchView.setIconifiedByDefault(true);
		searchView.setIconified(false);
//		searchView.setSubmitButtonEnabled(true);
		if (Build.VERSION.SDK_INT >= 14) {
			searchView.onActionViewExpanded();
		}
		
		searchView.setOnCloseListener(new OnCloseListener() {
			
			@Override
			public boolean onClose() {
				// TODO Auto-generated method stub
				return true;
			}
		});
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				searchInfo.setText("search submit result");
				hideSoftInput();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if (newText != null && newText.length() > 0) {
					currentTip = newText;
					showSearchTip(newText);
				}
				return true;
			}
		});
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		bar.setCustomView(customActionBarView, params);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
								| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
	}

	public void showSearchTip(String txt) {
		// TODO Auto-generated method stub
		schedule(new SearchTipThread(txt), 500);
	}

	private void hideSoftInput() {
		// TODO Auto-generated method stub
		if (inputMethodManager != null) {
			View v = SearchViewDemo.this.getCurrentFocus();
			if (v == null) {
				return;
			}
			inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			searchView.clearFocus();
		}
	}
	
	public ScheduledFuture<?> schedule(Runnable command, long delayTimeMills) {
		return scheduledExecutor.schedule(command, delayTimeMills, TimeUnit.MILLISECONDS);
	}
	
	class SearchTipThread implements Runnable {
		String newText;
		
		public SearchTipThread(String txt) {
			this.newText = txt;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (newText != null && newText.equals(currentTip)) {
				handler.sendMessage(handler.obtainMessage(1, newText + " search tips"));
			}
		}
		
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				searchInfo.setText((String)msg.obj);
				break;
			}
		}
		
	}
}
