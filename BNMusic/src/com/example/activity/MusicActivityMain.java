package com.example.activity;
import java.util.ArrayList;
import java.util.List;

import com.example.bnmusic.R;
import com.example.fragment.MusicFragmentMain;
import com.example.receiver.MusicUpdateMain;
import com.example.service.MusicService;
import com.example.util.Constant;
import com.example.util.DBUtil;
import com.example.util.MusicApplication;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MusicActivityMain extends FragmentActivity {
	MusicUpdateMain mu;
	SeekBar sb;
	public boolean Seekbar_touch = true;
	String key;
	int progress_seekbar;
	public static int mainFragmentId;
	private PopupWindow popupWindow;
	Notification noti;
//	NotificationManager nm;
//    public static void setNm(NotificationManager nm) {
//		nm = nm;
//	}
//
//	public static void setId(int id) {
//		id = id;
//	}
//	int id;
//	public MusicActivityMain(NotificationManager nm, int id)
//	{
//		this.nm = nm;
//		this.id = id;
//	}
//	
    @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	setContentView(R.layout.activity_main);
    	DBUtil.createTable();
    	mu = new MusicUpdateMain(this);
    	ActivityManager mActivityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
    	List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(100);
        if(!MusicServiceIsStart(mServiceList,Constant.SERVICE_CLASS))
        {
            this.startService(new Intent(this,MusicService.class));
            SharedPreferences sp = this.getSharedPreferences("music", Context.MODE_PRIVATE);
            int musicid = sp.getInt(Constant.SHARED_ID, -1);
            int seek = sp.getInt("current", 0);
            Intent intent_start = new Intent(Constant.MUSIC_CONTROL);
            intent_start.putExtra("cmd", Constant.COMMAND_START);
            intent_start.putExtra("path", DBUtil.getMusicPath(musicid));
            intent_start.putExtra("current",seek);
            this.sendBroadcast(intent_start);
        }
        LinearLayout ll_main_player = (LinearLayout)findViewById(R.id.main_linearlayout_play);
        ImageView iv_next = (ImageView)findViewById(R.id.imageview_next);
        sb = (SeekBar)findViewById(R.id.seekBar1);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Seekbar_touch = true;
				int musicid = getShared(Constant.SHARED_ID);
				if(musicid == -1)
				{
					sb.setProgress(0);
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityMain.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "歌曲不存在", Toast.LENGTH_LONG).show();
				}
				Intent intent = new Intent(Constant.MUSIC_CONTROL);
				intent.putExtra("cmd", Constant.COMMAND_PROGRESS);
				intent.putExtra("current", progress_seekbar);
				MusicActivityMain.this.sendBroadcast(intent);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				progress_seekbar = progress;
				if(fromUser)
				{
					Seekbar_touch = false;
				}
			}
		});
        ll_main_player.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MusicActivityMain.this,MusicActivityPlay.class);
			    startActivity(intent);
			    overridePendingTransition(R.anim.click_enter,R.anim.click_exit);
			}
		});
        iv_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int playMode = getShared("playmode");
				int musicid = getShared(Constant.SHARED_ID);
				ArrayList<Integer> musiclist = DBUtil.getMusicList(getShared(Constant.SHARED_LIST));
				if(playMode == Constant.PLAYMODE_RANDOM)
				{
					musicid = DBUtil.getRandomMusic(musiclist, musicid);
				}
				else
				{
					musicid = DBUtil.getNextMusic(musiclist, musicid);
				}
				setShared(Constant.SHARED_ID,musicid);
				if(musicid == -1)
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityMain.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "歌曲不存在", Toast.LENGTH_LONG).show();
				    return;
				}
				String path = DBUtil.getMusicPath(musicid);
				Intent intent = new Intent(Constant.MUSIC_CONTROL);
				intent.putExtra("cmd", Constant.COMMAND_PLAY);
				intent.putExtra("path", path);
				MusicActivityMain.this.sendBroadcast(intent);
			}
		});
        ImageView iv_play = (ImageView)findViewById(R.id.imageview_play);
        iv_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int musicid = getShared(Constant.SHARED_ID);
				if(musicid == -1)
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityMain.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "歌曲不存在", Toast.LENGTH_LONG).show();
				    return;
				}
				if(musicid != -1)
				{
					if(MusicUpdateMain.status == Constant.STATUS_PLAY)
					{
						Intent intent = new Intent(Constant.MUSIC_CONTROL);
						intent.putExtra("cmd", Constant.COMMAND_PAUSE);
					    MusicActivityMain.this.sendBroadcast(intent);
					}
					else if (MusicUpdateMain.status == Constant.STATUS_PAUSE) 
					{
						Intent intent = new Intent(Constant.MUSIC_CONTROL);
						intent.putExtra("cmd", Constant.COMMAND_PLAY);
						MusicActivityMain.this.sendBroadcast(intent);
					}
					else
					{
						String path = DBUtil.getMusicPath(musicid);
						Intent intent = new Intent(Constant.MUSIC_CONTROL);
						intent.putExtra("cmd", Constant.COMMAND_PLAY);
					    intent.putExtra("path", path);
					    MusicActivityMain.this.sendBroadcast(intent);
					}
				}
			}
		});
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.UPDATE_STATUS);
        this.registerReceiver(mu, filter);  
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        MusicFragmentMain fragment = new MusicFragmentMain();
        
        fragmentTransaction.setCustomAnimations(R.animator.click_enter, R.animator.click_exit,R.animator.back_enter,R.animator.back_exit);
        fragmentTransaction.add(R.id.main_linearlayout_l, fragment);
        fragmentTransaction.addToBackStack(null);
        mainFragmentId = fragmentTransaction.commit();
    }
	protected void setShared(String key, int value) {
		// TODO Auto-generated method stub
		SharedPreferences sp = this.getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putInt(key, value);
		spEditor.commit();
	}
	protected int getShared(String key) {
		// TODO Auto-generated method stub
		SharedPreferences sp = this.getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
		int value = sp.getInt(key, -1);
		return value;
	}
	public static boolean MusicServiceIsStart(List<RunningServiceInfo> mServiceList,
			String serviceClass) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < mServiceList.size() ; i++)
		{
			if(serviceClass.equals(mServiceList.get(i).service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(getFragmentManager().getBackStackEntryCount() > 1)
			{
				fragmentManager.popBackStack();
				return true;
			}
		}
		else if(keyCode == KeyEvent.KEYCODE_MENU)
		{
			LayoutInflater inflater = LayoutInflater.from(MusicActivityMain.this);
			View parentView = inflater.inflate(R.layout.activity_main, null);
			RelativeLayout mpopupwindow = (RelativeLayout)inflater.inflate(R.layout.menu, null);
			mpopupwindow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
				}
			});
			Animation popAnim = AnimationUtils.loadAnimation(MusicActivityMain.this,R.anim.pop_menu_main);
		    RelativeLayout ll_main = (RelativeLayout)mpopupwindow.getChildAt(0);
		    ll_main.setAnimation(popAnim);
		    LinearLayout ll_exit = (LinearLayout)mpopupwindow.findViewById(R.id.menu_linearlayout_exit);
		    ll_exit.setOnClickListener(new OnClickListener()
		    {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
					SharedPreferences sp = getSharedPreferences("music", Context.MODE_PRIVATE);
					SharedPreferences.Editor spEditor = sp.edit();
					spEditor.putInt("current", sb.getProgress());
					spEditor.commit();
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityMain.this.sendBroadcast(intent);
				    stopService(new Intent(MusicActivityMain.this,MusicService.class));
				    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
				    notificationManager.cancel(0);
				    finish();
				}
		    });
		    popupWindow = new PopupWindow(mpopupwindow,LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,true);
		    popupWindow.setOutsideTouchable(true);
		    popupWindow.setFocusable(true);
		    popupWindow.setAnimationStyle(R.anim.none);
		    popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		    popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.UPDATE_STATUS);
		this.registerReceiver(mu, filter);
		Notification();
	}
	private void Notification() {
		// TODO Auto-generated method stub
		final int HELLO_ID = 0;
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent(this,MusicActivityMain.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//		Notification myNotification = new Notification();
//		myNotification.icon = R.drawable.ic_launcher;
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Notification.Builder nb = new Notification.Builder(this);
		nb.setSmallIcon(icon);
		nb.setWhen(when);
		ArrayList<String> notification = DBUtil.getMusicInfo(getShared(Constant.SHARED_ID));
		if(notification.get(1).equals("中国好音乐"))
		{
//			myNotification.setLatestEventInfo();
			nb.setContentIntent(pi);
			nb.setTicker("没有播放的歌曲");
			nb.setContentText("客官，再来一首吧!");
			noti = nb.getNotification();	
		}
		else
		{
			nb.setContentIntent(pi);
			nb.setTicker(notification.get(0));
			nb.setContentText("正在播放中...");
			noti = nb.getNotification();
			
		}
		nm.notify(HELLO_ID, noti);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.unregisterReceiver(mu);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ImageView iv_play = (ImageView)findViewById(R.id.imageview_play);
		if(MusicUpdateMain.status == Constant.STATUS_PLAY)
		{
			iv_play.setImageResource(R.drawable.player_pause_w);
		}
		else
		{
			iv_play.setImageResource(R.drawable.player_play_w);
		}
		MusicApplication mApp=(MusicApplication)getApplication();
		
		if(mApp.isExit())
		{
			// 记录位置;
			SharedPreferences sp = this.getSharedPreferences("music",
					Context.MODE_MULTI_PROCESS);
			SharedPreferences.Editor spEditor = sp.edit();
			spEditor.putInt("current", sb.getProgress());
			spEditor.commit();
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.cancel(0);
			finish();
			System.exit(0);
		}
		
	}
}
