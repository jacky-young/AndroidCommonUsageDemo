package com.jack.commonusage.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;


import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class HttpImageDemo extends BasicActivity {
	private List<String> imageUrlList;
	
	private static final int COLUMNS = 2;
	private static final int IMAGEVIEW_DEFAULT_HEIGHT = 400;
	
	public static final String TAG_DB = "image_cache";
	public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
														.append(Environment.getExternalStorageDirectory()
																.getAbsolutePath()).append(File.separator)
														.append("Trina").append(File.separator)
														.append("ImageCache").append(File.separator)
														.toString();
	private RelativeLayout parentLayout;
	
	private Button httpImageGetBT;
	private Button UILGetBT;
	private Button picassoGetBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.http_image_demo);
		initImageUrls();
		parentLayout = (RelativeLayout)findViewById(R.id.http_image_parent_layout);
		
		httpImageGetBT = (Button)findViewById(R.id.http_image_get);
		UILGetBT       = (Button)findViewById(R.id.uil_image_get);
		picassoGetBT   = (Button)findViewById(R.id.picasso_image_get);
		
		httpImageGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IMAGE_CACHE.initData(context, TAG_DB);
				IMAGE_CACHE.setContext(context);
				IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
				
				int i = 0, viewId = 0x7F24FFF0;
				int verticalSpacing, horizontalSpacing;
				verticalSpacing = horizontalSpacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
				Display display = getWindowManager().getDefaultDisplay();
				int imageWidth = (display.getWidth() - (COLUMNS + 1)* verticalSpacing) / 2;
				for (String imageurl : imageUrlList) {
					ImageView image = new ImageView(context);
					image.setId(++viewId);
					image.setScaleType(ScaleType.CENTER);
					image.setBackgroundResource(R.drawable.image_border);
					parentLayout.addView(image);
					
					LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
					params.width = imageWidth;
					params.topMargin = verticalSpacing;
					params.rightMargin = horizontalSpacing;
					int column = i % COLUMNS;
					int row = i / COLUMNS;
					if (column > 0) {
						params.addRule(RelativeLayout.RIGHT_OF, viewId - 1);
					}
					if (row > 0) {
						params.addRule(RelativeLayout.BELOW, viewId - COLUMNS);
					}
					params.height = IMAGEVIEW_DEFAULT_HEIGHT;
					
					IMAGE_CACHE.get(imageurl, image);
					i++;
				}
			}
		});
		
		UILGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0, viewId = 0x7F24FFF0;
				int verticalSpacing, horizontalSpacing;
				verticalSpacing = horizontalSpacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
				Display display = getWindowManager().getDefaultDisplay();
				int imageWidth = (display.getWidth() - (COLUMNS + 1)* verticalSpacing) / 2;
				for (String imageurl : imageUrlList) {
					ImageView image = new ImageView(context);
					image.setId(++viewId);
					image.setScaleType(ScaleType.CENTER);
					image.setBackgroundResource(R.drawable.image_border);
					parentLayout.addView(image);
					
					LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
					params.width = imageWidth;
					params.topMargin = verticalSpacing;
					params.rightMargin = horizontalSpacing;
					int column = i % COLUMNS;
					int row = i / COLUMNS;
					if (column > 0) {
						params.addRule(RelativeLayout.RIGHT_OF, viewId - 1);
					}
					if (row > 0) {
						params.addRule(RelativeLayout.BELOW, viewId - COLUMNS);
					}
//					params.height = IMAGEVIEW_DEFAULT_HEIGHT;
					
					ImageLoader.getInstance().displayImage(imageurl, image);
					i++;
				}
			}
		});
		File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/cache");
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
										.cacheInMemory(false)
										.cacheOnDisk(false)
										.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
											.defaultDisplayImageOptions(options)
											.diskCache(new UnlimitedDiskCache(cacheDir))
											.build();
		ImageLoader.getInstance().init(config);
		
		picassoGetBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0, viewId = 0x7F24FFF0;
				int verticalSpacing, horizontalSpacing;
				verticalSpacing = horizontalSpacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
				Display display = getWindowManager().getDefaultDisplay();
				int imageWidth = (display.getWidth() - (COLUMNS + 1)* verticalSpacing) / 2;
				for (String imageurl : imageUrlList) {
					ImageView image = new ImageView(context);
					image.setId(++viewId);
					image.setScaleType(ScaleType.CENTER);
					image.setBackgroundResource(R.drawable.image_border);
					parentLayout.addView(image);
					
					LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
					params.width = imageWidth;
					params.topMargin = verticalSpacing;
					params.rightMargin = horizontalSpacing;
					int column = i % COLUMNS;
					int row = i / COLUMNS;
					if (column > 0) {
						params.addRule(RelativeLayout.RIGHT_OF, viewId - 1);
					}
					if (row > 0) {
						params.addRule(RelativeLayout.BELOW, viewId - COLUMNS);
					}
//					params.height = IMAGEVIEW_DEFAULT_HEIGHT;
					Picasso.with(context).setIndicatorsEnabled(true);
					Picasso.with(context).load(imageurl).into(image);
					i++;
				}
			}
		});
		
	}
	
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	
	static {
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {
			
			@Override
			public void onPreGet(String imageUrl, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view,
					boolean isInCache) {
				// TODO Auto-generated method stub
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView)view;
					imageView.setImageBitmap(loadedImage);
					
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}
					
					LayoutParams imageParams = (LayoutParams)imageView.getLayoutParams();
					imageParams.height = imageParams.width * loadedImage.getHeight() / loadedImage.getWidth();
					imageView.setScaleType(ScaleType.FIT_XY);
				}				
			}
			
			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage, View view,
					FailedReason failedReason) {
				// TODO Auto-generated method stub
				
			}
		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
	}
	
	private static AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}

	private void initImageUrls() {
		imageUrlList = new ArrayList<String>();
        imageUrlList.add("http://farm8.staticflickr.com/7409/9148527822_36fa37d7ca_z.jpg");
        imageUrlList.add("http://farm4.staticflickr.com/3755/9148527824_6c156185ea.jpg");
        imageUrlList.add("http://farm8.staticflickr.com/7318/9148527808_e804baef0b.jpg");
        imageUrlList.add("http://farm8.staticflickr.com/7318/9146300275_5fe995d123.jpg");
        imageUrlList.add("http://farm8.staticflickr.com/7288/9146300469_bd3420c75b_z.jpg");
        imageUrlList.add("http://farm8.staticflickr.com/7351/9148527976_8a4e75ae87.jpg");
        imageUrlList.add("http://farm3.staticflickr.com/2888/9148527996_f05118d7de_o.jpg");
        imageUrlList.add("http://farm3.staticflickr.com/2863/9148527892_31f9377351_o.jpg");
        imageUrlList.add("http://farm8.staticflickr.com/7310/9148528008_8e8f51997a.jpg");
        imageUrlList.add("http://farm3.staticflickr.com/2849/9148528108_dfcda19507.jpg");
        imageUrlList.add("http://farm4.staticflickr.com/3739/9148528022_e9bf03058f.jpg");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		IMAGE_CACHE.saveDataToDb(this, TAG_DB);
		super.onDestroy();
	}
}
