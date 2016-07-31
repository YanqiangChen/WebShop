package com.myproject.frame;

import cn.readphone.util.DisplayUtil;

import com.example.weatherreport.R;
import com.myproject.view.DragLayout;
import com.myproject.view.DragLayout.DragListener;
import com.myproject.view.MyRelativeLayout;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;

public class IndexActivity extends FragmentActivity implements OnClickListener
{
	DragLayout mDragLayout;
	View mvRoot;
	View mMenu;
	View ivopenmenu;
	private RadioButton mFirst;
	private RadioButton mSecond;
	private RadioButton mThird;
	private RadioButton mFourth;
	RadioButton[] mRadioGroup;
	private Fragment[] mFragments;
	
	ImageView mUserIocn;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragments = new Fragment[4];
		mRadioGroup = new RadioButton[4];
		initDragLayout();
		initMainView();
		showFragments(0);
		
		NavigationFragment navigationFragment=new NavigationFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.fr_navigation, navigationFragment).commit();
		
		GridBanner gridBanner=new GridBanner();
		getSupportFragmentManager().beginTransaction().replace(R.id.fr_grid_banners, gridBanner).commit();
	}

	private void initMainView( )
	{
		mUserIocn=(ImageView)findViewById(R.id.iv_icon);
		mUserIocn.setOnClickListener(this);
		
		mRadioGroup[0] = mFirst = (RadioButton)findViewById(R.id.main_first);
		mRadioGroup[1] = mSecond = (RadioButton)findViewById(R.id.main_second);
		mRadioGroup[2] = mThird = (RadioButton)findViewById(R.id.main_third);
		mRadioGroup[3] = mFourth = (RadioButton)findViewById(R.id.main_fourth);

		mFirst.setOnClickListener(this);
		mSecond.setOnClickListener(this);
		mThird.setOnClickListener(this);
		mFourth.setOnClickListener(this);

		Resources res = getResources();
		Drawable dbChoiceness = DisplayUtil.setSelStateListDrawable(res.getDrawable(R.drawable.icon_choiceness_sel), res.getDrawable(R.drawable.icon_choiceness_nor));
		Drawable dbClassify = DisplayUtil.setSelStateListDrawable(res.getDrawable(R.drawable.icon_classify_sel), res.getDrawable(R.drawable.icon_classify_nor));
		Drawable dbRank = DisplayUtil.setSelStateListDrawable(res.getDrawable(R.drawable.icon_rank_sel), res.getDrawable(R.drawable.icon_rank_nor));
		Drawable dbManage = DisplayUtil.setSelStateListDrawable(res.getDrawable(R.drawable.icon_manage_sel), res.getDrawable(R.drawable.icon_manage_nor));

		dbChoiceness.setBounds(0, 0, dbChoiceness.getMinimumWidth(), dbChoiceness.getMinimumWidth());
		dbClassify.setBounds(0, 0, dbClassify.getMinimumWidth(), dbClassify.getMinimumWidth());
		dbRank.setBounds(0, 0, dbRank.getMinimumWidth(), dbRank.getMinimumWidth());
		dbManage.setBounds(0, 0, dbManage.getMinimumWidth(), dbManage.getMinimumWidth());

		mFirst.setCompoundDrawables(null, dbChoiceness, null, null);
		mSecond.setCompoundDrawables(null, dbClassify, null, null);
		mThird.setCompoundDrawables(null, dbRank, null, null);
		mFourth.setCompoundDrawables(null, dbManage, null, null);

	}

	private void initFragment( int showIndex )
	{
		switch ( showIndex )
		{
			case 0 :
				mFragments[0] = new BannerFragment();
				
				break;
			case 1 :
				mFragments[1] = new DownLoadListFragment();
				break;
			case 2 :
				mFragments[2] = new ThirdFragment();
				break;
			case 3 :
				mFragments[3] = new FourthFragment();
				break;
			default :
				break;
		}
	}

	public void showFragments( int showIndex )
	{
		int fragmentSize = mFragments.length;
		for ( int i = 0; i < fragmentSize; i++ )
		{
			if ( i == showIndex )
			{
				if ( mFragments[showIndex] == null )
				{
					initFragment(showIndex);
					if(showIndex==0)
					{
						getSupportFragmentManager().beginTransaction().add(R.id.fr_fliping_banner, mFragments[i]).commitAllowingStateLoss();
					}
					else
					{
						getSupportFragmentManager().beginTransaction().add(R.id.main_container, mFragments[i]).commitAllowingStateLoss();
					}
					
				}
				else
				{
					getSupportFragmentManager().beginTransaction().show(mFragments[i]).commitAllowingStateLoss();
				}
				mRadioGroup[i].setChecked(true);
			}
			else
			{
				if ( mFragments[i] != null )
				{
					 getSupportFragmentManager().beginTransaction().hide(mFragments[i]).commitAllowingStateLoss();
				}
				mRadioGroup[i].setChecked(false);
			}
		}
	}

	private void initDragLayout( )
	{
		mDragLayout = (DragLayout)findViewById(R.id.dl);
		// mvRoot = mDragLayout.findViewById(R.id.index_activity);
		// mMenu = mDragLayout.findViewById(R.id.index_menu);
		DragListener dragListener = new DragListener()
		{

			@Override
			public void onOpen( )
			{

			}

			@Override
			public void onClose( )
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onDrag( float percent )
			{
				// TODO Auto-generated method stub

			}

		};
		mDragLayout.setDragListener(dragListener);
	}

	@Override
	public void onClick( View v )
	{
		switch ( v.getId() )
		{
			case R.id.iv_icon:
				mDragLayout.open();
				break;
			case R.id.main_first :
				
				NavigationFragment navigationFragment=new NavigationFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.fr_navigation, navigationFragment).commit();
				
				GridBanner gridBanner=new GridBanner();
				getSupportFragmentManager().beginTransaction().replace(R.id.fr_grid_banners, gridBanner).commit();
				showFragments(0);
				break;
			case R.id.main_second :
				showFragments(1);
				break;
			case R.id.main_third :
				showFragments(2);
				break;
			case R.id.main_fourth :
				showFragments(3);
				break;
			default :
				break;
		}
	}

}
