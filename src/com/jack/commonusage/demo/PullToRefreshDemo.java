package com.jack.commonusage.demo;

import java.util.Arrays;
import java.util.LinkedList;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PullToRefreshDemo extends BasicActivity{
	private String[] mStrings = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",};

	private PullToRefreshListView listview;
	private LinkedList<String> listItems;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.pulltorefresh_listview_demo);
		
		listview = (PullToRefreshListView)findViewById(R.id.pull_to_refresh_list);
		listview.setMode(Mode.BOTH);
//		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//				new GetDataTask(true).execute();
//			}
//		});
		ILoadingLayout label = listview.getLoadingLayoutProxy();
		label.setPullLabel("你可劲拉，拉...");
		label.setRefreshingLabel("好嘞，正在刷新...");
		label.setReleaseLabel("你敢放，我就敢刷新...");
		
		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				new GetDataTask(true).execute();				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				new GetDataTask(false).execute();	
			}

		});
		listview.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				Toast.makeText(context, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		
		listItems = new LinkedList<String>();
		listItems.addAll(Arrays.asList(mStrings));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		
		ListView actualListView = listview.getRefreshableView();
		registerForContextMenu(actualListView);
		
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		listview.setOnPullEventListener(soundListener);
		
		actualListView.setAdapter(adapter);
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private boolean isPullDown;
		
		public GetDataTask(boolean isPullDown) {
			this.isPullDown = isPullDown;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			if (isPullDown) {
				listItems.addFirst("Add after refresh...");
				adapter.notifyDataSetChanged();
				listview.onRefreshComplete();
			} else {
				listItems.add("Add after last...");
				adapter.notifyDataSetChanged();
				listview.onRefreshComplete();
			}

			super.onPostExecute(result);
		}
		
	}

}
