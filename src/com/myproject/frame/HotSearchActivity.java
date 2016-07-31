package com.myproject.frame;

import java.util.ArrayList;



import com.example.weatherreport.R;
import com.myproject.data.SearchResultMgr;
import com.myproject.data.YFDataInterfaceCallback;
import com.myproject.frame.adapter.ReslutListAdapter;
import com.myproject.struct.hotSearchResult;
import com.myproject.view.XListView;
import com.myproject.view.XListView.IXListViewListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class HotSearchActivity extends Activity implements OnClickListener, IXListViewListener,OnItemClickListener
{

	EditText metSearch;
	ListView mlvResult;
	TextView mtvDoSearch;
	private XListView mxListView;
	ArrayList<hotSearchResult> mlvHotSearchResult;
	ReslutListAdapter mReslutListAdapter;
	ImageView ivIcon;
	View footer;
	int page=1;
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_search);
		initView();
	}

	private void initView( )
	{
		mlvHotSearchResult=new ArrayList<hotSearchResult>();
		metSearch=(EditText)findViewById(R.id.search_editText);
		
		mxListView=(XListView)findViewById(R.id.lv_searchResult);
		mxListView.setPullLoadEnable(true);
		mxListView.setPullRefreshEnable(false);
		mxListView.setXListViewListener(this);
		mxListView.setOnItemClickListener(this);
		
		mtvDoSearch=(TextView)findViewById(R.id.tv_doSearch);
		mtvDoSearch.setOnClickListener(this);
		ivIcon=(ImageView)findViewById(R.id.iv_icon);
		ivIcon.setOnClickListener(this);
		footer=getLayoutInflater().inflate(R.layout.listview_footer, null);
	}

	@Override
	public void onClick( View v )
	{
		switch ( v.getId() )
		{
			case R.id.tv_doSearch :
				SearchResultMgr.getInstance().loadSearchResultFromWeb(10, 1, 1, metSearch.getText().toString().trim(), new YFDataInterfaceCallback()
				{
					@Override
					public void onDataResult( int code , Object objData )
					{
						mlvHotSearchResult=(ArrayList<hotSearchResult>)objData;
						mReslutListAdapter=new ReslutListAdapter(HotSearchActivity.this, mlvHotSearchResult);
						mxListView.setAdapter(mReslutListAdapter);
						mReslutListAdapter.notifyDataSetChanged();
					}
				});
				
				break;
			case R.id.iv_icon:
				finish();
			default :
				break;
		}
	}

	@Override
	public void onRefresh( )
	{
		SearchResultMgr.getInstance().loadSearchResultFromWeb(10, 1, 1, metSearch.getText().toString().trim(), new YFDataInterfaceCallback()
		{
			@Override
			public void onDataResult( int code , Object objData )
			{
				mlvHotSearchResult=(ArrayList<hotSearchResult>)objData;
				mReslutListAdapter=new ReslutListAdapter(HotSearchActivity.this, mlvHotSearchResult);
				mxListView.setAdapter(mReslutListAdapter);
				mReslutListAdapter.notifyDataSetChanged();
				mxListView.stopRefresh();
			}
		});
	}

	@Override
	public void onLoadMore( )
	{
		page=page+1;
		SearchResultMgr.getInstance().loadSearchResultFromWeb(10, 1, page, metSearch.getText().toString().trim(), new YFDataInterfaceCallback()
		{
			@Override
			public void onDataResult( int code , Object objData )
			{
				mlvHotSearchResult.addAll((ArrayList<hotSearchResult>)objData);
				mReslutListAdapter=new ReslutListAdapter(HotSearchActivity.this, mlvHotSearchResult);
				mxListView.setAdapter(mReslutListAdapter);
				mReslutListAdapter.notifyDataSetChanged();
				mxListView.stopLoadMore();
				mxListView.setSelection(mReslutListAdapter.getCount()-1);
			}
		});
		
	}

	@Override
	public void onItemClick( AdapterView<?> parent , View view , int position , long id )
	{
		String url=mlvHotSearchResult.get(position-1).getUrl();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
	}

	

}
