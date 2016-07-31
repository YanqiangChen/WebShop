package com.myproject.data;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

@SuppressLint( "HandlerLeak" )
public abstract class YFContentDataMgrBase
{
	// //////////////////////////////////////////////////////////////////////////////
	final static int EXECUTE_CALLBACK = 10001;// 在主线程内执行回调接口
	// //////////////////////////////////////////////////////////////////////////////

	public Handler mHandler = new Handler(Looper.getMainLooper())
	{
		@Override
		public void handleMessage( Message msg )
		{
			try
			{
				switch ( msg.what )
				{
					case EXECUTE_CALLBACK :
						CallBackTask task = (CallBackTask)msg.obj;
						( (YFDataInterfaceCallback)task.callback ).onDataResult(task.code, task.data);
						break;

					default :
						break;
				}
			}
			catch ( Exception e )
			{
				// e.printStackTrace();
			}
			super.handleMessage(msg);
		}
	};

	class CallBackTask
	{
		public int code;
		public Object data;
		public Object callback;

		public CallBackTask(int code , Object data , Object callback)
		{
			this.code = code;
			this.data = data;
			this.callback = callback;
		}
	}


	protected void executeCallbackOnUiThread( int code , Object data , YFDataInterfaceCallback callback )
	{
		if ( null != callback )
		{
			Message msg = mHandler.obtainMessage(EXECUTE_CALLBACK);
			msg.obj = new CallBackTask(code, data, callback);
			mHandler.sendMessage(msg);
		}
	}



}
