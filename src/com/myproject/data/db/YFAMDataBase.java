package com.myproject.data.db;

import java.util.ArrayList;

import com.myproject.app.YFRUApplication;
import com.myproject.struct.BannerImages;
import com.myproject.struct.GameMessages;
import com.myproject.struct.TalkMessage;
import com.myproject.struct.YFMBaseStruct;
import com.myproject.util.SettingConfig;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class YFAMDataBase extends SQLiteOpenHelper
{
	static String DB_NAME = "data_cache.db";
	static int DB_VER_CODE = 2;
	static YFAMDataBase obj;

	public YFAMDataBase(Context context , String name , CursorFactory factory ,
			int version)
	{
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	protected YFAMDataBase(Context context)
	{
		super(context, DB_NAME, null, DB_VER_CODE);
	}

	static public YFAMDataBase getInstance( )
	{
		if ( null == obj )
		{
			obj = new YFAMDataBase(YFRUApplication.getInstance());
		}
		return obj;
	}

	@Override
	public void onCreate( SQLiteDatabase db )
	{
		try
		{
			db.execSQL(BannerImages.buildCreateSQL());
			db.execSQL(TalkMessage.buildCreateSQL());
			db.execSQL(GameMessages.buildCreateSQL());
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade( SQLiteDatabase db , int oldVersion , int newVersion )
	{
		// TODO Auto-generated method stub

	}

	public void updataDatas( ArrayList<? extends YFMBaseStruct> datas )
	{
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try
		{
			for ( YFMBaseStruct yfamBaseStruct : datas )
			{
				db.execSQL(yfamBaseStruct.buildUpdateSQL());
			}
			db.setTransactionSuccessful();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			db.endTransaction();
		}
	}

	public void updataData( YFMBaseStruct data )
	{
		SQLiteDatabase db = getWritableDatabase();
		try
		{
			db.execSQL(data.buildUpdateSQL());
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void clearTable( String table , String where )
	{
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			String sql=String.format("delete from %s ", table);
			db.execSQL(sql);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void execSQL( String sql )
	{
		SQLiteDatabase db = getWritableDatabase();
		try
		{
			db.execSQL(sql);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public Cursor query( String table , String where )
	{
		return getReadableDatabase().rawQuery(String.format("select* from %s where %s;", table, where), null);
	}
	
	public Cursor query( String table )
	{
		return getReadableDatabase().rawQuery(String.format("select* from %s", table), null);
	}

	public ArrayList<TalkMessage> getTalkMessage()
	{
		ArrayList<TalkMessage> talkMessages=null;
		SQLiteDatabase db = getReadableDatabase();
		String sql="select * from talkMessage";
		Cursor cursor = db.rawQuery(sql, null);
		if ( cursor.moveToFirst() )
		{
			int count=cursor.getCount();
			talkMessages=new ArrayList<TalkMessage>(count);
			do
			{
				TalkMessage talkMessage=new TalkMessage();
				talkMessage.setContent(cursor.getString(cursor.getColumnIndex("content")));
				talkMessage.setDate(cursor.getString(cursor.getColumnIndex("date")));
				talkMessage.setType(cursor.getString(cursor.getColumnIndex("type")));
				talkMessages.add(talkMessage);
			} while ( cursor.moveToNext() );
		}
		return talkMessages;
		
	}
	
	public ArrayList<GameMessages> getGameMessagesList()
	{
		ArrayList<GameMessages> alGameMessages=null;
		SQLiteDatabase db = getReadableDatabase();
		String sql="select * from gameMessage";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst())
		{
			int count=cursor.getCount();
			alGameMessages=new ArrayList<GameMessages>(count);
			do
			{
				GameMessages gameMessage=new GameMessages();
				gameMessage.setId(cursor.getString(cursor.getColumnIndex("id")));
				gameMessage.setPackageName(cursor.getString(cursor.getColumnIndex("packageName")));
				gameMessage.setDescription(cursor.getString(cursor.getColumnIndex("description")));
				gameMessage.setVersionName(cursor.getString(cursor.getColumnIndex("versionName")));
				gameMessage.setName(cursor.getString(cursor.getColumnIndex("name")));
				gameMessage.setRating(cursor.getString(cursor.getColumnIndex("rating")));
				gameMessage.setVersionCode(cursor.getString(cursor.getColumnIndex("versionCode")));
				gameMessage.setIconUrl(cursor.getString(cursor.getColumnIndex("iconUrl")));
				gameMessage.setBoxlabel(cursor.getString(cursor.getColumnIndex("boxlabel")));
				gameMessage.setDownloadTimes(cursor.getString(cursor.getColumnIndex("downloadTimes")));
				gameMessage.setDownloadUrl(cursor.getString(cursor.getColumnIndex("downloadUrl")));
				gameMessage.setApkSize(cursor.getString(cursor.getColumnIndex("apkSize")));
				alGameMessages.add(gameMessage);
			} while ( cursor.moveToNext() );
		}
		return alGameMessages;
		
	}
	public ArrayList<BannerImages> getBannnerList( )
	{
		ArrayList<BannerImages> bannerImages=null;
		try
		{
			SQLiteDatabase db = getReadableDatabase();
			String sql = "select * from bannerImage ";
			Cursor cursor = db.rawQuery(sql, null);
			if ( cursor.moveToFirst() )
			{
				int count=cursor.getCount();
				bannerImages=new ArrayList<BannerImages>(count);
				do
				{
					BannerImages bannerImage=new BannerImages();
					bannerImage.setDescription(cursor.getString(cursor.getColumnIndex("description")));
					bannerImage.setPicurl(cursor.getString(cursor.getColumnIndex("picurl")));
					bannerImage.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					bannerImages.add(bannerImage);
				} 
				while ( cursor.moveToNext() );
				
				
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return bannerImages;

	}

}
