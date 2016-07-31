package com.myproject.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

/**
 * @author na.luo
 * 
 */
public class YFAMAutoScrollViewPager extends ViewPager
{
	public static final int DEFAULT_INTERVAL = 1500;

	/** the direction of the next page to appear **/
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	/** do nothing when sliding at the last or first item **/
	public static final int AUTO_SCROLL_MODE_NONE = 0;
	/** cycle when sliding at the last or first item **/
	public static final int AUTO_SCROLL_MODE_SLIDE = 1;

	private int mAutoScrollMode = AUTO_SCROLL_MODE_SLIDE;

	/** auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} **/
	private long interval = DEFAULT_INTERVAL;
	/** auto scroll direction, default is {@link #RIGHT} **/
	private int direction = RIGHT;
	/** whether stop auto scroll when touching, default is true **/
	private boolean stopScrollWhenTouch = true;// 暂未实现，和onClickListener有冲突，功能暂无需求（思路，使用OnLongclickListener控制）

	/** scroll factor for auto scroll animation, default is 1.0 **/
	private double autoScrollFactor = 1.0;
	/** scroll factor for swipe scroll animation, default is 1.0 **/
	private double swipeScrollFactor = 1.0;

	private Handler handler;
	private boolean isAutoScroll = false;
	private boolean isStopByTouch = false;
	private float touchX = 0f , downX = 0f;
	private long touchTime = 0;
	private YFAMCustomDurationScroller scroller = null;

	public static final int SCROLL_WHAT = 0;

	public YFAMAutoScrollViewPager(Context paramContext)
	{
		super(paramContext);
		init();
	}

	public YFAMAutoScrollViewPager(Context paramContext ,
			AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		init();
	}

	private void init( )
	{
		handler = new MyHandler();
		setViewPagerScroller();
	}

	/**
	 * start auto scroll, first scroll delay time is {@link #getInterval()}
	 */
	public void startAutoScroll( )
	{
		isAutoScroll = true;
		sendScrollMessage((long)( interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor ));
	}

	/**
	 * start auto scroll
	 * 
	 * @param delayTimeInMills
	 *            first scroll delay time
	 */
	public void startAutoScroll( int delayTimeInMills )
	{
		isAutoScroll = true;
		sendScrollMessage(delayTimeInMills);
	}

	/**
	 * stop auto scroll
	 */
	public void stopAutoScroll( )
	{
		isAutoScroll = false;
		handler.removeMessages(SCROLL_WHAT);
	}

	/**
	 * set the factor by which the duration of sliding animation will change
	 * while swiping
	 */
	public void setSwipeScrollDurationFactor( double scrollFactor )
	{
		swipeScrollFactor = scrollFactor;
	}

	/**
	 * set the factor by which the duration of sliding animation will change
	 * while auto scrolling
	 */
	public void setAutoScrollDurationFactor( double scrollFactor )
	{
		autoScrollFactor = scrollFactor;
	}

	private void sendScrollMessage( long delayTimeInMills )
	{
		/** remove messages before, keeps one message is running at most **/
		handler.removeMessages(SCROLL_WHAT);
		handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
	}

	/**
	 * set ViewPager scroller to change animation duration when sliding
	 */
	private void setViewPagerScroller( )
	{
		try
		{
			Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
			scrollerField.setAccessible(true);
			Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
			interpolatorField.setAccessible(true);

			scroller = new YFAMCustomDurationScroller(getContext(), (Interpolator)interpolatorField.get(null));
			scrollerField.set(this, scroller);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void setAutoScrollMode( int autoScrollMode )
	{
		mAutoScrollMode = autoScrollMode;
	}

	public int getAutoScrollMode( )
	{
		return mAutoScrollMode;
	}

	/**
	 * scroll only once
	 */
	public void scrollOnce( )
	{
		PagerAdapter adapter = getAdapter();
		int currentItem = getCurrentItem();
		int totalCount;
		if ( adapter == null || ( totalCount = adapter.getCount() ) <= 1 )
		{
			return;
		}

		int nextItem = ( direction == LEFT ) ? --currentItem : ++currentItem;
		if ( mAutoScrollMode == AUTO_SCROLL_MODE_NONE )
		{
			setCurrentItem(nextItem % totalCount, false);
		}
		else if ( mAutoScrollMode == AUTO_SCROLL_MODE_SLIDE )
		{
			setCurrentItem(nextItem, true);
		}
	}

	@Override
	public boolean onInterceptTouchEvent( MotionEvent ev )
	{
		return super.onInterceptTouchEvent(ev);
	}

	private class MyHandler extends Handler
	{

		@Override
		public void handleMessage( Message msg )
		{
			super.handleMessage(msg);

			switch ( msg.what )
			{
				case SCROLL_WHAT :
					scroller.setScrollDurationFactor(autoScrollFactor);
					scrollOnce();
					scroller.setScrollDurationFactor(swipeScrollFactor);
					sendScrollMessage(interval + scroller.getDuration());
				default :
					break;
			}
		}
	}

	/**
	 * get auto scroll time in milliseconds, default is
	 * {@link #DEFAULT_INTERVAL}
	 * 
	 * @return the interval
	 */
	public long getInterval( )
	{
		return interval;
	}

	/**
	 * set auto scroll time in milliseconds, default is
	 * {@link #DEFAULT_INTERVAL}
	 * 
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval( long interval )
	{
		this.interval = interval;
	}

	/**
	 * get auto scroll direction
	 * 
	 * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
	 */
	public int getDirection( )
	{
		return ( direction == LEFT ) ? LEFT : RIGHT;
	}

	/**
	 * set auto scroll direction
	 * 
	 * @param direction
	 *            {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
	 */
	public void setDirection( int direction )
	{
		this.direction = direction;
	}

	/**
	 * whether stop auto scroll when touching, default is true
	 * 
	 * @return the stopScrollWhenTouch
	 */
	public boolean isStopScrollWhenTouch( )
	{
		return stopScrollWhenTouch;
	}

	/**
	 * set whether stop auto scroll when touching, default is true
	 * 
	 * @param stopScrollWhenTouch
	 */
	public void setStopScrollWhenTouch( boolean stopScrollWhenTouch )
	{
		this.stopScrollWhenTouch = stopScrollWhenTouch;
	}

}
