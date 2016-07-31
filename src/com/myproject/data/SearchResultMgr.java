package com.myproject.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.struct.hotSearchResult;

public class SearchResultMgr extends YFContentDataMgrBase
{ 
	
	
	public static SearchResultMgr obj;
	
	public static SearchResultMgr getInstance()
	{
		if(obj==null)
		{
			obj=new SearchResultMgr();
		}
		return obj;
	}
	public static String request( String httpUrl , String httpArg)
	{
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" +  httpArg;

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
	
	public void loadSearchResultFromWeb(final int num,int rand,int page,String word,final YFDataInterfaceCallback callback)
	{
		final String httpUrl="http://apis.baidu.com/txapi/weixin/wxhot";
		try
		{
			word = URLEncoder.encode(word, "UTF-8");
		}
		catch ( UnsupportedEncodingException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String httpArg="num="+num+"&rand="+rand+"&word="+word+"&page="+page+"";
		new Thread(new Runnable()
		{
			@Override
			public void run( )
			{
				ArrayList<hotSearchResult> hotSearchResults=new ArrayList<hotSearchResult>();
				try
				{
					String data=request(httpUrl,httpArg);
					JSONObject jsonData=new JSONObject(data);
					for(int i=0;i<num;i++)
					{
						JSONObject jsonObject=jsonData.getJSONObject(i+"");
						hotSearchResult mHotSearchResult=new hotSearchResult();
						mHotSearchResult.setDataFromJson(jsonObject);
						hotSearchResults.add(mHotSearchResult);
					}
				}
				catch ( JSONException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( Exception e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				executeCallbackOnUiThread(200, hotSearchResults, callback);
			}
		}).start();
		
	}
}


















