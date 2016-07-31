package com.myproject.frame;

import com.example.weatherreport.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class NavigationFragment extends Fragment implements OnClickListener
{
	View mRootView;

	LinearLayout mllFirstNavigation;
	LinearLayout mllSecondNavigation;
	@Override
	public View onCreateView( LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState )
	{
		mRootView=inflater.inflate(R.layout.navigation_fragment, null);
		mllFirstNavigation=(LinearLayout)mRootView.findViewById(R.id.ll_first_navigation);
		mllFirstNavigation.setOnClickListener(this);
		mllSecondNavigation=(LinearLayout)mRootView.findViewById(R.id.ll_second_navigation);
		mllSecondNavigation.setOnClickListener(this);
		return mRootView;
	}
	@Override
	public void onClick( View v )
	{
		switch ( v.getId() )
		{
			case R.id.ll_first_navigation :
				Intent talkActivity=new Intent(getActivity(),RobotTalkActivity.class);
				startActivity(talkActivity);
				break;
			case R.id.ll_second_navigation:
				Intent hotSearchActivity=new Intent(getActivity(),HotSearchActivity.class);
				startActivity(hotSearchActivity);
			default :
				break;
		}
	}

}
