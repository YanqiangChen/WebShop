package com.myproject.frame;


import com.example.weatherreport.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Space;

public abstract class HeaderFragment extends Fragment
{
	private FrameLayout mFrameLayout;

	private View mHeaderView;
	private View mTitleView;
	private View mSearchView;
	private int mHeaderHeight;
	private int mScrollState = - 1;
	private float mMinHeaderTranslation;

	protected Space mFakeHeader;
	
	private OnHeaderScrollChangedListener mOnHeaderScrollChangedListener;
	
	public interface OnHeaderScrollChangedListener
	{
		public void onHeaderScrollChanged( float ratio );
	}

	public void setOnHeaderScrollChangedListener( OnHeaderScrollChangedListener listener )
	{
		mOnHeaderScrollChangedListener = listener;
	}
	@Override
	public View onCreateView( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
	{
		final Activity activity = getActivity();
		mFrameLayout = new FrameLayout(activity);

		mHeaderView = onCreateHeaderView(inflater, mFrameLayout);
		mTitleView = onCreateTitleView(inflater, mFrameLayout);
		mSearchView = onCreateSearchView(inflater, mFrameLayout);

		mHeaderHeight = mHeaderView.getLayoutParams().height; // headerView的高度
		mMinHeaderTranslation = (float)( - mHeaderHeight + getResources().getDimensionPixelSize(R.dimen.header_height) );

		// 预留和头部大小相同的空间
		mFakeHeader = new Space(getActivity());
		mFakeHeader.setLayoutParams(new ListView.LayoutParams(0, getResources().getDimensionPixelSize(R.dimen.header_bg_height)));

		// 列表视图
		View contentView = onCreateListView(inflater, mFrameLayout);

		if ( contentView instanceof ListView )    //contentView是否是ListView的一个实例
		{
			final ListView listView = (ListView)contentView;
			listView.setOnScrollListener(new OnScrollListener()         //向上移动是负值
			{
				
				@Override
				public void onScrollStateChanged( AbsListView absListView , int scrollState )
				{
					mScrollState = scrollState;
				}
				
				/*
				 * SCROLL_STATE_FLING 静止状态
				 * 
				 * SCROLL_STATE_TOUCH_SCROLL手指滚动状态
				 * 
				 * SCROLL_STATE_FLING 离开自己在滚动
				 */
				@Override
				public void onScroll( AbsListView absListView , int firstVisibleItem , int visibleItemCount , int totalItemCount )
				{
					if(mScrollState== SCROLL_STATE_TOUCH_SCROLL||mScrollState==SCROLL_STATE_FLING)
					{
						float scrollY = (float)mFakeHeader.getTop();// (初始位置为0)

						if ( scrollY > 0 )
						{
							scrollY = 0;
						}
						else if ( scrollY < mMinHeaderTranslation )
						{
							scrollY = mMinHeaderTranslation;
						}

						mHeaderView.setTranslationY(scrollY);
						mSearchView.setTranslationY(scrollY);
						mOnHeaderScrollChangedListener.onHeaderScrollChanged(Math.abs(scrollY / mMinHeaderTranslation));  //绝对值
					}
				}
			});
			
		}
		else
		{
			//如果不是ListView的情况
		}
		mFrameLayout.addView(contentView);
//		mFrameLayout.addView(mTitleView);
		mFrameLayout.addView(mHeaderView);
		mFrameLayout.addView(mSearchView);
		
		return mFrameLayout;
	}

	/**
	 * 搜索框
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public abstract View onCreateSearchView( LayoutInflater inflater , ViewGroup container );

	/**
	 * 类似ActionBar的Title
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public abstract View onCreateTitleView( LayoutInflater inflater , ViewGroup container );

	/**
	 * 头部可上移内容
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public abstract View onCreateHeaderView( LayoutInflater inflater , ViewGroup container );

	/**
	 * 展示列表
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public abstract View onCreateListView( LayoutInflater inflater , ViewGroup container );

	/**
	 * 进度条
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	public abstract View onCreateContentOverlayView( LayoutInflater inflater , ViewGroup container );

}
