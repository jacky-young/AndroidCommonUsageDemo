package com.jack.commonusage.demo;


import java.io.File;
import java.text.DecimalFormat;

import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadManagerDemo extends BasicActivity {
	private static final String DOWNLOAD_FOLDER_NAME = "Trinea";
	private static final String DOWNLOAD_FILE_NAME = "MEILISHUO.apk";
	private static final String APK_URL = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
	private static final String KEY_NAME_DOWNLOAD_ID_STRING = "downloadId";
	
	private TextView    downloadSize;
	private TextView    downloadPrecent;
	private TextView    downloadTip;	
	private Button      downloadButton;
	private Button      downloadCancel;
	private ProgressBar downloadProgress;
	
	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private long downloadId = 0;
	
	private MyHandler handler;
	private DownloadChangeObserver downloadChangeObserver;
	private CompleteReceiver completeReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.downlaod_manager_demo);
		
		handler = new MyHandler();
		downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		
		Intent intent = getIntent();
		if (intent != null) {
			Uri data = intent.getData();
			if (data != null) {
				Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show();
			}
		}
		
		initView();
		initData();
		
		downloadChangeObserver = new DownloadChangeObserver(handler);
		completeReceiver = new CompleteReceiver();
		registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
	
	private void initView() {
		downloadButton = (Button)findViewById(R.id.download_button);
		downloadCancel = (Button)findViewById(R.id.download_cancel);
		downloadProgress = (ProgressBar)findViewById(R.id.download_progress);
		downloadSize = (TextView)findViewById(R.id.download_size);
		downloadPrecent = (TextView)findViewById(R.id.download_precent);
		downloadTip = (TextView)findViewById(R.id.download_tip);
		downloadTip.setText(getString(R.string.tip_download_file)
				+ Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME));
	}
	
	private void initData() {
		downloadId = PreferencesUtils.getLong(context, KEY_NAME_DOWNLOAD_ID_STRING);
		updateView();
		downloadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File folder = Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
				if (!folder.exists()||!folder.isDirectory()) {
					folder.mkdirs();
				}
				
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(APK_URL));
				request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME);
				request.setDescription("meilishuo desc");
				request.setTitle(getString(R.string.download_notification_title));
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setVisibleInDownloadsUi(false);
				request.setMimeType("application/cn.trinea.download.file");
				downloadId = downloadManager.enqueue(request);
				PreferencesUtils.putLong(context, KEY_NAME_DOWNLOAD_ID_STRING, downloadId);
				updateView();
			}
		});
		downloadCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downloadManager.remove(downloadId);
				updateView();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getContentResolver().registerContentObserver(DownloadManagerPro.CONTENT_URI, true, downloadChangeObserver);
		updateView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(completeReceiver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getContentResolver().unregisterContentObserver(downloadChangeObserver);
	}	
	
	private void updateView() {
		// TODO Auto-generated method stub
		int[] byteAndStatus = downloadManagerPro.getBytesAndStatus(downloadId);
		handler.sendMessage(handler.obtainMessage(0, byteAndStatus[0], byteAndStatus[1], byteAndStatus[2]));
	}

	class DownloadChangeObserver extends ContentObserver {

		public DownloadChangeObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			updateView();
		}
	}
	
	class CompleteReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (completeDownloadId == downloadId) {
				initData();
				updateView();
				if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
					String apkFile = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
										.append(File.separator).append(DOWNLOAD_FOLDER_NAME)
										.append(File.separator).append(DOWNLOAD_FILE_NAME)
										.toString();
					install(context, apkFile);
				}
			}
		}
	}
	
	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch (msg.what) {
			case 0:
				int status = (Integer)msg.obj;
				if (isDownloading(status)) {
					downloadProgress.setVisibility(View.VISIBLE);
					downloadProgress.setMax(0);
					downloadProgress.setProgress(0);
					downloadButton.setVisibility(View.GONE);
					downloadSize.setVisibility(View.VISIBLE);
					downloadPrecent.setVisibility(View.VISIBLE);
					downloadCancel.setVisibility(View.VISIBLE);
					if (msg.arg2 < 0) {
						downloadProgress.setIndeterminate(true);
						downloadPrecent.setText("0%");
						downloadSize.setText("0M/0M");
					} else {
						downloadProgress.setIndeterminate(false);
						downloadProgress.setMax(msg.arg2);
						downloadProgress.setProgress(msg.arg1);
						downloadPrecent.setText(getNotiPrecent(msg.arg1, msg.arg2));
						downloadSize.setText(getAppSize(msg.arg1)+"/"+getAppSize(msg.arg2));
					}
				} else {
					downloadProgress.setVisibility(View.GONE);
					downloadProgress.setMax(0);
					downloadProgress.setProgress(0);
					downloadButton.setVisibility(View.VISIBLE);
					downloadSize.setVisibility(View.GONE);
					downloadPrecent.setVisibility(View.GONE);
					downloadCancel.setVisibility(View.GONE);
					if (status == DownloadManager.STATUS_FAILED) {
						downloadButton.setText(getString(R.string.download_button_failed_title));
					} else if (status == DownloadManager.STATUS_SUCCESSFUL) {
						downloadButton.setText(getString(R.string.download_button_success_title));
					} else {
						downloadButton.setText(getString(R.string.download_button_get_title));
					}
				}
				break;
			}
		}
		
	}

	public static boolean isDownloading(int status) {
		// TODO Auto-generated method stub
		return status == DownloadManager.STATUS_RUNNING ||
				status == DownloadManager.STATUS_PAUSED ||
				status == DownloadManager.STATUS_PENDING;
	}
	
	public static boolean install(Context context, String apkFile) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File file = new File(apkFile);
		if (file!=null && file.length()>0 || file.exists() || file.isFile()) {
			intent.setDataAndType(Uri.parse("file://"+file), "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		}
		return false;
	}

	private static final long KB_2_BYTE = 1024;
	private static final long MB_2_BYTE = 1024*1024;
	static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##"); 

	public static String getAppSize(int size) {
		// TODO Auto-generated method stub
		if (size <= 0) {
			return "0M";
		}
		
		if (size >= MB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size/MB_2_BYTE)).append("MB").toString();
		} else if (size >= KB_2_BYTE) {
			return new StringBuilder(16).append(DOUBLE_DECIMAL_FORMAT.format((double)size/KB_2_BYTE)).append("KB").toString();
		} else {
			return size+"B";
		}
	}

	public static CharSequence getNotiPrecent(int process, int max) {
		// TODO Auto-generated method stub
		int rate = 0;
		if (process <= 0 || max <= 0) {
			rate = 0;
		} else if (process > max) {
			rate = 100;
		} else {
			rate = (int)((double)process/max*100);
		}
		return new StringBuilder(16).append(rate).append("%");
	}

}
