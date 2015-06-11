package com.jack.commonusage.demo;

import java.util.Arrays;
import java.util.LinkedList;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwipeRefreshLayoutDemo extends BasicActivity implements OnRefreshListener{
    private static final int REFRESH_COMPLETE = 0x110;
    
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView           mListView;
    
    private String[] mStrings = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",};
    
    private LinkedList<String> listItems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState, R.layout.swiperefreshlayout_listview_demo);
        
        mListView           = (ListView)findViewById(R.id.swipe_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        
        listItems = new LinkedList<String>();
        listItems.addAll(Arrays.asList(mStrings));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }
    
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
            case REFRESH_COMPLETE:
                listItems.addFirst("Add after refresh...");
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            }
        }
        
    };

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1000);
	}
}
