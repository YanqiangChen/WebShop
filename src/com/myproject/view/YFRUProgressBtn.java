package com.myproject.view;

import com.example.weatherreport.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class YFRUProgressBtn implements OnClickListener
{
	View mvDownloadBtn;
	TextView mtvPbState;
	Activity mContext;
	ProgressBar showProgress;
	Object mObject;
	public YFRUProgressBtn(Activity mContext)
	{
		this.mContext = mContext;
		initView();
	}
	public YFRUProgressBtn(View view)
	{
		this.mvDownloadBtn = view;
		initView();
	}
	private void initView( )
	{
		if ( null == mvDownloadBtn )
		{
			mvDownloadBtn = mContext.findViewById(R.id.tool_bar);
		}
		mtvPbState = (TextView)mvDownloadBtn.findViewById(R.id.detail_tv_pbstate);
		showProgress = (ProgressBar)mvDownloadBtn.findViewById(R.id.classify_pb_showprogress);
		mvDownloadBtn.setOnClickListener(this);
		mtvPbState.setOnClickListener(this);
		showProgress.setOnClickListener(this);
	}

	public void setButtonState( int resid )
	{
		mtvPbState.setText(resid);
	}

	public void setButtonState( String strState )
	{
		mtvPbState.setText(strState);
	}

	public void setProgress( int progress )
	{
		showProgress.setProgress(progress);
	}

	public void setTextViewOnClickListener( View.OnClickListener listener )
	{
//		mtvPbState.setFocusable(true);
//		mtvPbState.setFocusableInTouchMode(true);
//		mtvPbState.requestFocus();
//		mtvPbState.requestFocusFromTouch();
		mtvPbState.setOnClickListener(listener);
	}

	public void setProgressBarOnClickListener( View.OnClickListener listener )
	{
		showProgress.setOnClickListener(listener);
	}

	public void setDownloadBtnOnCLickListener( View.OnClickListener listener )
	{
		mvDownloadBtn.setOnClickListener(listener);
	}
	
	public void hideProgressBar()
	{
		showProgress.setVisibility(View.INVISIBLE);
	}
	public Object getTextTag( )
	{
		return mObject;
	}
	public void setTextTag( Object mObject )
	{
		mtvPbState.setTag(mObject);
		this.mObject = mObject;
	}

	@Override
	public void onClick( View arg0 )
	{

	}

}
