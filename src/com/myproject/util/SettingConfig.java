package com.myproject.util;

import com.myproject.app.YFRUApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingConfig
{
	static SettingConfig singleObj;
	SharedPreferences mspConfig;
	Context mContext;
	protected String KEY_SETTING_CONFIG_NAME = "yf_am_setting_config";
	
	public SettingConfig(Context context)
	{
		mContext=context;
		mspConfig=context.getSharedPreferences(KEY_SETTING_CONFIG_NAME, Context.MODE_PRIVATE);
		
	}
	
	public static SettingConfig getInstance()
	{
		if(singleObj==null)
		{
			singleObj=new SettingConfig(YFRUApplication.getInstance());
		}
		return singleObj;
	}
	
	public void setLongKeyByValue(String key,long value)
	{
		mspConfig.edit().putLong(key, value).commit();
	}
	
	public long getLongValueByKey(String key)
	{
		return mspConfig.getLong(key, -1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
