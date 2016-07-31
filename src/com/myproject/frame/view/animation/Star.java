package com.myproject.frame.view.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.widget.ImageView;

public class Star
{

	public ImageView mivStar;

	// 开始的坐标
	float mStartX;
	float mStartY;
	// 结束的坐标
	float mEndX;
	float mEndY;

	int mStartTime;
	public Star(Context context ,int imageResource, float mStartX , float mStartY ,
			double mEndX , double mEndY , int mStartTime)
	{
		mivStar = new ImageView(context);
		mivStar.setImageResource(imageResource);
		this.mStartX = mStartX;
		this.mStartY = mStartY;
		this.mEndX = (float)mEndX;
		this.mEndY = (float)mEndY;
		this.mStartTime = mStartTime;
	}

	/**
	 * 根据star的属性，开始动画
	 * 
	 * @return
	 */
	public void startAnimation( )
	{
		ObjectAnimator animationTransX = ObjectAnimator.ofFloat(mivStar, "TranslationX", mStartX, mEndX);
		ObjectAnimator animationTransY=ObjectAnimator.ofFloat(mivStar, "TranslationY", mStartY,mEndY);
		ObjectAnimator animationAlpha=ObjectAnimator.ofFloat(mivStar, "Alpha", 255,0);
		AnimatorSet animatiorSet = new AnimatorSet();
		animatiorSet.play(animationTransX).with(animationTransY).with(animationAlpha);
		animatiorSet.setDuration(6000-this.mStartTime);
		animatiorSet.start();
	}

}
