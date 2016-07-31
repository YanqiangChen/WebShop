package com.myproject.frame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.readphone.util.DisplayUtil;

import com.example.weatherreport.R;
import com.myproject.data.RobotTalkMgr;
import com.myproject.data.YFDataInterfaceCallback;
import com.myproject.data.db.YFAMDataBase;
import com.myproject.struct.TalkMessage;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RobotTalkActivity extends Activity implements OnClickListener
{

	ListView mlvContent;
	ImageView mivSend;
	EditText metFeedback;
	ArrayList<TalkMessage> malTalkMessage;
	ImageView mBackIcon;
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_robot_talk);
		initView();
	}

	private void initView( )
	{
		mBackIcon=(ImageView)findViewById(R.id.iv_icon);
		mBackIcon.setOnClickListener(this);
		mlvContent=(ListView)findViewById(R.id.talk_content_list);
		mlvContent.setFooterDividersEnabled(false);
		mlvContent.setDivider(null);
		mlvContent.setSelector(new ColorDrawable());
		mlvContent.setDividerHeight(20);
		mivSend=(ImageView)findViewById(R.id.feedback_comment_send);
		mivSend.setOnClickListener(this);
		metFeedback=(EditText)findViewById(R.id.feedback_comment);
		malTalkMessage=new ArrayList<TalkMessage>();
		malTalkMessage=YFAMDataBase.getInstance().getTalkMessage();
		mlvContent.setAdapter(mTalkAdapter);
		mlvContent.setSelection(mTalkAdapter.getCount());
		mTalkAdapter.notifyDataSetChanged();
	}
	BaseAdapter mTalkAdapter = new BaseAdapter()
	{
		
		@Override
		public View getView( int position , View convertView , ViewGroup parent )
		{
			convertView=buildListItemView(position);
			Hodler hodler = (Hodler)convertView.getTag();
			if(null!=malTalkMessage)
			{
				hodler.tvReplyTime.setText(malTalkMessage.get(position).getDate());
				hodler.tvReplyContent.setText(malTalkMessage.get(position).getContent());
			}
			return convertView;
		}
		
		@Override
		public long getItemId( int position )
		{
			return position;
		}
		
		@Override
		public Object getItem( int position )
		{
			if(null!=malTalkMessage)
			{
				return malTalkMessage.get(position);
			}
			else
			{
				return 0;
			}
			
		}
		
		@Override
		public int getCount( )
		{
			if(null!=malTalkMessage)
			{
				return malTalkMessage.size();
			}
			else
			{
				return 0;
			}
			
		}
	};
	
	protected View buildListItemView( int position )
	{

		RelativeLayout parent = new RelativeLayout(this); // 新建一个相对布局
		parent.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); // 设置相对布局的大小
		Hodler hodler = new Hodler(); // 新建hodler
		hodler.imgUserFace = new ImageView(this);
		hodler.tvReplyContent = new TextView(this);
		hodler.tvReplyTime = new TextView(this);
		RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(this, 40), DisplayUtil.dip2px(this, 40));
		RelativeLayout.LayoutParams tvContentParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams tvTimeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int ID_IMAGE_HEAD = 1;
		int ID_TEXT_CONTENT = 2;
		int ID_TEXT_TIME = 3;
		int content_padding = DisplayUtil.dip2px(this, 6);

		hodler.imgUserFace.setId(ID_IMAGE_HEAD);
		hodler.tvReplyContent.setId(ID_TEXT_CONTENT);
		hodler.tvReplyTime.setId(ID_TEXT_TIME);

		if ( malTalkMessage.get(position).getType().equals("robot") )
		{
			imgParams.addRule(RelativeLayout.BELOW, ID_TEXT_TIME);
			// imgParams.setMargins(0, 60, 0, 0);
			tvTimeParams.addRule(RelativeLayout.ALIGN_LEFT, ID_TEXT_CONTENT);
			tvTimeParams.setMargins(content_padding, 0, 0, content_padding / 2);
			tvContentParams.addRule(RelativeLayout.BELOW, ID_TEXT_TIME);
			tvContentParams.addRule(RelativeLayout.RIGHT_OF, ID_IMAGE_HEAD);
			tvContentParams.setMargins(content_padding, 30, DisplayUtil.dip2px(this, 50), 0);
			hodler.imgUserFace.setImageResource(R.drawable.icon);
			hodler.tvReplyContent.setBackgroundResource(R.drawable.left);
			// hodler.tvReplyContent.setPadding(DisplayUtil.dip2px(this, 16),
			// content_padding, content_padding, content_padding);
		}

		else
		{
			imgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			imgParams.addRule(RelativeLayout.BELOW, ID_TEXT_TIME);
			// imgParams.setMargins(0, 60, 0, 0);
			tvTimeParams.addRule(RelativeLayout.ALIGN_RIGHT, ID_TEXT_CONTENT);
			tvTimeParams.setMargins(0, 0, content_padding, content_padding / 2);
			tvContentParams.addRule(RelativeLayout.BELOW, ID_TEXT_TIME);
			tvContentParams.addRule(RelativeLayout.LEFT_OF, ID_IMAGE_HEAD);
			tvContentParams.setMargins(DisplayUtil.dip2px(this, 50), 30, content_padding, 0);

			hodler.imgUserFace.setImageResource(R.drawable.icon_head_small);
			hodler.tvReplyContent.setBackgroundResource(R.drawable.right);
		}

		imgParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

		hodler.tvReplyTime.setTextSize(11.0f);
		hodler.tvReplyTime.setTextColor(getResources().getColor(R.color.gray));
		hodler.tvReplyContent.setTextSize(14.0f);
		// hodler.tvReplyContent.setGravity(Gravity.CENTER);
		hodler.tvReplyContent.setTextColor(getResources().getColor(R.color.white));

		parent.addView(hodler.imgUserFace, imgParams);
		parent.addView(hodler.tvReplyContent, tvContentParams);
		parent.addView(hodler.tvReplyTime, tvTimeParams);

		parent.setTag(hodler);

		return parent;

	}
	
	class Hodler
	{
		ImageView imgUserFace;
		TextView tvReplyTime;
		TextView tvReplyContent;
	};
	@Override
	public void onClick( View v )
	{
		switch ( v.getId() )
		{
			case R.id.feedback_comment_send :
				
				TalkMessage talkMessage=new TalkMessage();
				talkMessage.setContent(metFeedback.getText().toString().trim());
				Date dates = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				talkMessage.setDate(df.format(dates));
				talkMessage.setType("");
				YFAMDataBase.getInstance().updataData(talkMessage);
				RobotTalkMgr.getInstance().loadTalkMessagesFromWeb(metFeedback.getText().toString().trim(),new YFDataInterfaceCallback()
				{
					
					@Override
					public void onDataResult( int code , Object objData )
					{
						initView();
					}
				});
				metFeedback.setText("");
				break;
			case R.id.iv_icon:
				finish();
				break;
			default :
				break;
		}
	}

}
