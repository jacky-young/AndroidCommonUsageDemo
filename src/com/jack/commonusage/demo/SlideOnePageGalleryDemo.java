package com.jack.commonusage.demo;

import java.util.ArrayList;
import java.util.List;

import com.jack.commonusage.demo.adapter.ImageListAdapter;

import cn.trinea.android.common.view.SlideOnePageGallery;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SlideOnePageGalleryDemo extends BasicActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.slide_one_page_gallery_demo);
		
		SlideOnePageGallery imageGallery = (SlideOnePageGallery)findViewById(R.id.slide_onepage_gallery);
		imageGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageListAdapter adapter = new ImageListAdapter(context);
		List<Integer> imageList = new ArrayList<Integer>();
		imageList.add(R.drawable.image1);
		imageList.add(R.drawable.image2);
		imageList.add(R.drawable.image3);
		adapter.setImageList(imageList);
		imageGallery.setAdapter(adapter);
	}

}
