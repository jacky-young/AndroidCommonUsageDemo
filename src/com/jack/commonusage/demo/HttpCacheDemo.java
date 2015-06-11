package com.jack.commonusage.demo;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.StringUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HttpCacheDemo extends BasicActivity{
	private static final String OKHTTP_RESPONSE_BODY = "body";
	private static final String OKHTTP_RESPONSE_HEAD = "head";
	private EditText httpUrlET;
	private Button   httpGetBT;
	private Button   okhttpGetBT;
	private Button   asynchttpGetBT;
	private TextView httpInfoTV;
	private TextView httpContentTV;
	
	private HttpCache httpCache;
	private MyHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.http_cache_demo);
		
		httpUrlET = (EditText)findViewById(R.id.http_cache_url);
		httpGetBT = (Button)findViewById(R.id.http_cache_get);
		okhttpGetBT = (Button)findViewById(R.id.okhttp_get);
		asynchttpGetBT = (Button)findViewById(R.id.async_http_get);
		httpInfoTV = (TextView)findViewById(R.id.http_cache_info);
		httpContentTV = (TextView)findViewById(R.id.http_cache_content);
		
		httpCache = CacheManager.getHttpCache(context);
		httpGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = httpUrlET.getText().toString();
				url = StringUtils.isEmpty(url) ? httpUrlET.getHint().toString() : url;
				httpCache.httpGet(url, new HttpCacheListener() {

					@Override
					protected void onPreGet() {
						// TODO Auto-generated method stub
						httpInfoTV.setText("");
						httpContentTV.setText("waiting...");
						httpGetBT.setEnabled(false);
					}

					@Override
					protected void onPostGet(HttpResponse httpResponse,
							boolean isInCache) {
						// TODO Auto-generated method stub
						if (httpResponse != null) {
							StringBuilder sb = new StringBuilder(256);
							sb.append("is in cache: ").append(isInCache).append("\r\n");
							if (isInCache) {
								sb.append("expires: ").append(new Date(httpResponse.getExpiredTime()).toGMTString())
								.append("\r\n");
							}
							httpInfoTV.setText(sb.toString());
							httpContentTV.setText(httpResponse.getResponseBody());
						} else {
							httpContentTV.setText("httpResponse is null");
						}
						httpGetBT.setEnabled(true);
					}
					
				});
			}
		});
		
		handler = new MyHandler();
		okhttpGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				okhttpGetBT.setEnabled(false);
				String url = httpUrlET.getText().toString();
				url = StringUtils.isEmpty(url) ? httpUrlET.getHint().toString() : url;
//				new OKHttpAsyncTask().execute(url);
				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder().url(url).build();
				client.newCall(request).enqueue(new Callback() {
					
					@Override
					public void onResponse(Response response) throws IOException {
						// TODO Auto-generated method stub
						Message msg = new Message();
						if (response.isSuccessful()) {
							Bundle b = new Bundle();
							b.putString(OKHTTP_RESPONSE_BODY, response.body().string());
							b.putInt(OKHTTP_RESPONSE_HEAD, response.headers().size());
							msg.setData(b);
						}
						handler.sendMessage(msg);
					}
					
					@Override
					public void onFailure(Request arg0, IOException arg1) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		
		asynchttpGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				asynchttpGetBT.setEnabled(false);
				String url = httpUrlET.getText().toString();
				url = StringUtils.isEmpty(url) ? httpUrlET.getHint().toString() : url;
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(url, new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] arg1, byte[] response) {
						// TODO Auto-generated method stub
						httpInfoTV.setText("status code: " + statusCode);
						httpContentTV.setText(new String(response));
						asynchttpGetBT.setEnabled(true);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] arg1, byte[] arg2, Throwable arg3) {
						// TODO Auto-generated method stub
						httpInfoTV.setText("status code: " + statusCode);
						httpContentTV.setText("httpResponse is null");
						asynchttpGetBT.setEnabled(true);
					}
				});
			}
		});
	}
	
	private class OKHttpAsyncTask extends AsyncTask<String, Void, Response> {
		OkHttpClient client = null;

		@Override
		protected Response doInBackground(String... url) {
			// TODO Auto-generated method stub
			Request request = new Request.Builder().url(url[0]).build();
			try {
				Response response = client.newCall(request).execute();
				return response;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			client = new OkHttpClient();
		}

		@Override
		protected void onPostExecute(Response result) {
			// TODO Auto-generated method stub
			if (result.isSuccessful()) {
				httpInfoTV.setText("headers size: " + result.headers().size());
				try {
					httpContentTV.setText(result.body().string());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				httpContentTV.setText("httpResponse is null");
			}
			okhttpGetBT.setEnabled(true);
		}
	}
	
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b = msg.getData();
			String body = b.getString(OKHTTP_RESPONSE_BODY);
			int head = b.getInt(OKHTTP_RESPONSE_HEAD);
			if (body != null) {
				httpContentTV.setText(body);
				httpInfoTV.setText("headers size: " + head);
			} else {
				httpContentTV.setText("httpResponse is null");
			}
			okhttpGetBT.setEnabled(true);
		}
		
	}
}
