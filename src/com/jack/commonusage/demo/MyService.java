package com.jack.commonusage.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service{
	private int count;
	private MyBinder binder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service onBind", Toast.LENGTH_SHORT).show();
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
		count = 0;
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service onDestory", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service onUnbind", Toast.LENGTH_SHORT).show();
		return super.onUnbind(intent);
	}
	
	public class MyBinder extends Binder {
		MyService getService() {
			return MyService.this;
		}
	}
	
	public int increaseCount() {
		return ++count;
	}
	
	public int getCount() {
		return count;
	}
}
