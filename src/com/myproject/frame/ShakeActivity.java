package com.myproject.frame;

import com.example.weatherreport.R;
import com.myproject.frame.ShakeFragment.OnShakeListener;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ShakeActivity extends FragmentActivity
{

	private final static String className = ShakeActivity.class.getName();
	private final String FRAGMENT_TAG_SHAKE = className + ".shake";
	public static final String IS_SUPPORT_SHAKING = className + ".is_support_shaking";
	
	private ShakeFragment shakeFragment;
	
	//传感器相关
	SensorManager sensorManager;
	Sensor sensor;
	private Vibrator mVibrator;       //震动服务是震动哦
	
	private boolean mIsSupportShaking;   //是否支持摇一摇功能
	private boolean mIsShaking;
	
	private FragmentManager fm;  
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shake);
		mIsSupportShaking=isSupportShaking();
		mVibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		
		fm=getSupportFragmentManager();
		showShakingView();
	}

	
	
	@Override
	protected void onResume( )
	{
		if(sensor!=null)
		{
			sensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
		}
		super.onResume();
	}

 
	@Override
	protected void onPause( )
	{

		if(sensor!=null)
		{
			sensorManager.unregisterListener(mSensorEventListener);
		}
		super.onPause();
	}


	private SensorEventListener mSensorEventListener=new SensorEventListener()
	{
		
		@Override
		public void onSensorChanged( SensorEvent event )
		{
			if ( Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2) >= 2 * Math.pow(17, 2) && ! mIsShaking )
			{
				mVibrator.vibrate(500);
				mIsShaking=true;
				showShakingView();
				shakeFragment.startShakeAnimation();
			}
			
		}
		
		@Override
		public void onAccuracyChanged( Sensor arg0 , int arg1 )
		{
			
		}
	};

	private void showShakingView( )
	{
		shakeFragment=(ShakeFragment)fm.findFragmentByTag(FRAGMENT_TAG_SHAKE);
		if(shakeFragment==null)
		{
			shakeFragment=new ShakeFragment();
			Bundle bundle=new Bundle();
			bundle.putBoolean(IS_SUPPORT_SHAKING, mIsSupportShaking);
			shakeFragment.setArguments(bundle);
			shakeFragment.setOnShakeResultListener(mOnShakeListener);
			fm.beginTransaction().add(R.id.fr_shake_content, shakeFragment, FRAGMENT_TAG_SHAKE).commitAllowingStateLoss();
		}
		else
		{
			fm.beginTransaction().show(shakeFragment).commitAllowingStateLoss();
		}
	}


	private OnShakeListener mOnShakeListener=new OnShakeListener()
	{
		
		@Override
		public void onShakingAnimationEnd( )
		{
			showShakingResultView();
		}
	};

	/**
	 * 摇一摇结果的界面
	 * 
	 * @return
	 */
	
	public void showShakingResultView()
	{
		
	}
	
	
//	private void showShakingResultView( )
//	{
//		mShakeResultFragment = (YFAMShakeResultFragment)fm.findFragmentByTag(FRAGMENT_TAG_SHAKE_RESULT);
//		if ( mShakeResultFragment == null )
//		{
//			mShakeResultFragment = new YFAMShakeResultFragment();
//			mShakeResultFragment.setOnShakeResultListener(mOnShakeResultListener);
//			fm.beginTransaction().add(R.id.fr_shake_content, mShakeResultFragment, FRAGMENT_TAG_SHAKE_RESULT).commitAllowingStateLoss();
//		}
//		else
//		{
//			hideShakingView();
//			fm.beginTransaction().show(mShakeResultFragment).commitAllowingStateLoss();
//			mShakeResultFragment.loadData();
//		}
//	}
	/**
	 * 手机是否支持摇一摇功能
	 * 
	 * @return boolean 返回true支持摇一摇 否则不支持
	 */
	public boolean isSupportShaking()
	{
		boolean isSupportShaking=false;
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);   //获取系统传感器的管理类
		sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);        //获得加速度传感器
		
		if(sensor==null)
		{
			isSupportShaking=false;
		}
		else
		{
			isSupportShaking=true;
		}
		return isSupportShaking;
	}
}
