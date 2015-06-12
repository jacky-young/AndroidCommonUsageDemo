package com.jack.commonusage.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.view_pager_fragment_demo, container, false);
		TextView text = (TextView)v.findViewById(R.id.view_pager_fragment_text);
		ImageView image = (ImageView)v.findViewById(R.id.view_pager_fragment_image);
		
		Bundle bundle = getArguments();
		if (bundle != null) {
			int upImgeId = bundle.getInt("upImageId");
			if (upImgeId != 0) {
				image.setImageResource(upImgeId);
			}
			String t = bundle.getString("text");
			text.setText(t);
		}
		return v;
	}

}
