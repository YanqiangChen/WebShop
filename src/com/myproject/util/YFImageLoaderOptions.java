package com.myproject.util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class YFImageLoaderOptions
{
	public static Builder build( )
	{
		Builder optionsBuild = new DisplayImageOptions.Builder().cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在SD卡中
		.cacheOnDisc(true)
		// 设置图片以如何的编码方式显示
		.considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		// 设置图片的解码类型
		.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).resetViewBeforeLoading(true);// 设置图片在下载前是否重置，复位

		return optionsBuild;
	}

	public static Builder buildBannerImage( )
	{
		Builder optionsBuild = new DisplayImageOptions.Builder().cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在SD卡中
		.cacheOnDisc(true)
		// 设置图片以如何的编码方式显示
		.considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		// 设置图片的解码类型
		.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).resetViewBeforeLoading(true);// 设置图片在下载前是否重置，复位

		return optionsBuild;
	}
}
