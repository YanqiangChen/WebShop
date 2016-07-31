package com.myproject.app;



import android.app.Application;

public class YFRUApplication extends Application
{
	static public YFRUApplication obj;

	@Override
	public void onCreate( )
	{
		super.onCreate();
	}
	
	public YFRUApplication()
	{
		obj=this;
	}
	static public YFRUApplication getInstance( )
	{
		return obj;
	}

}
