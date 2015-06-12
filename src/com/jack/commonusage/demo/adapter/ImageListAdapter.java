package com.jack.commonusage.demo.adapter;

import java.util.List;

import com.jack.commonusage.demo.R;

import cn.trinea.android.common.util.ListUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Integer> imageLists;
	
	public ImageListAdapter(Context context) {
		super();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListUtils.getSize(imageLists);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (imageLists == null | imageLists.size()==0) ? null : imageLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.slide_gallery_image_layout, null);
			holder = new ViewHolder();
			holder.image = (ImageView)convertView.findViewById(R.id.slide_gallery_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.image.setImageResource(imageLists.get(position));
		return convertView;
	}
	
	static class ViewHolder {
		ImageView image;
	}
	
	public void setImageList(List<Integer> imagelist) {
		this.imageLists = imagelist;
	}

}
