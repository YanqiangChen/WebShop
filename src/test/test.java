package test;

import java.util.ArrayList;

import com.example.weatherreport.R;
import com.myproject.data.GameMgr;
import com.myproject.data.RobotTalkMgr;
import com.myproject.data.SearchResultMgr;
import com.myproject.data.YFDataInterfaceCallback;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.myproject.data.BannerMgr;
import com.myproject.struct.BannerImages;
import com.myproject.struct.GameMessages;
import com.myproject.struct.hotSearchResult;
public class test extends Activity
{

	TextView mTestView;
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		initView();
		initData();
	}

	private void initData( )
	{
//		BannerMgr bannerMgr=new BannerMgr();
//		bannerMgr.loadAdvertisementListFromWeb(10,new YFDataInterfaceCallback()
//		{
//			
//			@Override
//			public void onDataResult( int code , Object objData )
//			{
//				ArrayList<BannerImages> bannerImages =new ArrayList<BannerImages>();
//				bannerImages=(ArrayList<BannerImages>)objData;
//				mTestView.setText(bannerImages.get(1).getDescription());
//			}
//		});
		
//		bannerMgr.loadAdvertisementListFromDB(new YFDataInterfaceCallback()
//		{
//			
//			@Override
//			public void onDataResult( int code , Object objData )
//			{
//				ArrayList<BannerImages> bannerImages =new ArrayList<BannerImages>();
//				bannerImages=(ArrayList<BannerImages>)objData;
//				mTestView.setText(bannerImages.get(1).getDescription());
//			}
//		});
		
//		RobotTalkMgr.getInstance().loadTalkMessagesFromWeb("你好", new YFDataInterfaceCallback()
//		{
//			
//			@Override
//			public void onDataResult( int code , Object objData )
//			{
//				String textMessage=(String)objData;
//				mTestView.setText(textMessage);
//			}
//		});
		
//		SearchResultMgr.getInstance().loadSearchResultFromWeb(10, 1, 1, "美女", new YFDataInterfaceCallback()
//		{
//			
//			@Override
//			public void onDataResult( int code , Object objData )
//			{ 
//				ArrayList<hotSearchResult> alHotSearchResult=new ArrayList<hotSearchResult>();
//				alHotSearchResult=(ArrayList<hotSearchResult>)objData;
//				mTestView.setText(alHotSearchResult.size()+alHotSearchResult.get(1).getDescription());
//			}
//		});
		
		GameMgr.getInstance().loadGameRandFromWeb(new YFDataInterfaceCallback()
		{
			
			@Override
			public void onDataResult( int code , Object objData )
			{
				ArrayList<GameMessages> alGameMessages=new ArrayList<GameMessages>();
				alGameMessages=(ArrayList<GameMessages>)objData;
				mTestView.setText(alGameMessages.get(1).getDescription());
			}
		});
	}

	private void initView( )
	{
		mTestView=(TextView)findViewById(R.id.test_textView);
		
	}

}
