package com.myproject.frame;

import com.example.weatherreport.R;
import com.myproject.frame.view.share.ShareHandler;
import com.myproject.struct.GameMessages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class ShareForGameDetailActivity extends Activity implements OnClickListener
{

	ShareHandler mShareHandler;
	GameMessages gameMessage;
	String iconUrl;
	String name;
	String description;
	String downLoadUrl;
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_for_app);
		findViewById(R.id.share_sina_weibo).setOnClickListener(this);
		findViewById(R.id.share_friend_circle).setOnClickListener(this);
		findViewById(R.id.share_weixin).setOnClickListener(this);
		findViewById(R.id.shared_qq_qzone).setOnClickListener(this);
	    Bundle bundle=getIntent().getExtras();
	    name=bundle.getString("name");
	    description=bundle.getString("description");
	    iconUrl=bundle.getString("iconUrl");
	    downLoadUrl=bundle.getString("DownloadUrl");
	}

	@Override
	public void onClick( View view )
	{
		String platform=null;
		switch ( view.getId() )
		{
			case R.id.share_sina_weibo :
				platform = "新浪微博";
				doShare(platform);
				break;
			case R.id.share_friend_circle:
				platform = "朋友圈";
				doShare(platform);
				break;
			case R.id.share_weixin:
				platform = "微信好友";
				doShare(platform);
				break;
			case R.id.shared_qq_qzone:
				platform = "QQ空间";
				doShare(platform);
				break;
		}
	}

	public void doShare(String platform)
	{
		
		
		mShareHandler=new ShareHandler(this);
		mShareHandler.setTargetUrl(downLoadUrl);
		mShareHandler.setPlatform(ShareHandler.getPlatform(platform));
		mShareHandler.setImage(iconUrl);
		mShareHandler.setTitle(name);
		mShareHandler.setContent(description);
		mShareHandler.doShare(true);
	}
	
}
