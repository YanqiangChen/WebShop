package com.myproject.struct;

import org.json.JSONObject;


public interface YFMBaseStruct
{
	public abstract void setDataFromJson( JSONObject json ) throws Exception;

	public abstract String buildDeleteSQL( );

	public abstract String buildUpdateSQL( );
}
