package com.myproject.util;



import android.view.View;

public class Controller<T extends View, D>
{
	T mView;
	D mData;
	public interface OnDataChangeListener<T extends View, D>
	{
		public void onDataChanged( T view , D data );
	};
	
	private OnDataChangeListener<T, D> mOnDataChangeListener;
	
	public Controller<T, D> setOnDataChangelistener( OnDataChangeListener<T, D> onDataChangeListener )
	{
		this.mOnDataChangeListener = onDataChangeListener;
		return this;

	}
	
	public OnDataChangeListener<T, D> getOnDataChangeListener( )
	{
		return mOnDataChangeListener;
	}
	
	public Controller(T view)
	{
		this.mView = view;
	}
	
	public void setData( D data )
	{
		this.mData = data;
		if ( mOnDataChangeListener != null && mView != null && mData != null )
		{
			mOnDataChangeListener.onDataChanged(mView, mData);
		}
	}
	
}
