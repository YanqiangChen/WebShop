package com.myproject.struct;

import java.io.Serializable;

import org.json.JSONObject;

public class GameMessages implements YFMBaseStruct,Serializable
{

	private String id;
	private String packageName;
	private String description;
	private String versionName;
	private String name;
	private String rating;
	private String versionCode;
	private String iconUrl;
	private String boxlabel;
	private String downloadTimes;
	private String downloadUrl;
	private String apkSize;
	private int status;
	
	static final public String TABLE_NAME="gameMessage";
	static final public String ID="id";
	static final public String PACKAGE_NAME="packageName";
	static final public String DESCRIPTION="description";
	static final public String VERSION_NAME="versionName";
	static final public String NAME="name";
	static final public String RATING="rating";
	static final public String VERSION_CODE="versionCode";
	static final public String ICON_URL="iconUrl";
	static final public String BOX_LABEL="boxlabel";
	static final public String DOWNLOAD_TIMES="downloadTimes";
	static final public String DOWNLOAD_URL="downloadUrl";
	static final public String APK_SIZE="apkSize";
	
	public String getId( )
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getPackageName( )
	{
		return packageName;
	}

	public void setPackageName( String packageName )
	{
		this.packageName = packageName;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getVersionName( )
	{
		return versionName;
	}

	public void setVersionName( String versionName )
	{
		this.versionName = versionName;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getRating( )
	{
		return rating;
	}

	public void setRating( String rating )
	{
		this.rating = rating;
	}

	public String getVersionCode( )
	{
		return versionCode;
	}

	public void setVersionCode( String versionCode )
	{
		this.versionCode = versionCode;
	}

	public String getIconUrl( )
	{
		return iconUrl;
	}

	public void setIconUrl( String iconUrl )
	{
		this.iconUrl = iconUrl;
	}

	public String getBoxlabel( )
	{
		return boxlabel;
	}

	public void setBoxlabel( String boxlabel )
	{
		this.boxlabel = boxlabel;
	}

	public String getDownloadTimes( )
	{
		return downloadTimes;
	}

	public void setDownloadTimes( String downloadTimes )
	{
		this.downloadTimes = downloadTimes;
	}

	public String getDownloadUrl( )
	{
		return downloadUrl;
	}

	public void setDownloadUrl( String downloadUrl )
	{
		this.downloadUrl = downloadUrl;
	}

	public String getApkSize( )
	{
		return apkSize;
	}

	public void setApkSize( String apkSize )
	{
		this.apkSize = apkSize;
	}

	@Override
	public void setDataFromJson( JSONObject json ) throws Exception
	{
		id=json.optString("id");
		packageName=json.optString("packageName");
		description=json.optString("description");
		
		versionName=json.optString("versionName");
		name=json.optString("name");
		rating=json.optString("rating");
		
		versionCode=json.optString("versionCode");
		iconUrl=json.optString("iconUrl");
		boxlabel=json.optString("boxLabel");
		
		downloadTimes=json.optString("downloadTimes");
		downloadUrl=json.optString("downloadUrl");
		apkSize=json.optString("apkSize");
	}

	public static String buildCreateSQL()
	{
		String CREATE_TABLE = "create table gameMessage(id text,packageName text,description text,versionName text,name text,rating text,versionCode text,iconUrl text,boxlabel text,downloadTimes text,downloadUrl text,apkSize text)";
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
		String str = String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)values(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');", TABLE_NAME, ID, PACKAGE_NAME, DESCRIPTION, VERSION_NAME, NAME,RATING,VERSION_CODE,ICON_URL,BOX_LABEL,DOWNLOAD_TIMES,DOWNLOAD_URL,APK_SIZE,id,packageName,description,versionName,name,rating,versionCode,iconUrl,boxlabel,downloadTimes,downloadUrl,apkSize);
		return str;
	}

	public int getStatus( )
	{
		return status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

}
