package com.myproject.frame.adapter;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.weatherreport.R;
import com.myproject.struct.GameMessages;
import com.myproject.util.YFImageLoaderOptions;
import com.myproject.view.YFRUProgressBtn;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.readphone.util.SDCardUtil;
import cn.readphone.util.StringUtil;
import cn.readphone.util.YFAppControl;
import cn.readphone.util.downloader.DownloadListener;
import cn.readphone.util.downloader.DownloadMgr;
import cn.readphone.util.downloader.DownloadStatus;
import cn.readphone.util.downloader.DownloadTask;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private Context mContext;
	private List<GameMessages> mApps;
	private ListView mlistView;
	private DisplayImageOptions mBannerOptions;
	Holder mHolder = null;
	int mnMaxProgress = 100;
	Timer mTimer;
	String mPath = SDCardUtil.getSDCardPath() + "/Readphone/";
	DownloadTask mTask;

	public GameListAdapter(Context mContext , List<GameMessages> apps ,
			ListView mlistView)
	{
		this.mContext = mContext;
		this.mApps = apps;
		this.mlistView = mlistView;
		inflater = LayoutInflater.from(mContext);
		mTimer = new Timer();
		setTimerTask();
	}

	@Override
	public int getCount( )
	{
		if ( mApps != null )
		{
			return mApps.size();
		}
		return 0;
	}

	@Override
	public Object getItem( int position )
	{
		return mApps.get(position);
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public View getView( final int position , View convertView , ViewGroup parent )
	{

		if ( convertView == null )
		{
			mHolder = new Holder();
			convertView = inflater.inflate(R.layout.download_list_item, null);
			mHolder.ivGameicon = (ImageView)convertView.findViewById(R.id.iv_game_icon);
			mHolder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
			mHolder.rblevel = (RatingBar)convertView.findViewById(R.id.classify_rb_ratingbar);
			mHolder.tvAppSize = (TextView)convertView.findViewById(R.id.tv_app_size);
			mHolder.tvDescription = (TextView)convertView.findViewById(R.id.tv_descriptions);
			mHolder.mBtnView = convertView.findViewById(R.id.toolbar);
			mHolder.mYFRUProgressBtn = new YFRUProgressBtn(mHolder.mBtnView);
			convertView.setTag(mHolder);
		}
		else
		{
			mHolder = (Holder)convertView.getTag();
			mHolder.mYFRUProgressBtn.setTextTag(position);
		}
		mHolder.mYFRUProgressBtn.setTextTag(position);

		mBannerOptions = YFImageLoaderOptions.buildBannerImage().build();
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(configuration);
		imageLoader.displayImage(mApps.get(position).getIconUrl(), mHolder.ivGameicon, mBannerOptions);

		mHolder.tvTitle.setText(mApps.get(position).getName());
		mHolder.rblevel.setRating(Float.parseFloat(mApps.get(position).getRating()) - 2);
		
		String appSize = StringUtil.formatSize(Long.parseLong(mApps.get(position).getApkSize()));
		mHolder.tvAppSize.setText(appSize+"");
		
		mHolder.tvDescription.setText(mApps.get(position).getDescription());
		// mHolder.mYFRUProgressBtn.setButtonState("下载");
		setStateAndProgress(mHolder.mYFRUProgressBtn, 100, R.string.download);

		DownloadTask mTask = DownloadMgr.getInstance().getDownloadTask(0, 0, Integer.parseInt(mApps.get(position).getId()));
		if ( mTask == null )
		{
			mTask = new DownloadTask(0, 0, Integer.parseInt(mApps.get(position).getId()), mApps.get(position).getDownloadUrl(), mPath);
		}
		setProgressButton(mTask, mHolder.mYFRUProgressBtn, position, mApps.get(position));
		mHolder.mYFRUProgressBtn.setTextViewOnClickListener(new ProgressOnClickListener(mTask,position));
		return convertView;
	}

	public int getItemIndexByContentId( int id ) // 根据Task id 获取mApps的位置id
													// 从而知道Task对应的ListView的第几项
	{
		int index = - 1;
		int size = mApps.size();
		for ( int n = 0; n < size; n++ )
		{
			if ( Integer.parseInt(mApps.get(n).getId()) == id )
			{
				index = n;
				break;
			}
		}
		return index;
	}

	public void updateView( int index , DownloadTask task )
	{
		int visiblePosition = mlistView.getFirstVisiblePosition();
		View v = mlistView.getChildAt(index - visiblePosition + mlistView.getHeaderViewsCount());
		if ( null != v )
		{
			Holder mHolder = (Holder)v.getTag();
			if ( null != mHolder )
			{
				setProgressButton(task, mHolder.mYFRUProgressBtn, index, mApps.get(index));
			}
		}
	}

	private void setTimerTask( )
	{
		mTimer.schedule(new TimerTask()
		{
			@Override
			public void run( )
			{
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);

			}
		}, 1000, 1000);
	}

	@SuppressLint( "HandlerLeak" )
	final Handler handler = new Handler()
	{
		public void handleMessage( Message msg )
		{
			super.handleMessage(msg);
			if ( msg.what == 1 )
			{
				int nSize = DownloadMgr.getInstance().getTaskCount();
				for ( int i = 0; i < nSize; i++ )
				{
					DownloadTask task = DownloadMgr.getInstance().getAllDownloadingTask().valueAt(i);
					if ( task.isDownloading() )
					{
						updateView(getItemIndexByContentId(task.getId()), task);
					}
				}
			}

		}
	};

	private void setStateAndProgress( YFRUProgressBtn mYFRUProgessButton , int percent , int statusId )
	{
		mYFRUProgessButton.setButtonState(statusId);
		mYFRUProgessButton.setProgress(percent);
	}

	private void setProgressButton( DownloadTask dlTask , YFRUProgressBtn mYFRUProgessButton , int position , GameMessages appItem )
	{
		int percent = (int)( ( ( (float)dlTask.getTempSize() ) / ( (float)dlTask.getFileSize() ) ) * mnMaxProgress );
		int mnTextTag = (Integer)mYFRUProgessButton.getTextTag();
		switch ( dlTask.getStatus() )
		{
			case DownloadStatus.DL_DOWNLOADING :
				if ( mnTextTag == position )
				{
					mYFRUProgessButton.setProgress(percent);
					mYFRUProgessButton.setButtonState(percent+ "%");
				}

				break;
			case DownloadStatus.DL_PREPARE :
			case DownloadStatus.DL_START :
				if ( mnTextTag == position )
				{
					setStateAndProgress(mYFRUProgessButton, percent, R.string.prepare_start);

				}

				break;
			case DownloadStatus.DL_PAUSE :
				if ( mnTextTag == position )
				{
					setStateAndProgress(mYFRUProgessButton, percent, R.string.proceed);
				}

				break;
			case DownloadStatus.DL_FAILED :
				if ( mnTextTag == position )
				{
					setStateAndProgress(mYFRUProgessButton, percent, R.string.retry);
				}

				break;
			case DownloadStatus.DL_FINISHED :
				if ( mnTextTag == position )
				{
					setStateAndProgress(mYFRUProgessButton, mnMaxProgress, R.string.app_install);
				}

				break;
			default :
				setStateAndProgress(mYFRUProgessButton, mnMaxProgress, R.string.download);
				break;
		}
	}

	class Holder
	{
		private ImageView ivGameicon;
		private TextView tvTitle;
		private RatingBar rblevel;
		private TextView tvAppSize;
		private TextView tvDescription;
		private View mBtnView;
		private YFRUProgressBtn mYFRUProgressBtn;
	}

	private class ProgressOnClickListener implements OnClickListener
	{

		private DownloadTask mDownloadTask;
        private int position;
		public ProgressOnClickListener(DownloadTask mDownloadTask,int position)
		{
			this.mDownloadTask = mDownloadTask;
			this.position=position;
		}

		@Override
		public void onClick( View arg0 )
		{
			switch ( mDownloadTask.getStatus() )
			{
				case DownloadStatus.DL_PREPARE :
				case DownloadStatus.DL_START :
				case DownloadStatus.DL_DOWNLOADING :
					mDownloadTask.pause();
					break;
				case DownloadStatus.DL_PAUSE :
					mDownloadTask.start();
				case DownloadStatus.DL_FAILED :
					break;
				case DownloadStatus.DL_FINISHED :
					String s=mApps.get(position).getPackageName()+".apk";
					String path=mPath;
					File file=new File(s, path);
//					YFAppControl.installApp(mContext, new File(mPath, mApps.get(position).getPackageName()+"_1.apk"));
					YFAppControl.installApp(mContext, new File(mPath, mDownloadTask.getFileName()));

//					YFAppControl.openAPP(mContext, mApps.get(position).getPackageName());
					break;
				default :
					mDownloadTask.start();
					break;
			}

			mDownloadTask.setTaskListener(new DownloadListener()
			{

				@Override
				public void onTaskStop( DownloadTask task )
				{
					updateView(getItemIndexByContentId(task.getId()), task);
				}

				@Override
				public void onTaskStart( DownloadTask task )
				{
				}

				@Override
				public void onTaskProgress( DownloadTask task )
				{

				}

				@Override
				public void onTaskComplate( DownloadTask task )
				{
					updateView(getItemIndexByContentId(task.getId()), task);
				}
			});
		}

	}
}
