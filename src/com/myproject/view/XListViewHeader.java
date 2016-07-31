package com.myproject.view;

/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */

import com.example.weatherreport.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XListViewHeader extends LinearLayout
{
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context)
	{
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context , AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	private void initView( Context context )
	{
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar)findViewById(R.id.xlistview_header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, - 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(- 180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	public void setState( int state )
	{
		if ( state == mState )            //mState初始化为正常状态
			return;

		if ( state == STATE_REFRESHING )                //正在刷新状态
		{ 
			mArrowImageView.clearAnimation();       
			mArrowImageView.setVisibility(View.INVISIBLE);        //mArrowImageView设置为不显示
			mHintTextView.setText("正在刷新...");
//			mProgressBar.setVisibility(View.VISIBLE);             //进度条设置为显示
		}
		else
		{  
			mArrowImageView.setVisibility(View.VISIBLE);          //mArrowImageView设置为
			mProgressBar.setVisibility(View.INVISIBLE);           //进度条设置为不显示
		}

		switch ( state )         //判断状态
		{
			case STATE_NORMAL :       //如果为正常状态
				if ( mState == STATE_READY )          //mStatess为准备状态
				{
					mArrowImageView.startAnimation(mRotateDownAnim);
				}
				if ( mState == STATE_REFRESHING )    
				{
					mArrowImageView.clearAnimation();
				}
				mHintTextView.setText("下拉刷新");
				break;
			case STATE_READY :
				if ( mState != STATE_READY )
				{
					mArrowImageView.clearAnimation();
					mArrowImageView.startAnimation(mRotateUpAnim);
					mHintTextView.setText("松开刷新");
				}
				break;
			case STATE_REFRESHING :
				mHintTextView.setText("正在刷新...");
				break;
			default :
		}

		mState = state;
	}

	public void setVisiableHeight( int height )
	{
		if ( height < 0 )
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight( )
	{
		return mContainer.getHeight();
	}

}
