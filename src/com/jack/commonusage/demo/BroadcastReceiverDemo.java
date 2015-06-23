package com.jack.commonusage.demo;

import java.net.Inet4Address;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BroadcastReceiverDemo extends BasicActivity {
	private static final String ACTION_GENERAL_SEND = "com.jack.commonusage.demo.SEND_GENERAL_BROADCAST";
	private static final String ACTION_LOCAL_SEND = "com.jack.commonusage.demo.SEND_LOCAL_BROADCAST";
	private static final String ACTION_ORDERED_SEND = "com.jack.commonusage.demo.SEND_ORDERED_BROADCAST";
	private static final String ACTION_STICKY_SEND = "com.jack.commonusage.demo.SEND_STICKY_BROADCAST";
	
	private static final String MSG_KEY = "msg";
	private static final String RESULT_MSG_KEY = "resultMsg";
	
	private Button sendGeneralBtn;
	private Button sendLocalBtn;
	private Button sendOrderedBtn;
	private Button sendStickyBtn;
	private TextView generalMsg;
	private TextView localMsg;
	private TextView orderedMsg;
	private TextView stickyMsg;
	
	private MyGeneralBroadcastReceiver generalReceiver;
	private MyLocalBroadcastReceiver localReceiver;
	private MyOrderedBroadcastReceiverPriorityHigh highPriorityReceiver;
	private MyOrderedBroadcastReceiverPriorityMedium mediumPriorityReceiver;
	private MyOrderedBroadcastReceiverPriorityLow lowPriorityReceiver;
	private MyStickyBroadcastReceiver stickyReceiver;
	
	private boolean isStickyBroadcast = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.broadcast_receiver_demo);
		
		initView();
	}
	
	private void initView() {
		sendGeneralBtn = (Button)findViewById(R.id.send_general_broadcast);
		sendLocalBtn = (Button)findViewById(R.id.send_local_broadcast);
		sendOrderedBtn = (Button)findViewById(R.id.send_ordered_broadcast);
		sendStickyBtn = (Button)findViewById(R.id.send_sticky_broadcast);
		
		generalMsg = (TextView)findViewById(R.id.msg_general_broadcast);
		localMsg = (TextView)findViewById(R.id.msg_local_broadcast);
		orderedMsg = (TextView)findViewById(R.id.msg_ordered_broadcast);
		stickyMsg = (TextView)findViewById(R.id.msg_sticky_broadcast);
		
		sendGeneralBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ACTION_GENERAL_SEND);
				i.putExtra(MSG_KEY, getString(R.string.msg_general_broadcast));
				sendBroadcast(i);
			}
		});
		sendLocalBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ACTION_LOCAL_SEND);
				i.putExtra(MSG_KEY, getString(R.string.msg_local_broadcast));
				LocalBroadcastManager.getInstance(context).sendBroadcast(i);				
			}
		});
		sendOrderedBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ACTION_ORDERED_SEND);
				i.putExtra(MSG_KEY, getString(R.string.msg_ordered_broadcast));
				sendOrderedBroadcast(i, null, new MyOrderedBroadcastResultReceiver(), null, Activity.RESULT_OK, null, null);
			}
		});
		sendStickyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ACTION_STICKY_SEND);
				i.putExtra(MSG_KEY, getString(R.string.msg_sticky_broadcast));
				sendStickyBroadcast(i);
				int waitTime = 2000;
				stickyMsg.setText(getString(R.string.msg_sticky_broadcast_tip, waitTime));
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isStickyBroadcast = true;
						registerReceiver(stickyReceiver, new IntentFilter(ACTION_STICKY_SEND));
					}
				}, waitTime);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		generalReceiver = new MyGeneralBroadcastReceiver();
		localReceiver = new MyLocalBroadcastReceiver();
		highPriorityReceiver = new MyOrderedBroadcastReceiverPriorityHigh();
		mediumPriorityReceiver = new MyOrderedBroadcastReceiverPriorityMedium();
		lowPriorityReceiver = new MyOrderedBroadcastReceiverPriorityLow();
		stickyReceiver = new MyStickyBroadcastReceiver();
		
		registerReceiver(generalReceiver, new IntentFilter(ACTION_GENERAL_SEND));
		LocalBroadcastManager.getInstance(context).registerReceiver(localReceiver, new IntentFilter(ACTION_LOCAL_SEND));
		IntentFilter high = new IntentFilter(ACTION_ORDERED_SEND);
		high.setPriority(100);
		IntentFilter medium = new IntentFilter(ACTION_ORDERED_SEND);
		medium.setPriority(-1);
		IntentFilter low = new IntentFilter(ACTION_ORDERED_SEND);
		low.setPriority(-100);		
		registerReceiver(highPriorityReceiver, high);
		registerReceiver(mediumPriorityReceiver, medium);
		registerReceiver(lowPriorityReceiver, low);	
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(generalReceiver);
		LocalBroadcastManager.getInstance(context).unregisterReceiver(localReceiver);
		unregisterReceiver(highPriorityReceiver);
		unregisterReceiver(mediumPriorityReceiver);
		unregisterReceiver(lowPriorityReceiver);
	}
	
	private class MyGeneralBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			generalMsg.setText(intent.getStringExtra(MSG_KEY));
		}
	}
	
	private class MyLocalBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			localMsg.setText(intent.getStringExtra(MSG_KEY));
		}
	}
	
	private class MyOrderedBroadcastReceiverPriorityHigh extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			orderedMsg.setText(String.format(getString(R.string.ordered_broadcast_high_tip), intent.getStringExtra(MSG_KEY)));
			getResultExtras(true).putString(RESULT_MSG_KEY, "High");
		}
	}
	
	private class MyOrderedBroadcastReceiverPriorityMedium extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			boolean isCancel = false;
			if (isCancel) {
				orderedMsg.setText(orderedMsg.getText()
						+ "\r\n" +
						String.format(getString(R.string.ordered_broadcast_medium_tip), intent.getStringExtra(MSG_KEY),
								getResultExtras(true).get(RESULT_MSG_KEY)));
				abortBroadcast();
			} else {
				orderedMsg.setText(orderedMsg.getText()
						+ "\r\n" +
						String.format(getString(R.string.ordered_broadcast_medium_tip), intent.getStringExtra(MSG_KEY),
								getResultExtras(true).get(RESULT_MSG_KEY)));
				getResultExtras(true).putString(RESULT_MSG_KEY, "Medium");
			}
		}
	}
	
	private class MyOrderedBroadcastReceiverPriorityLow extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			orderedMsg.setText(orderedMsg.getText()
					+ "\r\n" +
					String.format(getString(R.string.ordered_broadcast_low_tip), intent.getStringExtra(MSG_KEY),
							getResultExtras(true).get(RESULT_MSG_KEY)));
			getResultExtras(true).putString(RESULT_MSG_KEY, "Low");
		}
	}
	
	private class MyOrderedBroadcastResultReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			orderedMsg.setText(orderedMsg.getText()
					+ "\r\n" +
					String.format(getString(R.string.ordered_broadcast_tip), intent.getStringExtra(MSG_KEY),
							getResultExtras(true).get(RESULT_MSG_KEY)));
		}
	}
	
	private class MyStickyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			stickyMsg.setText(intent.getStringExtra(MSG_KEY));
		}
	}
	
}
