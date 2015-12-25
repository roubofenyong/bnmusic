package com.example.receiver;

import java.util.ArrayList;

import com.example.service.MusicService;
import com.example.thread.MusicPlayerThread;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Toast;

public class MusicUpdateMedia extends BroadcastReceiver {

	public MediaPlayer mp;
	public int status = Constant.STATUS_STOP;
	public int playMode;
	public int threadNumber;
	Context context;
	MusicService ms;

	public MusicUpdateMedia(MusicService ms) {
		// TODO Auto-generated constructor stub
		this.ms = ms;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
     this.context = context;
     switch(intent.getIntExtra("cmd", -1))
     {
     case Constant.COMMAND_PLAYMODE:
    	 playMode = intent.getIntExtra("playmode", Constant.PLAYMODE_SEQUENCE);
    	 break;
     case Constant.COMMAND_START:
    	 NumberRandom();
    	 String musicpath = intent.getStringExtra("path");
    	 int musiccurrent = intent.getIntExtra("current",0);
    	 if(musiccurrent == 0)
    	 {
    		 return;
    	 }
    	 if(mp != null)
    	 {
    		 mp.release();
    	 }
    	 ms.canalVisualizer();
    	 mp = new MediaPlayer();
    	 mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				NumberRandom();
				onComplete(mp);
				UpdateUI();
			}
		});
    	if(musicpath == null)
    	{
    		return;
    	}
    	 try
    	 {
    		 boolean flag = intent.getBooleanExtra("flag", true);
    		 mp.setDataSource(musicpath);
    		 mp.prepare();
    		 ms.initVisualizer(mp);
    		 mp.start();
    		 mp.seekTo(musiccurrent);
    		 status = Constant.STATUS_PLAY;
    		 if(flag)
    		 {
    			 mp.pause();
    			 ms.canalSensor();
    			 status = Constant.STATUS_PAUSE;
    		 }
    		 new MusicPlayerThread(this,context,threadNumber).start();
    	 }catch(Exception e)
    	 {
    		 e.printStackTrace();
    		 ms.canalVisualizer();
    		 NumberRandom();
    	 }
    	 break;
     case Constant.COMMAND_PLAY:
    	 String path = intent.getStringExtra("path");
    	 if(path != null)
    	 {
    		 commandPlay(path);
    	 }
    	 else
    	 {
    		 mp.start();
    		 ms.startSensor();
    		 try
    		 {
    			 ms.StartVisualizer();
    		 }catch(Exception e)
    		 {
    			 e.printStackTrace();
    			 status = Constant.STATUS_STOP;
    			 break;
    		 }
    	 }
    	 status = Constant.STATUS_PLAY;
    	 break;
     case Constant.COMMAND_STOP:
    	 NumberRandom();
    	 status = Constant.STATUS_STOP;
    	 if(mp != null)
    	 {
    		 mp.release();
    	 }
    	 ms.canalSensor();
    	 break;
     case Constant.COMMAND_PAUSE:
    	 status = Constant.STATUS_PAUSE;
    	 mp.pause();
    	 ms.canalSensor();
    	 break;
     case Constant.COMMAND_PROGRESS:
    	 int current = intent.getIntExtra("current", 0);
    	 mp.seekTo(current);
    	 break; 	 
     }
     UpdateUI();
	}

	private void commandPlay(String path) {
		// TODO Auto-generated method stub
		NumberRandom();
		if(mp != null)
		{
			mp.release();
		}
		ms.canalVisualizer();
	    mp = new MediaPlayer();
	    mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				NumberRandom();
				onComplete(mp);
				UpdateUI();
			}
		});
	    try
	    {
	    	mp.setDataSource(path);
	    	mp.prepare();
	    	mp.start();
	    	ms.initVisualizer(mp);
	    	new MusicPlayerThread(this,context,threadNumber).start();
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    	NumberRandom();
	    	ms.canalVisualizer();
	    }
	    status = Constant.STATUS_PLAY;
		
	}

	public void NumberRandom() {
		// TODO Auto-generated method stub
		int numberTemp;
		do
		{
			numberTemp = (int)(Math.random()*30);
		}while(numberTemp == threadNumber);
		threadNumber = numberTemp;
	}

	public void onComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		SharedPreferences sp = ms.getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
		int musicid = sp.getInt(Constant.SHARED_ID, -1);
		int playMode = sp.getInt("playmode",Constant.PLAYMODE_SEQUENCE);
		int list = sp.getInt(Constant.SHARED_LIST, Constant.LIST_ALLMUSIC);
		ArrayList<Integer> musicList = DBUtil.getMusicList(list);
		if(musicid == -1)
		{
			return;
		}
		if(musicList.size() == 0)
		{
			return;
		}
		String playpath;
		switch(playMode)
		{
		case Constant.PLAYMODE_REPEATSINGLE:
			playpath = DBUtil.getMusicPath(musicid);
			commandPlay(playpath);
			break;
		case Constant.PLAYMODE_REPEATALL:
			musicid = DBUtil.getNextMusic(musicList,musicid);
			playpath = DBUtil.getMusicPath(musicid);
			commandPlay(playpath);
			break;
		case Constant.PLAYMODE_SEQUENCE:
			if(musicList.get(musicList.size()-1) == musicid)
			{
				ms.canalSensor();
				ms.canalVisualizer();
				mp.release();
				status = Constant.STATUS_STOP;
				Toast.makeText(context, "已到达播放列表的最后，请重新选歌",Toast.LENGTH_LONG).show();
			}
			else
			{
			musicid = DBUtil.getNextMusic(musicList, musicid);
			playpath = DBUtil.getMusicPath(musicid);
			commandPlay(playpath);
			}
			break;
		case Constant.PLAYMODE_RANDOM:
			musicid = DBUtil.getRandomMusic(musicList,musicid);
			playpath = DBUtil.getMusicPath(musicid);
			commandPlay(playpath);
			break;
		}
		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putInt(Constant.SHARED_ID, musicid);
		spEditor.commit();
		UpdateUI();
	}

	public void UpdateUI() {
		// TODO Auto-generated method stub
		Intent intentwidget = new Intent(Constant.WIDGET_STATUS);
	    intentwidget.putExtra("status", status);
	    context.sendBroadcast(intentwidget);
	    Intent intent = new Intent(Constant.UPDATE_STATUS);
	    intent.putExtra("status", status);
	    context.sendBroadcast(intent);
	}

	public void UpdateUI(Context context) {
		// TODO Auto-generated method stub
		Intent intentwidget = new Intent(Constant.WIDGET_STATUS);
		intentwidget.putExtra("status", status);
		context.sendBroadcast(intentwidget);
		
		Intent intent = new Intent(Constant.UPDATE_STATUS);
		intent.putExtra("status", status);
		context.sendBroadcast(intent);
	}

}
