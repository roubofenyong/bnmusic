package com.example.receiver;

import java.util.ArrayList;
import java.util.List;

import com.example.activity.MusicActivityMain;
import com.example.service.MusicService;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MusicUpdateUtil extends BroadcastReceiver
{
	//���ศ������widget����MediaPlayer��Ϊ���š���ͣ����һ�ס���һ�׵Ĳ�����
	Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		this.context=context;
		String controlTemp=intent.getStringExtra("control");
		System.out.println(controlTemp);
		ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); //��ȡϵͳ���������    
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(100);//��ȡ�������е�ϵͳ����
		if(!MusicActivityMain.MusicServiceIsStart(mServiceList, Constant.SERVICE_CLASS))//�����Ƿ���ڱ�����Ľ��̣�û���򴴽�
		{
			context.startService(new Intent(context, MusicService.class));
			// ��� ����������
			SharedPreferences sp = context.getSharedPreferences("music",Context.MODE_PRIVATE);
			int musicid = sp.getInt(Constant.SHARED_ID, -1);
			int seek = sp.getInt("current", 0);
			
			//���Ͳ�������
			Intent intent_start = new Intent(Constant.MUSIC_CONTROL);
			intent_start.putExtra("cmd", Constant.COMMAND_START);
			intent_start.putExtra("flag", false);
			intent_start.putExtra("path", DBUtil.getMusicPath(musicid));
			intent_start.putExtra("current", seek);
			context.sendBroadcast(intent_start);
			return;
		}
		if(controlTemp.equals(Constant.WIDGET_NEXT))
		{
			//��ȡ��һ�׸���ID
			int musicid=getShared(Constant.SHARED_ID);
			ArrayList<Integer> musicList = DBUtil.getMusicList(getShared(Constant.SHARED_LIST));
			musicid = DBUtil.getNextMusic(musicList, musicid);

			//��¼����ID
			setShared(Constant.SHARED_ID,musicid);

			//���Ͳ�������
			String path = DBUtil.getMusicPath(musicid);
			Intent intentTemp = new Intent(Constant.MUSIC_CONTROL);
			intentTemp.putExtra("cmd", Constant.COMMAND_PLAY);
			intentTemp.putExtra("path", path);
			context.sendBroadcast(intentTemp);
		}
		else if(controlTemp.equals(Constant.WIDGET_PREVIOUS))
		{
			//��ȡ��һ�׸���ID
			int musicid=getShared(Constant.SHARED_ID);
			ArrayList<Integer> musicList = DBUtil.getMusicList(getShared(Constant.SHARED_LIST));
			musicid = DBUtil.getPreviousMusic(musicList, musicid);

			//��¼����ID
			setShared(Constant.SHARED_ID,musicid);

			//���Ͳ�������
			String path = DBUtil.getMusicPath(musicid);
			Intent intentTemp = new Intent(Constant.MUSIC_CONTROL);
			intentTemp.putExtra("cmd", Constant.COMMAND_PLAY);
			intentTemp.putExtra("path", path);
			context.sendBroadcast(intentTemp);
		}
		else if(controlTemp.equals(Constant.WIDGET_PLAY))
		{
			int status=intent.getIntExtra("status", Constant.STATUS_STOP);
			System.out.println(status);
			if (status == Constant.STATUS_PLAY) 
			{
				Intent intentTemp = new Intent(Constant.MUSIC_CONTROL);
				intentTemp.putExtra("cmd", Constant.COMMAND_PAUSE);
				context.sendBroadcast(intentTemp);
			} 
			else if (status == Constant.STATUS_PAUSE)
			{
				Intent intentTemp = new Intent(Constant.MUSIC_CONTROL);
				intentTemp.putExtra("cmd", Constant.COMMAND_PLAY);
				context.sendBroadcast(intentTemp);
			} 
			else 
			{
				Intent intentTemp = new Intent(Constant.MUSIC_CONTROL);
				intentTemp.putExtra("cmd", Constant.COMMAND_PLAY);
				intentTemp.putExtra("path", DBUtil.getMusicPath(getShared(Constant.SHARED_ID)));
				context.sendBroadcast(intentTemp);
			}
		}
	}
	
	public void setShared(String key, int value)
	{
		SharedPreferences sp = context.getSharedPreferences("music",
				Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor spEditor=sp.edit();
		spEditor.putInt(key, value);
		spEditor.commit();
	}
	
	public int getShared(String key)
	{
		SharedPreferences sp = context.getSharedPreferences("music",
				Context.MODE_MULTI_PROCESS);
		int value = sp.getInt(key, -1);
		return value;
	}
}