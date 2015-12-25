package com.example.receiver;

import java.util.ArrayList;

import com.example.activity.MusicActivityPlay;
import com.example.bnmusic.R;
import com.example.util.Constant;
import com.example.util.DBUtil;
import com.example.view.LyricView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicUpdatePlay extends BroadcastReceiver {

	public static int status = Constant.STATUS_STOP;
	public boolean seek_play_touch = true;
	MusicActivityPlay pa;
	ArrayList<String> musicinfo;
	ArrayList<String[]> lyric;
	int oldmusicid;
    int duration = 0;
    int current = 0;
    

	public MusicUpdatePlay(MusicActivityPlay pa) {
		// TODO Auto-generated constructor stub
	   this.pa = pa;
	   oldmusicid = -1;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
     int tempStatus = intent.getIntExtra("status", -1);
     ImageView ib_play = (ImageView)pa.findViewById(R.id.player_iv_play);
     TextView lyricabove = (TextView)pa.findViewById(R.id.player_textview_above);
     TextView lyricbelow = (TextView)pa.findViewById(R.id.player_textview_below);
     LyricView lyricView = (LyricView)pa.findViewById(R.id.lyricview);
     SharedPreferences sp = pa.getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
     int musicid = sp.getInt(Constant.SHARED_ID, -1);
     try
     {
       TextView tv_gequ = (TextView)pa.findViewById(R.id.player_textView_gequ_w);
       TextView tv_geshou = (TextView)pa.findViewById(R.id.player_textView_geshou_w);
       tv_gequ.setText(DBUtil.getMusicInfo(musicid).get(1));
       tv_geshou.setText(DBUtil.getMusicInfo(musicid).get(2));
       ImageView iv_like = (ImageView)pa.findViewById(R.id.player_imageView_like_w);
       if(DBUtil.getILikeStatus(musicid))
       {
    	   iv_like.setImageResource(R.drawable.player_liked_w);
    	   pa.like = true;
       }
       else
       {
    	   iv_like.setImageResource(R.drawable.player_like_w);
    	   pa.like = false;
       }
       
     }catch(Exception e)
     {
    	 e.printStackTrace();
     }
     ArrayList<String> musicInfo = DBUtil.getMusicInfo(musicid);
     String music = musicInfo.get(1);
     String singer = musicInfo.get(2);
     switch(tempStatus)
     {
     case Constant.STATUS_PLAY:
    	 MusicUpdateMain.status = tempStatus;
    	 status = tempStatus;
    	 lyricabove.setText(music);
    	 lyricbelow.setText(singer);
    	 break;
     case Constant.STATUS_STOP:
    	 MusicUpdateMain.status = tempStatus;
    	 status = tempStatus;
    	 try
    	 {
    		 TextView tv_gequ = (TextView)pa.findViewById(R.id.player_textView_gequ_w);
    		 TextView tv_geshou = (TextView)pa.findViewById(R.id.player_textView_geshou_w);
    		 tv_gequ.setText("百纳音乐");
    		 tv_geshou.setText("传播好声音");
    		 
    	 }catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
     case Constant.STATUS_PAUSE:
    	 MusicUpdateMain.status = tempStatus;
    	 status = tempStatus;
    	 break;
     case Constant.COMMAND_GO:
    	 if(seek_play_touch)
    	 {
    		 SeekBar sb = (SeekBar)pa.findViewById(R.id.player_seekBar_w);
    		 duration = intent.getIntExtra("duration", 0);
    		 current = intent.getIntExtra("current", 0);
    		 sb.setMax(duration);
    		 sb.setProgress(current);
    		 TextView tv_current = (TextView)pa.findViewById(R.id.play_textView_time_current);
    		 TextView tv_duration = (TextView)pa.findViewById(R.id.play_textView_time_duration);
    		 tv_current.setText(fromMsToMinuteStr(current));
    		 tv_duration.setText(fromMsToMinuteStr(duration));
    		 if(musicid != oldmusicid)
    		 {
    			 oldmusicid = musicid;
    			 lyric = null;
    			 lyric = DBUtil.getLyric(DBUtil.getLyricPath(musicid));
                 lyricabove.setText(music);
                 lyricbelow.setText(singer);
                 lyricView.setLyric(musicid);
    		 }
    		 lyricView.setMusictime(current,duration);
    		 if(lyric == null)
    		 {
    			 lyricabove.setText("当前歌曲没有歌词");
    			 lyricbelow.setText("");
    		 }
    		 else
    		 {
    			 if(lyric.size() == 1)
    			 {
    				 lyricabove.setText(music);
                     lyricbelow.setText(singer);
    			 }
    			 for(int i = 0 ; i < lyric.size() ; i++)
    			 {
    				 String lyric1[] = lyric.get(i);
    				 int time1 = (Integer.parseInt(lyric1[0]) *60 + Integer.parseInt(lyric1[1]))*1000;
    				 String lyric2[] = {"","",""};
    				 int time2;
    				 if(i != lyric.size()-1)
    				 {
    					 lyric2 = lyric.get(i+1);
    					 time2 = (Integer.parseInt(lyric2[0])*60+Integer.parseInt(lyric2[1]))*1000;
    				 }
    				 else
    				 {
    					 time2 = duration;
    				 }
    				 int time = current;
    				 if(time > time1 && time < time2)
    				 {
    					 if(i % 2 == 0)
    					 {
    						 lyricabove.setText(lyric1[2]);
    						 lyricabove.setTextColor(Color.GREEN);
    						 lyricbelow.setText(lyric2[2]);
    						 lyricbelow.setTextColor(Color.WHITE);
    					 }
    					 else 
							{
								lyricbelow.setText(lyric1[2]);
								lyricbelow.setTextColor(Color.GREEN);
								lyricabove.setText(lyric2[2]);
								lyricabove.setTextColor(Color.WHITE);
							}
							break;
    				 }
    			 }
    		 }
    	 }
    	 break;
     }
     
     if(status == Constant.STATUS_PLAY)
     {
    	 ib_play.setImageResource(R.drawable.player_pause_w);
    	 pa.findViewById(R.id.player_textview_default).setVisibility(View.INVISIBLE);
    	 pa.findViewById(R.id.player_textview_above).setVisibility(View.VISIBLE);
    	 pa.findViewById(R.id.player_textview_below).setVisibility(View.VISIBLE);
    	 
     }
 	else 
	{
		ib_play.setImageResource(R.drawable.player_play_w);
		pa.findViewById(R.id.player_textview_default).setVisibility(View.VISIBLE);
		pa.findViewById(R.id.player_textview_above).setVisibility(View.INVISIBLE);
		pa.findViewById(R.id.player_textview_below).setVisibility(View.INVISIBLE);
	}
     
	}

	public String fromMsToMinuteStr(int ms) {
		ms = ms / 1000;
		int minute = ms / 60;
		int second = ms % 60;
		return minute + ":" + ((second > 9) ? second : "0" + second);
	}

}
