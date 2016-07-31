/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.myproject.view;


import com.example.weatherreport.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XListViewFooter extends LinearLayout
{
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;

	public XListViewFooter(Context context)
	{
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context , AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public void setState( int state )
	{
		mHintView.setVisibility(View.INVISIBLE); // 不显示mHintView
		mProgressBar.setVisibility(View.INVISIBLE); // 不显示进度条
		mHintView.setVisibility(View.INVISIBLE);
		if ( state == STATE_READY )
		{
			mHintView.setVisibility(View.VISIBLE); // 如果为准备状态的话，mHintView设置为显示
			mHintView.setText("松开加载更多"); // 设置Text为松开刷新更多
		}
		else if ( state == STATE_LOADING )
		{ // r如果为加载状态的话
//			mProgressBar.setVisibility(View.VISIBLE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("正在加载...");
		}
		else
		{ // 否则为正常状态
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("上拉加载更多");
		}
	}

	public void setBottomMargin( int height )
	{ // 设置底部的Margin
		if ( height < 0 )
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin( )
	{
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal( )         //正常状态下
	{
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading( )              //加载状态下
	{
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide( )         //隐藏状态下
	{ // 隐藏尾部 把高度设置为0
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show( )
	{ // 显示尾部 把高度有设置成WrapContent
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView( Context context )
	{
		mContext = context;
		LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}

}
