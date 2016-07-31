package com.myproject.struct;

import org.json.JSONObject;

public class BannerImages implements YFMBaseStruct
{

	private String picurl;
	private String title;
	private String description;
	static final public String TABLE_NAME = "bannerImage";
	static final public String PICURL = "picurl";
	static final public String TITLE = "title";
	static final public String DESCRIPTION = "description";

	public String getPicurl( )
	{
		return picurl;
	}

	public void setPicurl( String picurl )
	{
		this.picurl = picurl;
	}

	public String getTitle( )
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	@Override
	public void setDataFromJson( JSONObject json ) throws Exception
	{

		picurl = json.getString("picUrl");
		title = json.getString("title");
		description = json.getString("description");
	}

	public static String buildCreateSQL( )
	{
		String CREATE_TABLE = "create table bannerImage(id integer primary key autoincrement,title text,picurl text,description text)";
		return CREATE_TABLE;
	}

	@Override
	public String buildDeleteSQL( )
	{
		return null;
	}

	@Override
	public String buildUpdateSQL( )
	{
		String str = String.format("insert into %s(%s,%s,%s)values(\'%s\',\'%s\',\'%s\');", TABLE_NAME, TITLE, PICURL, DESCRIPTION, title, picurl, description);
		return str;
	}

}
