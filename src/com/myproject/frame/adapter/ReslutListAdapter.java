package com.myproject.frame.adapter;

import java.util.ArrayList;

import com.example.weatherreport.R;
import com.myproject.struct.YFMBaseStruct;
import com.myproject.struct.hotSearchResult;
import com.myproject.util.YFImageLoaderOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReslutListAdapter extends BaseAdapter
{

	ArrayList<hotSearchResult> obj = new ArrayList<hotSearchResult>();
	Context context;
	private DisplayImageOptions mBannerOptions;
	public ReslutListAdapter(Context context , ArrayList<hotSearchResult> obj)
	{
		this.obj = obj;
		this.context = context;
	}

	@Override
	public int getCount( )
	{
		return obj.size();
	}

	@Override
	public Object getItem( int position )
	{
		return obj.get(position);
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public View getView( int position , View convertView , ViewGroup parent )
	{
		hotSearchResult mHotSearchResult=obj.get(position);
		final ViewHolder viewHoler;
		if(convertView==null)
		{
			viewHoler=new ViewHolder();
			convertView=View.inflate(context, R.layout.search_result_item, null);
			viewHoler.ivIcon=(ImageView)convertView.findViewById(R.id.iv_icon);
			viewHoler.tvDescription=(TextView)convertView.findViewById(R.id.tv_description);
			viewHoler.tvTitle=(TextView)convertView.findViewById(R.id.tv_title);
			viewHoler.tvTime=(TextView)convertView.findViewById(R.id.tv_hottime);
			convertView.setTag(viewHoler);
		}
		else
		{
			viewHoler=(ViewHolder)convertView.getTag();
		}
		
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(configuration);
		mBannerOptions = YFImageLoaderOptions.buildBannerImage().build();
		imageLoader.displayImage(mHotSearchResult.getPicUrl(), viewHoler.ivIcon, mBannerOptions);
		
		viewHoler.tvDescription.setText(mHotSearchResult.getDescription());
		viewHoler.tvTitle.setText(mHotSearchResult.getTitle());
		viewHoler.tvTime.setText(mHotSearchResult.getHottime());
		return convertView;
	}

	class ViewHolder
	{
         ImageView ivIcon;
         TextView tvTitle;
         TextView tvDescription;
         TextView tvTime;
         
	}

}
