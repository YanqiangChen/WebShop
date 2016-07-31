package com.myproject.frame;

import java.util.ArrayList;
import java.util.List;


import com.example.weatherreport.R;
import com.myproject.frame.view.animation.Star;
import com.myproject.frame.view.animation.StartSet;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShakeFragment extends Fragment
{
	boolean mIsSupportShaking = true;// 是否支持摇一摇功能
	OnShakeListener onShareListener;
	View mRootView;
	TextView mtvShakeTitle;
	ImageView mivShake;
	RelativeLayout mrlShake;

	private int mCenterX;
	private int mCenterY;

	public void setOnShakeResultListener( OnShakeListener onShareListener )
	{
		this.onShareListener = onShareListener;
	}

	public OnShakeListener getOnShakeResultListener( )
	{
		return onShareListener;
	}

	public interface OnShakeListener
	{
		public void onShakingAnimationEnd( );
	}

	@Override
	public View onCreateView( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
	{
		mIsSupportShaking = getArguments().getBoolean(ShakeActivity.IS_SUPPORT_SHAKING, false);

		mRootView = inflater.inflate(R.layout.layout_shake, null);

		mtvShakeTitle = (TextView)mRootView.findViewById(R.id.tv_shake_title);
		mivShake = (ImageView)mRootView.findViewById(R.id.iv_shake);
		mrlShake = (RelativeLayout)mRootView.findViewById(R.id.rl_shake);

		initShakeView();

		return mRootView;
	}

	private void initShakeView( )
	{
		if ( ! mIsSupportShaking )
		{
			mtvShakeTitle.setText("您的手机不支持摇一摇功能");
			mivShake.setVisibility(View.GONE);
		}
	}

	// public void startShakeAnimation( )
	// {
	// // 设置动画起始位置
	// mCenterX = mivShake.getLeft() + mivShake.getWidth() / 2;
	// mCenterY = mivShake.getTop() + mivShake.getHeight() / 2;
	//
	// // 子动画视图
	// final StartSet startSet = new StartSet(getActivity());
	// startSet.addStar(20, mrlShake.getWidth(), mrlShake.getHeight(), mCenterX,
	// mCenterY);
	// List<Star> stars = startSet.getStars();
	// for ( int i = 0; i < stars.size(); i++ )
	// {
	// mrlShake.addView(stars.get(i).mivStar);
	// stars.get(i).startAnimation();
	// }
	//
	// // 动画
	// ObjectAnimator animatorShake = ObjectAnimator.ofFloat(mivShake,
	// "Rotation", 0, 45, 0);
	// animatorShake.setDuration(200); //设置动画时间
	// animatorShake.setRepeatCount(10);

	public void startShakeAnimation( )
	{
		// 动画的开始位置
		mCenterX = mivShake.getLeft() + mivShake.getWidth() / 2;
		mCenterY = mivShake.getTop() + mivShake.getHeight() / 2;

		// 星星漫天弥漫的动画视图
		final StartSet startSet=new StartSet(getActivity());
		startSet.addStar(30, mrlShake.getHeight(), mrlShake.getWidth(), mCenterX, mCenterY);
		ArrayList<Star> stars=startSet.getStars();
		for(int i=0;i<stars.size();i++)
		{
			mrlShake.addView(stars.get(i).mivStar);
			stars.get(i).startAnimation();
		}
		
		// 动画
		ObjectAnimator animatorShake = ObjectAnimator.ofFloat(mivShake, "Rotation", 0, 45, 0);
		animatorShake.setDuration(200);
		animatorShake.setRepeatCount(10);
		animatorShake.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart( Animator arg0 )
			{

			}

			@Override
			public void onAnimationRepeat( Animator arg0 )
			{

			}

			@Override
			public void onAnimationEnd( Animator arg0 )
			{
				startSet.clear();
				onShareListener.onShakingAnimationEnd();
			}

			@Override
			public void onAnimationCancel( Animator arg0 )
			{

			}
		});
		animatorShake.start();

	}
}
