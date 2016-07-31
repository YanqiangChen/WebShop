package com.myproject.frame.view.share;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.UserInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.BaseShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareHandler implements ShareInterface
{
	public static final int REQUEST_CODE = 1;

	private static Map<String, SHARE_MEDIA> mPlatformsMap;
	private Activity mContext;
	UMSocialService mController;
	static shareData mSharedData; // 要分享的数据内容

	public class shareData
	{
		public SHARE_MEDIA platform; // 分享平台
		public String strContent;
		public String strTitle;
		public String strTargetUrl;// 分享标题的点击跳转链接
		public UMImage imgPicture;// 要分享的图片
		public UMVideo mvVideo;// 分享的视频
		public UMusic mMusic;// 要分享的音乐
	}

	/**
	 * 初始化平台map
	 */

	public static SHARE_MEDIA getPlatform( String platform )
	{
		if ( mPlatformsMap == null )
		{
			mPlatformsMap = new HashMap<String, SHARE_MEDIA>();
			mPlatformsMap.put("新浪微博", SHARE_MEDIA.SINA);
			mPlatformsMap.put("朋友圈", SHARE_MEDIA.WEIXIN_CIRCLE);
			mPlatformsMap.put("微信好友", SHARE_MEDIA.WEIXIN);
			mPlatformsMap.put("QQ空间", SHARE_MEDIA.QZONE);
		}
		return mPlatformsMap.get(platform);
	}

	public ShareHandler(Activity activity)
	{
		mContext = activity;
		initShare(mContext);
	}

	public UMSocialService getController( )
	{
		return mController;
	}

	protected void initShareSDK( )
	{
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");

		// qq平台相关参数
		String qq_appId = "1104552976";
		String qq_appKey = "LRINDbfsF7NkuLom";
		new UMQQSsoHandler(mContext, qq_appId, qq_appKey).addToSocialSDK();
		new QZoneSsoHandler(mContext, qq_appId, qq_appKey).addToSocialSDK();

		// 微信平台相关参数
		String wx_appId = "wxc39e0b3275612faa";
		String wx_appSecret = "ef6f8e643822a5e2a52ae60226250d40";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(mContext, wx_appId, wx_appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(mContext, wx_appId, wx_appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 新浪平台
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

	}

	protected void initSharedContent( )
	{
		mSharedData = new shareData();
	}

	@Override
	public void initShare( Activity activity )
	{
		initShareSDK();
		initSharedContent();
	}

	public static void showCustomUI( Activity packageContext , Class<?> cls )
	{
		Intent intent = new Intent();
		intent.setClass(packageContext, cls);
		packageContext.startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	public void setContent( String args )
	{
		mSharedData.strContent = args;
	}

	@Override
	public void setTitle( String args )
	{
		mSharedData.strTitle = args;
	}

	@Override
	public void setTargetUrl( String args )
	{
		mSharedData.strTargetUrl = args;
	}

	@Override
	public void setImage( Bitmap args )
	{
		mSharedData.imgPicture = new UMImage(mContext, args);
	}

	@Override
	public void setImage( byte[] args )
	{
		mSharedData.imgPicture = new UMImage(mContext, args);
	}

	@Override
	public void setImage( File args )
	{
		mSharedData.imgPicture = new UMImage(mContext, args);
	}

	@Override
	public void setImage( int args )
	{
		mSharedData.imgPicture = new UMImage(mContext, args);
	}

	@Override
	public void setImage( String args )
	{
		mSharedData.imgPicture = new UMImage(mContext, args);
	}

	public void setImage( UMImage image )
	{
		mSharedData.imgPicture = image;
	}

	@Override
	public void setVideo( String agrs )
	{
		mSharedData.mvVideo = new UMVideo(agrs);
	}

	public void setVideo( UMVideo video )
	{
		mSharedData.mvVideo = video;
	}

	public void setMusic( UMusic music )
	{
		mSharedData.mMusic = music;
	}

	@Override
	public void setMusic( String args )
	{
		mSharedData.mMusic = new UMusic(args);
	}

	@Override
	public void setPlatform( SHARE_MEDIA plateform )
	{
		mSharedData.platform = plateform;
	}

	@Override
	public void doShare( boolean isDirectShare )
	{
		mController.setShareMedia(convertTo(mSharedData.platform));
		if ( isDirectShare )
		{
			mController.directShare(mContext, mSharedData.platform, mShareListener);
		}
		else
		{
			mController.postShare(mContext, mSharedData.platform, mShareListener);
		}
	}

	public UMediaObject convertTo( SHARE_MEDIA arg0 )
	{
		BaseShareContent shared = null;

		switch ( arg0 )
		{
			case QZONE :
				shared = new QZoneShareContent();
				break;
			case WEIXIN_CIRCLE :
				shared = new CircleShareContent();
				break;
			case SINA :
				shared = new SinaShareContent();
				break;
			case WEIXIN :
				shared = new WeiXinShareContent();
				break;
			default :
				break;
		}

		if ( null != shared )
		{
			// 设置跳转页
//			mSharedData.strTargetUrl = "http://bw.readphone.com.cn/";
			// if ( SHARE_MEDIA.WEIXIN_CIRCLE == arg0 )
			// {
			// mSharedData.strTargetUrl = "http://bw.readphone.com.cn/";
			// }
			// else
			// {
			// mSharedData.strTargetUrl = "http://bw.readphone.com.cn/";
			// }

			shared.setTargetUrl(mSharedData.strTargetUrl);

			// 设置分享内容
			if ( SHARE_MEDIA.SINA == arg0 )
			{
				if ( ! TextUtils.isEmpty(mSharedData.strContent) && ! TextUtils.isEmpty(mSharedData.strTargetUrl) )
				{
					shared.setShareContent(String.format("%s %s", mSharedData.strContent, mSharedData.strTargetUrl));
				}
			}
			else if ( SHARE_MEDIA.WEIXIN_CIRCLE == arg0 )
			{// PS：微信分享到朋友圈仅显示标题，这里用标题显示分享内容。但是仅仅setTitle会什么都不显示，仍然需要setShareContent一下
				shared.setTitle(mSharedData.strContent);
				shared.setShareContent(mSharedData.strContent);
			}
			else
			{
				// shared.setTitle(mSharedData.strTitle);
				shared.setTitle(mSharedData.strTitle);
				shared.setShareContent(mSharedData.strContent);
			}

			shared.setShareImage(mSharedData.imgPicture);
			if ( null != mSharedData.mMusic )// 如果有音乐设置音乐，音乐和视频不能同时分享
			{
				shared.setShareMedia(mSharedData.mMusic);
			}
			else if ( null != mSharedData.mvVideo )
			{
				shared.setShareMedia(mSharedData.mvVideo);
			}
		}
		return shared;
	}
	
	SnsPostListener mShareListener = new SnsPostListener()
	{
		@Override
		public void onStart( )
		{

		}

		@Override
		public void onComplete( SHARE_MEDIA platform , int stCode , SocializeEntity entity )
		{
			if ( stCode == 200 )
			{
				Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
			}
			else
			{
				// Toast.makeText(mContext, "分享失败 : error code : " + stCode,
				// Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	public void setUserFeedback( String qq , String phone , String content )
	{
		content = content.substring(0, content.indexOf('|'));
		FeedbackAgent mfaAgent = new FeedbackAgent(mContext);
		Conversation conversation = mfaAgent.getDefaultConversation();
		UserInfo userInfo = mfaAgent.getUserInfo();
		if ( null == userInfo )
		{
			userInfo = new UserInfo();
		}
		Map<String, String> contect = userInfo.getContact();
		if ( null == contect )
		{
			contect = new HashMap<String, String>();
		}
		contect.put("QQ", qq);
		contect.put("Phone", phone);

		userInfo.setContact(contect);
		mfaAgent.setUserInfo(userInfo);
		mfaAgent.sync();

		conversation.addUserReply(content + "--from MHD");
		conversation.sync(null);
	}

}
