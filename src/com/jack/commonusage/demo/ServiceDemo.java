package com.jack.commonusage.demo;

import com.jack.commonusage.demo.MyService.MyBinder;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ServiceDemo extends BasicActivity {
	
	private MyService myService;
	private Intent generalIntent;
	private Intent intentService;
	private Intent intentService2;

	private Button startGeneralService;
	private Button stopGeneralService;
	private Button startIntentService;
	private Button bindService;
	private Button operateBindService;
	private Button getBindService;
	private Button unbindService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.service_demo);
		
		initView();
		initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		generalIntent = new Intent(this, MyService.class);
		intentService = new Intent(ServiceDemo.this, MyIntentService.class);
		intentService2 = new Intent(ServiceDemo.this, MyIntentService.class);
		
		startGeneralService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startService(generalIntent);
			}
		});
		stopGeneralService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopService(generalIntent);
			}
		});
		bindService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bindService(generalIntent, conn, Context.BIND_AUTO_CREATE);
			}
		});
		operateBindService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myService != null) {
					Toast.makeText(context, "count: "+myService.increaseCount(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Service is null", Toast.LENGTH_SHORT).show();
				}
			}
		});
		getBindService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myService != null) {
					Toast.makeText(context, "count: "+myService.getCount(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Service is null", Toast.LENGTH_SHORT).show();
				}
			}
		});
		unbindService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myService != null) {
					unbindService(conn);
					myService = null;
				}
			}
		});
		startIntentService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startService(intentService);
				startService(intentService2);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		startGeneralService = (Button)findViewById(R.id.start_general_service);
		stopGeneralService = (Button)findViewById(R.id.stop_general_service);
		startIntentService = (Button)findViewById(R.id.start_intent_service);
		bindService = (Button)findViewById(R.id.bind_service);
		operateBindService = (Button)findViewById(R.id.operate_bind_service);
		getBindService = (Button)findViewById(R.id.get_bind_service);
		unbindService = (Button)findViewById(R.id.unbind_service);
	}
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "Service disconnect", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			myService = ((MyBinder)service).getService();
			Toast.makeText(context, "Service connect", Toast.LENGTH_SHORT).show();
		}
	};

}
