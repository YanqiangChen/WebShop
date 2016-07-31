package com.myproject.frame;

import java.util.ArrayList;
import java.util.List;

import com.example.weatherreport.R;
import com.myproject.data.BannerMgr;
import com.myproject.data.YFDataInterfaceCallback;
import com.myproject.struct.BannerImages;
import com.myproject.util.Controller;
import com.myproject.util.YFImageLoaderOptions;
import com.myproject.util.Controller.OnDataChangeListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GridBanner extends Fragment implements OnDataChangeListener<ImageView, BannerImages>
{

	View mRootView;
	private ImageView mivBanner1;
	private ImageView mivBanner2;
	private ImageView mivBanner3;
	private ImageView mivBanner4;
	private ImageView mivBanner5;
	
	private DisplayImageOptions mBannerOptions;
	ArrayList<BannerImages> BannerImages;
	private List<Controller<ImageView, BannerImages>> mControllers;
	
	@Override
	public View onCreateView( LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState )
	{
		mRootView=inflater.inflate(R.layout.fragment_grid_banner, null);
		mivBanner1=(ImageView)mRootView.findViewById(R.id.iv_banner1);
		mivBanner2=(ImageView)mRootView.findViewById(R.id.iv_banner2);
		mivBanner3=(ImageView)mRootView.findViewById(R.id.iv_banner3);
		mivBanner4=(ImageView)mRootView.findViewById(R.id.iv_banner4);
		mivBanner5=(ImageView)mRootView.findViewById(R.id.iv_banner5);
		
		mBannerOptions = YFImageLoaderOptions.buildBannerImage().build();
		
		initControllers();
		showBanners();
		return mRootView;
	}
	private void showBanners( )
	{
		BannerMgr.getInstance().loadBelowAdvertisementListFromWeb(6, new YFDataInterfaceCallback()
		{
			
			@Override
			public void onDataResult( int code , Object objData )
			{
				BannerImages=(ArrayList<com.myproject.struct.BannerImages>)objData;
				for(int i=0;i<BannerImages.size();i++)
				{
					mControllers.get(i).setData(BannerImages.get(i));
				}
			}
		});
	}
	private void initControllers( )
	{
		mControllers=new ArrayList<Controller<ImageView,BannerImages>>();
		mControllers.add(new Controller<ImageView, BannerImages>(mivBanner1).setOnDataChangelistener(this));
		mControllers.add(new Controller<ImageView, BannerImages>(mivBanner2).setOnDataChangelistener(this));
		mControllers.add(new Controller<ImageView, BannerImages>(mivBanner3).setOnDataChangelistener(this));
		mControllers.add(new Controller<ImageView, BannerImages>(mivBanner4).setOnDataChangelistener(this));
		mControllers.add(new Controller<ImageView, BannerImages>(mivBanner5).setOnDataChangelistener(this));
	}
	
	
	@Override
	public void onDataChanged( ImageView view , BannerImages data )
	{
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getActivity());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(configuration);
		imageLoader.displayImage(data.getPicurl(), view, mBannerOptions);
	}

}
