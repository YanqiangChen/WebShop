package com.myproject.frame;

import com.example.weatherreport.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FourthFragment extends Fragment
{

	private View mContentView;
	@Override
	public View onCreateView( LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState )
	{
		mContentView=inflater.inflate(R.layout.fourth_fragment, container, false);
		return mContentView;
	}

}
