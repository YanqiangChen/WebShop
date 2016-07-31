package com.myproject.struct;

import org.json.JSONObject;

public class hotSearchResult implements YFMBaseStruct
{
	private String hottime;
	private String title;
	private String description;
	private String picUrl;
	private String url;
	static final public String TABLE_NAME="searchResult";
	static final public String TITLE="title";
	static final public String DESCRIPTION="description";
	static final public String PICURL="picUrl";
	static final public String URL="url";
	public String getHottime( )
	{
		return hottime;
	}
	public void setHottime( String hottime )
	{
		this.hottime = hottime;
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
	public String getPicUrl( )
	{
		return picUrl;
	}
	public void setPicUrl( String picUrl )
	{
		this.picUrl = picUrl;
	}
	public String getUrl( )
	{
		return url;
	}
	public void setUrl( String url )
	{
		this.url = url;
	}
	@Override
	public void setDataFromJson( JSONObject json ) throws Exception
	{
		hottime=json.getString("hottime");
		title=json.getString("title");
		description=json.getString("description");
		picUrl=json.getString("picUrl");
		url=json.getString("url"); 
	}
	@Override
	public String buildDeleteSQL( )
	{
		return null;
	}
	@Override
	public String buildUpdateSQL( )
	{
		return null;
	}
}
