package com.myproject.struct;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class TalkMessage implements YFMBaseStruct
{
	
	private String content;
	private String date;
	private String type;
	
	static final public String TEABLE_NAME="talkMessage";
	static final public String CONTENT="content";
	static final public String DATE="date";
	static final public String TYPE="type";
	public String getContent( )
	{
		return content;
	}
	public void setContent( String content )
	{
		this.content = content;
	}
	public String getDate( )
	{
		return date;
	}
	public void setDate( String date )
	{
		this.date = date;
	}
	public String getType( )
	{
		return type;
	}
	public void setType( String type )
	{
		this.type = type;
	}
	@Override
	public void setDataFromJson( JSONObject json ) throws Exception
	{
		content=json.getString("text");
		Date dates = new Date();
		String str = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		date = df.format(dates);
		type="robot";
		
	}
	
	public static String buildCreateSQL()
	{
		String CREATE_TABLE = "create table talkMessage(id integer primary key autoincrement,content text,date text,type text)";
		return CREATE_TABLE;
		
	}
	@Override
	public String buildDeleteSQL( )
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String buildUpdateSQL( )
	{
		String str = String.format("insert into %s(%s,%s,%s)values(\'%s\',\'%s\',\'%s\');", TEABLE_NAME, CONTENT, DATE, TYPE, content, date, type);
		return str;
	}

	
}
