package com.myproject.frame.view.share;

import java.io.File;

import com.umeng.socialize.bean.SHARE_MEDIA;

import android.app.Activity;
import android.graphics.Bitmap;

public interface ShareInterface
{

	/**
	 * 接口使用分享，要显式初始化接口
	 * 
	 * @param activity
	 */
	public void initShare(Activity activity);
	
	/**
	 * 设置分享文字
	 * 
	 * @param arg
	 */
	public void setContent(String args);
	
	/**
	 * 设置分享标题
	 * 
	 * @param arg
	 */
	public void setTitle(String args);
	
	/**
	 * 设置跳转URL
	 * 
	 * @param arg
	 */
	public void setTargetUrl(String args);
	
	/**
	 * 设置分享图片，重载函数
	 * 
	 * @param arg
	 */
	public void setImage(Bitmap args);
	
	/**
	 * 设置分享图片，重载函数
	 * 
	 * @param arg
	 */
	public void setImage(byte[] args);
	
	/**
	 * 设置分享图片，重载函数
	 * 
	 * @param arg
	 */
	public void setImage(File args);
	
	/**
	 * 设置分享图片，重载函数
	 * 
	 * @param arg
	 */
	public void setImage(int args);
	
	/**
	 * 设置分享图片，重载函数
	 * 
	 * @param arg
	 */
	public void setImage(String args);
	
	/**
	 * 设置分享视频
	 * 
	 * @param arg
	 *            视频链接，暂未使用
	 */
	public void setVideo(String agrs);
	
	/**
	 * 设置分享音乐
	 * 
	 * @param arg
	 *            音乐链接，暂未使用
	 */
	public void setMusic(String args);
	
	/**
	 * 设置分享的平台
	 * 
	 * @param plateform
	 */
	public void setPlatform( SHARE_MEDIA plateform );
	
	/**
	 * 开始分享
	 * 
	 * @param isDirectShare
	 *            是否直接分享
	 */
	public void doShare(boolean isDirectShare);
	
	/**
	 * 设置用户反馈信息
	 * 
	 * @param arg0
	 *            用户联系方式
	 * @param arg1
	 *            反馈内容
	 * @param arg2
	 *            其他信息
	 */
	public void setUserFeedback( String arg0 , String arg1 , String arg2 );
	
}
