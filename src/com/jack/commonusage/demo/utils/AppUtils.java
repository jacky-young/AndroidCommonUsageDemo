package com.jack.commonusage.demo.utils;


import com.jack.commonusage.demo.BroadcastReceiverDemo;
import com.jack.commonusage.demo.DownloadManagerDemo;
import com.jack.commonusage.demo.DropDownListViewDemo;
import com.jack.commonusage.demo.HttpCacheDemo;
import com.jack.commonusage.demo.HttpImageDemo;
import com.jack.commonusage.demo.InfiniteIndicatorDemo;
import com.jack.commonusage.demo.MainActivity;
import com.jack.commonusage.demo.PullToRefreshDemo;
import com.jack.commonusage.demo.R;
import com.jack.commonusage.demo.ServiceDemo;
import com.jack.commonusage.demo.SlideOnePageGalleryDemo;
import com.jack.commonusage.demo.SwipeRefreshLayoutDemo;
import com.jack.commonusage.demo.ViewPagerDemo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AppUtils {
	
	public static void init(final Activity activity) {
		initActionBar(activity);
		initTrinea(activity);
	}
	
	private static void initActionBar(final Activity activity){
		ActionBar bar = activity.getActionBar();
		if (activity instanceof MainActivity) {
			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_SHOW_CUSTOM);
		} else {
			bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_SHOW_CUSTOM);
		}
	}
	
	public static void urlOpen(Context context, String url){
		Uri urlUri = Uri.parse(url);
		Intent i = new Intent(Intent.ACTION_VIEW, urlUri);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
	
	private static void initTrinea(final Activity activity) {
		if (activity == null) {
			return;
		}

        final String[] result = getText(activity);
        if (result == null) {
			return;
		}
        
        Button info = (Button)activity.findViewById(R.id.trinea_info);
        Spanned txt = Html.fromHtml(result[1]);
        info.setText(txt);
        info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				urlOpen(activity, result[0]);
			}
		});
	}
	
	
    private static String[] getText(final Activity activity) {
		// TODO Auto-generated method stub
    	if (activity == null) {
			return null;
		}
    	
        int prefixSrcId = R.string.description, contentSrcId;
        String url = null;
        
    	Class<?> cls = activity.getClass();
    	if (cls == HttpCacheDemo.class) {
    		url = "http://www.trinea.cn/android/android-http-cache/";
			contentSrcId = R.string.desc_httpcache_view;
		} else if (cls == HttpImageDemo.class) {
			url = "http://www.trinea.cn/android/android-imagecache/";
			contentSrcId = R.string.desc_httpimage_view;
		} else if (cls == SwipeRefreshLayoutDemo.class) {
			url = "";
			contentSrcId = R.string.desc_swiperefreshlayout_view;
		} else if (cls == PullToRefreshDemo.class) {
			url = "https://github.com/chrisbanes/Android-PullToRefresh";
			contentSrcId = R.string.desc_pulltorefresh_view;
		} else if (cls == DropDownListViewDemo.class) {
			url = "http://www.trinea.cn/android/dropdown-to-refresh-and-bottom-load-more-listview/";
			contentSrcId = R.string.desc_dropdownlv_view;
		} else if (cls == ViewPagerDemo.class) {
			url = "http://www.cnblogs.com/trinea/archive/2012/11/23/2771273.html";
			contentSrcId = R.string.desc_viewpager_view;
		} else if (cls == SlideOnePageGalleryDemo.class) {
			url = "http://www.trinea.cn/android/gallery-scroll-one-page/";
			contentSrcId = R.string.desc_slidegallery_view;
		} else if (cls == InfiniteIndicatorDemo.class) {
			url = "https://github.com/lightSky/InfiniteIndicator";
			contentSrcId = R.string.desc_infiniteindicator_view;
		} else if (cls == DownloadManagerDemo.class) {
			url = "http://www.trinea.cn/android/android-downloadmanager/";
			contentSrcId = R.string.desc_downloadmanager_view;
		} else if (cls == BroadcastReceiverDemo.class) {
			url = "http://www.cnblogs.com/trinea/archive/2012/11/09/2763182.html";
			contentSrcId = R.string.desc_broadcastreceiver_view;
		} else if (cls == ServiceDemo.class) {
			url = "http://www.cnblogs.com/trinea/archive/2012/11/08/2699856.html";
			contentSrcId = R.string.desc_servicedemo_view;
		} else {
	        url = "http://www.trinea.cn";
	        contentSrcId = R.string.desc_default;
	        prefixSrcId = R.string.profile;
		}
    	
    	String[] result =	new String[] {url, 
    			getUrlInfo(activity.getString(prefixSrcId), url, activity.getString(contentSrcId))};
    	return result;
	}

	private static String getUrlInfo(String prefix, String url, String content) {
        return new StringBuilder().append(prefix).append("<a href=\"").append(url).append("\">").append(content)
                .append("</a>").toString();
    }	
}
