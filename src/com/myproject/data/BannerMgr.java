package com.myproject.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.data.db.YFAMDataBase;
import com.myproject.struct.BannerImages;
import com.myproject.util.SettingConfig;

public class BannerMgr extends YFContentDataMgrBase
{

	YFAMDataBase mdbAdDataBase;

	final String AD_UPDATE_TIME = "ad_update_time";

	final long AD_UPDATE_INTERVAL = 3600000;// 1*60*60*1000;间隔时间为1小时

	boolean mbBannerLoading;

	private static BannerMgr obj;
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	static public BannerMgr getInstance( )
	{
		if ( null == obj )
		{
			obj = new BannerMgr();
		}
		return obj;
	}
	public static String request( String httpUrl , String httpArg , String APIKey )
	{
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + "num=" + httpArg;

		try
		{
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", APIKey);
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

	public BannerMgr()
	{
		mdbAdDataBase = YFAMDataBase.getInstance();
	}

	public boolean loadAdvertisementListFromDB( YFDataInterfaceCallback callBack )
	{
		boolean ret = false;
		if ( ! mbBannerLoading && System.currentTimeMillis() - SettingConfig.getInstance().getLongValueByKey(AD_UPDATE_TIME) > AD_UPDATE_INTERVAL )
		{
			return false;
		}

		ArrayList<BannerImages> bannerImages = mdbAdDataBase.getBannnerList();
		if ( null != bannerImages && bannerImages.size() > 0 )
		{
			executeCallbackOnUiThread(200, bannerImages, callBack);
			ret = true;
		}
		return ret;
	}

	public void loadAdvertisementListFromWeb( final int num , final YFDataInterfaceCallback callBack )
	{
		mbBannerLoading = true;
		final String httpUrl = "http://apis.baidu.com/txapi/mvtp/meinv";
		final String httpArg = num + "";
		final String apikey = "cd47e30691764059b86284152f5e4678";
		new Thread(new Runnable()
		{

			@Override
			public void run( )
			{
				ArrayList<BannerImages> BannerImages = new ArrayList<BannerImages>();
				try
				{

					String data = request(httpUrl, httpArg, apikey);
					JSONObject js_data;
					js_data = new JSONObject(data);
					for ( int i = 0; i < num - 1; i++ )
					{
						JSONObject jsonObject = js_data.getJSONObject(i + "");
						BannerImages bannerImage = new BannerImages();
						bannerImage.setDataFromJson(jsonObject);
						BannerImages.add(bannerImage);
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
				synchronized ( mdbAdDataBase )
				{
					mdbAdDataBase.clearTable("bannerImage", null);
					mdbAdDataBase.updataDatas(BannerImages);
				}
				executeCallbackOnUiThread(200, BannerImages, callBack);
			}
		}).start();

		SettingConfig.getInstance().setLongKeyByValue(AD_UPDATE_TIME, System.currentTimeMillis());

		mbBannerLoading = false;
	}
	
	
	public void loadBelowAdvertisementListFromWeb(final int num,final YFDataInterfaceCallback callback)  //加载下面的广告图片，没有做缓存
	{
		mbBannerLoading = true;
		final String httpUrl = "http://apis.baidu.com/txapi/mvtp/meinv";
		final String httpArg = num + "";
		final String apikey = "cd47e30691764059b86284152f5e4678";
		new Thread(new Runnable()
		{

			@Override
			public void run( )
			{
				ArrayList<BannerImages> BannerImages = new ArrayList<BannerImages>();
				try
				{

					String data = request(httpUrl, httpArg, apikey);
					JSONObject js_data;
					js_data = new JSONObject(data);
					for ( int i = 0; i < num - 1; i++ )
					{
						JSONObject jsonObject = js_data.getJSONObject(i + "");
						BannerImages bannerImage = new BannerImages();
						bannerImage.setDataFromJson(jsonObject);
						BannerImages.add(bannerImage);
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
//				synchronized ( mdbAdDataBase )
//				{
//					mdbAdDataBase.clearTable("bannerImage", null);
//					mdbAdDataBase.updataDatas(BannerImages);
//				}
				executeCallbackOnUiThread(200, BannerImages, callback);
			}
		}).start();

		SettingConfig.getInstance().setLongKeyByValue(AD_UPDATE_TIME, System.currentTimeMillis());

		mbBannerLoading = false;
	}
	
	public void getAdvertisementListByNum( int num, YFDataInterfaceCallback callback )
	{
		if ( ! loadAdvertisementListFromDB(callback) )
		{
			loadAdvertisementListFromWeb(num, callback);
		}
	}

}
