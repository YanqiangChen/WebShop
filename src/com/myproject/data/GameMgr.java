package com.myproject.data;

import java.util.ArrayList;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.data.db.YFAMDataBase;
import com.myproject.struct.BannerImages;
import com.myproject.struct.GameMessages;
import com.myproject.util.SettingConfig;

import cn.readphone.util.HttpUtil;
import cn.readphone.util.HttpUtil.HttpCallback;

public class GameMgr extends YFContentDataMgrBase
{
	GameMessages gameMessage;
	ArrayList<GameMessages> gameMessages;
	public static GameMgr obj;

	YFAMDataBase mdbAdDataBase;

	boolean mbGameLoading;
	final String GAME_UPDATE_TIME = "game_update_time";

	final long GAME_UPDATE_INTERVAL = 3600000;// 1*60*60*1000;间隔时间为1小时

	private GameMgr()
	{
		mdbAdDataBase = YFAMDataBase.getInstance();
	}

	public static GameMgr getInstance( )
	{
		if ( null == obj )
		{
			obj = new GameMgr();
		}
		return obj;
	}

	public void getGameMessage( final YFDataInterfaceCallback callback )
	{
		if(!loadGameRandFromDB(callback))
		{
			loadGameRandFromWeb(callback);
		}
	}
	public boolean loadGameRandFromDB( final YFDataInterfaceCallback callback )
	{
		boolean ret = false;
		if ( ! mbGameLoading && System.currentTimeMillis() - SettingConfig.getInstance().getLongValueByKey(GAME_UPDATE_TIME) > GAME_UPDATE_INTERVAL )
		{
			return false;
		}
		ArrayList<GameMessages> gameMessages = mdbAdDataBase.getGameMessagesList();
		if ( null != gameMessages && gameMessages.size() > 0 )
		{
			executeCallbackOnUiThread(200, gameMessages, callback);
			ret = true;
		}
		return ret;

	}

	public void loadGameRandFromWeb( final YFDataInterfaceCallback callback )
	{
		mbGameLoading = true;
		gameMessages = new ArrayList<GameMessages>();
		final String Url = "http://211.152.38.225/Service/Store/getRandData";
		new HttpUtil(HttpUtil.HTTPGET, Url, new HttpCallback()
		{

			@Override
			public void onResult( int code , String result )
			{
				if ( code == 200 )
				{
					try
					{
						JSONObject jsonData = new JSONObject(result).getJSONObject("data");
						JSONArray jsonArray = jsonData.getJSONArray("info");
						for ( int i = 0; i < jsonArray.length(); i++ )
						{
							gameMessage = new GameMessages();
							gameMessage.setDataFromJson(jsonArray.getJSONObject(i));
							gameMessages.add(gameMessage);
						}

					}
					catch ( JSONException e )
					{
						e.printStackTrace();
					}
					catch ( Exception e )
					{
						e.printStackTrace();
					}

					synchronized ( mdbAdDataBase )
					{
						mdbAdDataBase.clearTable("gameMessage", null);
						mdbAdDataBase.updataDatas(gameMessages);
					}
					executeCallbackOnUiThread(200, gameMessages, callback);
				}
			}
		}).start();
		mbGameLoading = false;
		SettingConfig.getInstance().setLongKeyByValue(GAME_UPDATE_TIME, System.currentTimeMillis());
	}

}
