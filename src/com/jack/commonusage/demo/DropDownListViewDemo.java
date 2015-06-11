package com.jack.commonusage.demo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;


import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class DropDownListViewDemo extends BasicActivity {
	private String[] mStrings = {"Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee", "Ffffff",
            "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm", "Nnnnnn",}; 
	
	private DropDownListView    listview  = null;
	private LinkedList<String>  listitems = null;
	private ArrayAdapter<String> adapter  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.drop_down_listview_demo);
		listview = (DropDownListView)findViewById(R.id.list_view);
		
		listview.setOnDropDownListener(new OnDropDownListener() {
			
			@Override
			public void onDropDown() {
				// TODO Auto-generated method stub
				new GetDataTask(true).execute();
			}
		});
		listview.setOnBottomListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetDataTask(false).execute();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "position: "+position+" clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		listitems = new LinkedList<String>();
		listitems.addAll(Arrays.asList(mStrings));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listitems);
		listview.setAdapter(adapter);
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]>{
		private boolean isDropDown;
		
		public GetDataTask(boolean isDropDown) {
			this.isDropDown = isDropDown;
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
			if (isDropDown) {
				listitems.addFirst("Add new item on Top");
				adapter.notifyDataSetChanged();
				
				SimpleDateFormat dataFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
				listview.onDropDownComplete(getString(R.string.update_at) + dataFormat.format(new Date()));
			} else {
				listitems.add("Add new item on Bottom");
				adapter.notifyDataSetChanged();
				listview.onBottomComplete();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}

}
