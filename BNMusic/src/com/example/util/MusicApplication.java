package com.example.util;

import android.app.Application;

public class MusicApplication extends Application{

	private static boolean isExit=false;
	public static String socketIp="10.150.45.76";//´ý¶¨
	
	public void setExit(boolean b)
	{
		isExit=b;
	}
	
	public boolean isExit()
	{
		return isExit;
	}
}
