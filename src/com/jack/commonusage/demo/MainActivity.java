package com.jack.commonusage.demo;


import java.util.Arrays;
import java.util.LinkedList;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BasicActivity {
	private static final String TAG = "MainActivity";
	
    private static final String[] mStrings = {"SwipeRefreshLayout Demo", "HttpCache Demo", "ImageCache Demo",
        "PullToRefresh Demo", "DropDownListView Demo", "onBottom onTop ScrollView Demo", "DownloadManager Demo",
        "SearchView Demo", "InfiniteIndicator Demo", "Slide One Page Gallery Demo", "ViewPager Demo",
        "Service Demo", "BroadcastReceiver Demo"};

    private static final int      total    = mStrings.length - 1;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.main_layout);
		Log.d(TAG, "onCreate called");
		
		LinkedList<String> mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, mListItems);
		
		ListView demoListView = (ListView)findViewById(R.id.simple_list_view);
		demoListView.setAdapter(adapter);
		demoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				if (position == total-12) {
					startActivity(SwipeRefreshLayoutDemo.class);
				} else if (position == total-11) {
					startActivity(HttpCacheDemo.class);
				} else if (position == total-10) {
					startActivity(HttpImageDemo.class);
				} else if (position == total-9) {
					startActivity(PullToRefreshDemo.class);
				} else if (position == total-8) {
					startActivity(DropDownListViewDemo.class);
				} else if (position == total-6) {
					startActivity(DownloadManagerDemo.class);
				} else if (position == total-4) {
					startActivity(InfiniteIndicatorDemo.class);
				} else if (position == total-3) {
					startActivity(SlideOnePageGalleryDemo.class);
				} else if (position == total-2) {
					startActivity(ViewPagerDemo.class);
				} else if (position == total-1) {
					startActivity(ServiceDemo.class);
				} else if (position == total) {
					startActivity(BroadcastReceiverDemo.class);
				}
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged called");
	}
	
	private void startActivity(Class<?> cls) {
		Intent intent = new Intent(MainActivity.this, cls);
		startActivity(intent);
	}
}
