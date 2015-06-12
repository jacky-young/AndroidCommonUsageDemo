package com.jack.commonusage.demo;

import java.util.HashMap;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;
import android.os.Bundle;

public class InfiniteIndicatorDemo extends BasicActivity implements OnSliderClickListener {
	private InfiniteIndicatorLayout mDefaultIndicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.infinite_indicator_demo);
		mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.indicator_default_circle);
		HashMap<String, Integer> image_map = new HashMap<String, Integer>();
		image_map.put("Page A", R.drawable.image1);
		image_map.put("Page B", R.drawable.image2);
		image_map.put("Page C", R.drawable.image3);
		for (String name : image_map.keySet()) {
			DefaultSliderView mSliderView = new DefaultSliderView(this);
			mSliderView.image(image_map.get(name))
					   .setScaleType(BaseSliderView.ScaleType.Fit)
					   .setOnSliderClickListener(this);
			mSliderView.getBundle().putString("extra", name);
			mDefaultIndicator.addSlider(mSliderView);
		}
		mDefaultIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		mDefaultIndicator.startAutoScroll();
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mDefaultIndicator.stopAutoScroll();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mDefaultIndicator.startAutoScroll();
	}

}
