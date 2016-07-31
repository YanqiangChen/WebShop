package com.myproject.util;

import cn.readphone.util.NetworkUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class ImageLoaderEx extends ImageLoader
{

	private volatile static ImageLoaderEx instance;

	public static ImageLoaderEx getInstance( )
	{
		if ( instance == null )
		{
			synchronized ( ImageLoaderEx.class )
			{
				if ( instance == null )
				{
					instance = new ImageLoaderEx();
				}
			}
		}
		return instance;
	}

	protected ImageLoaderEx()
	{
	}

	@Override
	public void displayImage( String uri , ImageAware imageAware , DisplayImageOptions options , ImageLoadingListener listener , ImageLoadingProgressListener progressListener )
	{
		super.displayImage(uri, imageAware, options, listener, progressListener);
	}

	
}
