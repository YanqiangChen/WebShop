package com.myproject.frame.view.animation;


import com.example.weatherreport.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class ShakingResultView extends View
{

	// 定义了一些自定义的属性
	private float mCenterCircleRadius;//
	private float mOutCircleRadius; //
	private float mRingRadius; //
	private int mCenterCircleColor;
	private int mOutCircleColor;
	private int mRingColor;
	private int mRingWidth;

	// 定义画笔
	private Paint mpCenterCircle;
	private Paint mpOutCircle;
	private Paint mpRing;

	// 定义视图的高度宽度 定义视图中心
	private int mViewHeight;
	private int mViewWidth;
	private int mViewCenterX;
	private int mViewCenterY;

	// 每一帧变化的时候都会对这个属性赋值
	private float mfAppScaleRate = 0;

	public ShakingResultView(Context context , AttributeSet attrs ,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ShakingResultView(Context context , AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShakingResultAnimation, 0, 0);

		// 获取属性
		mCenterCircleRadius = (int)a.getDimension(R.styleable.ShakingResultAnimation_centerCircleRadius, 0);
		mOutCircleRadius = (int)a.getDimension(R.styleable.ShakingResultAnimation_outCircleRadius, 0);
		mRingRadius = (int)a.getDimension(R.styleable.ShakingResultAnimation_ringRadius, 0);
		mRingWidth = (int)a.getDimension(R.styleable.ShakingResultAnimation_strokeWidth, 0);
		mCenterCircleColor = a.getColor(R.styleable.ShakingResultAnimation_centerCircleColor, Color.BLACK);
		mOutCircleColor = a.getColor(R.styleable.ShakingResultAnimation_outCircleColor, Color.BLACK);
		mRingColor = a.getColor(R.styleable.ShakingResultAnimation_ringColor, Color.BLACK);

		// 不得设置Padding值，如果设置了，在这里归0一下
		setPadding(0, 0, 0, 0);
		a.recycle();
		init();

	}

	/**
	 * 对画笔初始化
	 * 
	 * @return
	 */
	private void init( )
	{
		// 中心圆的画笔
		mpCenterCircle = new Paint();
		mpCenterCircle.setColor(mCenterCircleColor);
		mpCenterCircle.setAntiAlias(true);
		// 外圈的画笔
		mpOutCircle = new Paint();
		mpOutCircle.setColor(mOutCircleColor);
		mpOutCircle.setAntiAlias(true);
		// 圆环的画笔
		mpRing = new Paint();
		mpRing.setColor(mRingColor);
		mpRing.setStyle(Style.STROKE);
		mpRing.setStrokeWidth(mRingWidth);
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		super.onDraw(canvas);
		canvas.drawCircle(mViewCenterX, mViewCenterY, mCenterCircleRadius * mfAppScaleRate, mpCenterCircle);
		canvas.drawCircle(mViewCenterX, mViewCenterY, mOutCircleRadius * mfAppScaleRate, mpOutCircle);
		canvas.drawCircle(mViewCenterX, mViewCenterY, mRingRadius * mfAppScaleRate, mpRing);
	}

	@Override
	protected void onMeasure( int widthMeasureSpec , int heightMeasureSpec )
	{
		mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
		mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
		mViewCenterX = mViewWidth / 2 + getLeft();
		mViewCenterY = mViewHeight / 2 + getRight();
		setMeasuredDimension(resolveSize(mViewWidth, widthMeasureSpec), resolveSize(mViewHeight, heightMeasureSpec));

	}

	public void startAppearAnimation( )
	{
		ValueAnimator appearAnimator = ValueAnimator.ofFloat(0f, 1f);
		appearAnimator.setDuration(500);
		appearAnimator.addUpdateListener(new AnimatorUpdateListener()
		{

			@Override
			public void onAnimationUpdate( ValueAnimator animation )
			{
				mfAppScaleRate = (Float)animation.getAnimatedValue(); // 获得每一帧的值
				postInvalidate(); // 华丽而又逆袭的重绘
			}
		});
		appearAnimator.addListener(new AnimatorListener()
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
				if(mOnCenterAnimationListener!=null)
				{
					mOnCenterAnimationListener.onCenterAppearAnimationEnd(mViewCenterX, mViewCenterY);
				}
				
			}

			@Override
			public void onAnimationCancel( Animator arg0 )
			{

			}
		});
	}

	public interface OnCenterAnimationListener
	{

		public void onCenterAppearAnimationEnd( float centerX , float centerY );
	}

	private OnCenterAnimationListener mOnCenterAnimationListener;

	public void setOnCenterAnimationListener( OnCenterAnimationListener onCenterAnimationListener )
	{
		this.mOnCenterAnimationListener = onCenterAnimationListener;
	}

	public OnCenterAnimationListener getOnCenterAnimationListener( )
	{
		return this.mOnCenterAnimationListener;
	}
}
