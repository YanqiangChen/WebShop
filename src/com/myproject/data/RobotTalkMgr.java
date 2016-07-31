package com.myproject.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.data.db.YFAMDataBase;
import com.myproject.struct.TalkMessage;
import com.myproject.util.SettingConfig;

public class RobotTalkMgr extends YFContentDataMgrBase
{
	static RobotTalkMgr robotTalk;
	
	YFAMDataBase mdbAdDataBase;
	String textMessage;
	static public RobotTalkMgr getInstance( )
	{
		if ( robotTalk == null )
		{
			robotTalk = new RobotTalkMgr();
		}
		return robotTalk;
	}

	public RobotTalkMgr()
	{
		mdbAdDataBase = YFAMDataBase.getInstance();
	}
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request( String httpUrl , String httpArg )
	{
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try
		{
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "cd47e30691764059b86284152f5e4678");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ( ( strRead = reader.readLine() ) != null )
			{
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void loadTalkMessagesFromWeb(String message,final YFDataInterfaceCallback callback)
	{
		try
		{
			message = URLEncoder.encode(message, "UTF-8");
		}
		catch ( UnsupportedEncodingException e1 )
		{
			e1.printStackTrace();
		}
		final String httpUrl="http://apis.baidu.com/turing/turing/turing";
		final String httpArg="key="+"879a6cb3afb84dbf4fc84a1df2ab7319"+"&info="+message;
		new Thread(new Runnable()
		{
			TalkMessage talkMessage=new TalkMessage();
			@Override
			public void run( )
			{
				try
				{
					String data=request(httpUrl,httpArg);
					JSONObject js_data = new JSONObject(data);
					
					talkMessage.setDataFromJson(js_data);
				}
				catch ( JSONException e )
				{
					e.printStackTrace();
				}
				catch ( Exception e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized ( mdbAdDataBase )
				{
//					mdbAdDataBase.clearTable("bannerImage", null);
					mdbAdDataBase.updataData(talkMessage);
				}
				executeCallbackOnUiThread(200,textMessage,callback);
				
				
				
			}
		}).start();
		
		
	}
}
