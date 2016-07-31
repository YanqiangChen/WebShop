package com.myproject.frame;

import java.util.ArrayList;
import java.util.List;

import cn.readphone.util.DisplayUtil;

import com.example.weatherreport.R;
import com.myproject.data.BannerMgr;
import com.myproject.data.YFDataInterfaceCallback;
import com.myproject.struct.BannerImages;
import com.myproject.util.ImageLoaderEx;
import com.myproject.util.YFImageLoaderOptions;
import com.myproject.view.YFAMAutoScrollViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class BannerFragment extends Fragment
{

	View mRootView;
	private YFAMAutoScrollViewPager mvpBanner;
	private LinearLayout mllpoint;
	private DisplayImageOptions mBannerOptions;
	private List<View> mBannerViews;
	ArrayList<BannerImages> bannerImages = new ArrayList<BannerImages>();
	BannerImages bannerImage;
	ImageLoaderEx mImageLoader;
	int mBannerSize;
	private YFBannerPagerAdapter mBannerAdapter;

	int mnCurrentSel;

	@Override
	public View onCreateView( LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState )
	{
		mRootView = inflater.inflate(R.layout.fragment_banner, null);
		mvpBanner = (YFAMAutoScrollViewPager)mRootView.findViewById(R.id.vp_banner);
		mllpoint = (LinearLayout)mRootView.findViewById(R.id.banner_point);
		mBannerOptions = YFImageLoaderOptions.buildBannerImage().build();

		showBanners();
		return mRootView;
	}

	private void showBanners( )
	{
		BannerMgr.getInstance().getAdvertisementListByNum(10, new YFDataInterfaceCallback()
		{

			@Override
			public void onDataResult( int code , Object objData )
			{
				mBannerViews = new ArrayList<View>();
				bannerImages = (ArrayList<BannerImages>)objData;
				mBannerSize = bannerImages.size();
				for ( int i = 0; i < bannerImages.size(); i++ )
				{
					ImageView bannerImage = new ImageView(getActivity());
					bannerImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
					mImageLoader = ImageLoaderEx.getInstance();
					mBannerOptions = YFImageLoaderOptions.buildBannerImage().build();

					ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getActivity());
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.init(configuration);
					imageLoader.displayImage(bannerImages.get(i).getPicurl(), bannerImage, mBannerOptions);

					bannerImage.setScaleType(ScaleType.FIT_XY);
					if ( mBannerSize < 3 )
					{
						mvpBanner.addView(bannerImage);
					}
					mBannerViews.add(bannerImage);
				}
				if ( mBannerSize < 3 )
				{
					mvpBanner.setAutoScrollMode(YFAMAutoScrollViewPager.AUTO_SCROLL_MODE_NONE);
				}
				else
				{
					mvpBanner.setAutoScrollMode(YFAMAutoScrollViewPager.AUTO_SCROLL_MODE_SLIDE);
				}
				mBannerAdapter = new YFBannerPagerAdapter();
				mvpBanner.setAdapter(mBannerAdapter);
				mvpBanner.setInterval(3000);
				mvpBanner.startAutoScroll();
				mvpBanner.setOnPageChangeListener(mOnPageChangeListener);

				int space = DisplayUtil.dip2px(getActivity(), 3);
				android.widget.LinearLayout.LayoutParams pointParams = new android.widget.LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mllpoint.removeAllViews();
				pointParams.setMargins(space, 0, space, 0);
				for ( int i = 0; i < mBannerSize; i++ )
				{
					ImageView point = new ImageView(getActivity());
					point.setImageResource(R.drawable.icon_guide_tip_nor);
					mllpoint.addView(point, pointParams);
				}
				( (ImageView)mllpoint.getChildAt(mnCurrentSel = 0) ).setImageResource(R.drawable.icon_guide_tip_sel);
			}
		});
	}

	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener()
	{

		@Override
		public void onPageSelected( int arg0 )
		{
			( (ImageView)mllpoint.getChildAt(mnCurrentSel) ).setImageResource(R.drawable.icon_guide_tip_nor);
			( (ImageView)mllpoint.getChildAt(mnCurrentSel=(arg0 % mBannerSize)) ).setImageResource(R.drawable.icon_guide_tip_sel);
		}

		@Override
		public void onPageScrolled( int arg0 , float arg1 , int arg2 )
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged( int arg0 )
		{
			// TODO Auto-generated method stub

		}
	};

	public class YFBannerPagerAdapter extends PagerAdapter
	{

		public YFBannerPagerAdapter()
		{
		}

		@Override
		public int getCount( )
		{
			if ( mvpBanner.getAutoScrollMode() == YFAMAutoScrollViewPager.AUTO_SCROLL_MODE_SLIDE )
			{
				return Integer.MAX_VALUE;
			}
			else
			{
				return mBannerSize;
			}
		}

		@Override
		public boolean isViewFromObject( View arg0 , Object arg1 )
		{
			return arg0 == arg1;
		}

		@Override
		public void destroyItem( ViewGroup view , int position , Object object )
		{
			if ( mvpBanner.getAutoScrollMode() == YFAMAutoScrollViewPager.AUTO_SCROLL_MODE_SLIDE )
			{
				view.removeView(mBannerViews.get(position % mBannerSize));
			}
		}

		@Override
		public Object instantiateItem( ViewGroup view , int position )
		{
			if ( mvpBanner.getAutoScrollMode() == YFAMAutoScrollViewPager.AUTO_SCROLL_MODE_SLIDE )
			{
				view.addView(mBannerViews.get(position % mBannerSize));
			}
			return mBannerViews.get(position % mBannerSize);
		}

	}

}
