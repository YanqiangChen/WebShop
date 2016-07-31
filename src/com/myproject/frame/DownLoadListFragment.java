package com.myproject.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import cn.readphone.util.downloader.DownloadMgr;

import com.example.weatherreport.R;
import com.myproject.data.BannerMgr;
import com.myproject.data.GameMgr;
import com.myproject.data.YFDataInterfaceCallback;
import com.myproject.frame.BannerFragment.YFBannerPagerAdapter;
import com.myproject.frame.adapter.GameListAdapter;
import com.myproject.frame.view.share.ShareHandler;
import com.myproject.struct.BannerImages;
import com.myproject.struct.GameMessages;
import com.myproject.util.ImageLoaderEx;
import com.myproject.util.YFImageLoaderOptions;
import com.myproject.view.YFAMAutoScrollViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ImageView.ScaleType;

public class DownLoadListFragment extends HeaderFragment
{
	private View mContentView;// app列表和活动入口
	private View mHeaderView;// 头部banner和透明渐变背景
	private View mTitleView;// 相当于TitleBar，背景透明，下载按钮和搜索框的最终位置
	private View mSearchView;// 搜索框的最初位置
	private View mContentOverlay; // 加载进度

	// 广告的部分
	private YFAMAutoScrollViewPager mvpBanner;
	private List<View> mBannerViews;
	ArrayList<BannerImages> bannerImages;
	ImageLoaderEx mImageLoader;
	DisplayImageOptions mBannerOptions;
	int mBannerSize;
	YFBannerPagerAdapter mBannerAdapter;

	// listView的部分
	ArrayList<GameMessages> malGameMessage;
	GameListAdapter gameListAdapter;
	private View mEntrancesView;
	private ListView mListView;
	private View mViewOver;
	
	//search部分
	private View mViewShake;
	@Override
	public void onAttach( Activity activity )
	{
		super.onAttach(activity);
		DownloadMgr.getInstance().buildDownloadDb(getActivity());
		showBanners();
		setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener()
		{

			@Override
			public void onHeaderScrollChanged( float ratio )
			{
				mViewOver.setAlpha(ratio);
			}
		});
	}

	private void loadAppList( )
	{
		malGameMessage = new ArrayList<GameMessages>();
		GameMgr.getInstance().getGameMessage(new YFDataInterfaceCallback()
		{

			@Override
			public void onDataResult( int code , Object objData )
			{
				malGameMessage = (ArrayList<GameMessages>)objData;
				Log.d("aaaaaaaaaaaaaa", "获取数据了" + malGameMessage.get(1).getDescription());
				gameListAdapter = new GameListAdapter(getActivity(), malGameMessage, mListView);
				mListView.setAdapter(gameListAdapter);
				gameListAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public View onCreateSearchView( LayoutInflater inflater , ViewGroup container )
	{
		mSearchView=inflater.inflate(R.layout.layout_entrances, container,false);
		mViewShake=mSearchView.findViewById(R.id.rl_third);
		mViewShake.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick( View arg0 )
			{
				Intent intent=new Intent(getActivity(),ShakeActivity.class);
				startActivity(intent);
			}
		});
		return mSearchView;
	}

	@Override
	public View onCreateTitleView( LayoutInflater inflater , ViewGroup container )
	{
		return null;
	}

	@Override
	public View onCreateHeaderView( LayoutInflater inflater , ViewGroup container )
	{
		mHeaderView = inflater.inflate(R.layout.fragment_header, container, false);
		mViewOver=mHeaderView.findViewById(R.id.view_bg_over);
		mViewOver.setAlpha(0);
		mvpBanner = (YFAMAutoScrollViewPager)mHeaderView.findViewById(R.id.vp_banner);
		return mHeaderView;
	}

	@Override
	public View onCreateListView( LayoutInflater inflater , ViewGroup container )
	{
		mContentView = inflater.inflate(R.layout.content_listview, container, false);
		mListView = (ListView)mContentView.findViewById(R.id.lv_list);

//		mEntrancesView = View.inflate(getActivity(), R.layout.layout_entrances, null);
		mListView.addHeaderView(mFakeHeader);
//		mListView.addHeaderView(mEntrancesView);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick( AdapterView<?> arg0 , View view , int position , long id )
			{ 
				Intent intent=new Intent(getActivity(),ShareForGameDetailActivity.class);
				int i=position-1;
				Bundle bundle=new Bundle();
				bundle.putString("name", malGameMessage.get(i).getName());
				bundle.putString("description", malGameMessage.get(i).getDescription());
				bundle.putString("iconUrl", malGameMessage.get(i).getIconUrl());
				bundle.putString("DownloadUrl", malGameMessage.get(i).getDownloadUrl());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		loadAppList();
		return mContentView;
	}


	@Override
	public View onCreateContentOverlayView( LayoutInflater inflater , ViewGroup container )
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void showBanners( )
	{
		mBannerViews = new ArrayList<View>();
		bannerImages = new ArrayList<BannerImages>();
		BannerMgr.getInstance().getAdvertisementListByNum(10, new YFDataInterfaceCallback()
		{
			@Override
			public void onDataResult( int code , Object objData )
			{
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
					if ( bannerImages.size() < 3 )
					{
						mvpBanner.addView(bannerImage);
					}
					mBannerViews.add(bannerImage);
				}
				if ( bannerImages.size() < 3 )
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
			}
		});
	}

	public class YFBannerPagerAdapter extends PagerAdapter
	{

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
