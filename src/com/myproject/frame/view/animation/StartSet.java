package com.myproject.frame.view.animation;

import java.util.ArrayList;


import com.example.weatherreport.R;


import android.content.Context;

public class StartSet
{
	Context mContext;
	ArrayList<Star> Stars;
	
	public StartSet(Context mContext)
	{
		this.mContext = mContext;
		Stars = new ArrayList<Star>();
	}
	
	public void addStar(int count,int height,int width,int startX,int startY)
	{
		for(int i=0;i<count;i++)
		{
			int imageResourse=getImageResource(i);
			double endX=( ( Math.random() * 2 ) > 1 ? 1 : - 1 )*(Math.random())*width+startX;
			double endY=( ( Math.random() * 2 ) > 1 ? 1 : - 1 )*(Math.random())*height+startY;
			int startTime= (int)( 6000 * ( Math.random() ) );
			Star star = new Star(this.mContext, imageResourse, startX, startY, endX, endY, startTime);
			Stars.add(star);
		}
		
	}
	
	public ArrayList<Star> getStars()
	{
		return Stars;
	}
	
	public void clear()
	{
		Stars.clear();
	}
	
	public int getImageResource(int i)
	{
		int color= R.drawable.icon_star_blue;
		switch ( i%4 )
		{
			
			case 0 :
				color=R.drawable.icon_star_blue;
				break;
			case 1:
				color=R.drawable.icon_star_purple;
				break;
			case 2:
				color=R.drawable.icon_star_pink;
				break;
			case 3:
				color=R.drawable.icon_star_orange;
				break;
			default :
				break;
		}
		return color;
	}
}
