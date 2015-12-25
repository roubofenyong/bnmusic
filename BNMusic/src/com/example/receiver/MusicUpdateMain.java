package com.example.receiver;

import com.example.activity.MusicActivityMain;
import com.example.bnmusic.R;
import com.example.fragment.MusicFragmentFour;
import com.example.fragment.MusicFragmentLocalmusic;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicUpdateMain extends BroadcastReceiver {

	public static int status;
    MusicActivityMain ma;
    int duration,current;
    public int updateTime = 0;
	public MusicUpdateMain(MusicActivityMain ma) {
		// TODO Auto-generated constructor stub
		this.ma = ma;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
       int statustemp = intent.getIntExtra("status", -1);
       ImageView iv_play = (ImageView)ma.findViewById(R.id.imageview_play);
       try
       {
    	   SharedPreferences sp = ma.getSharedPreferences("music",Context.MODE_MULTI_PROCESS);
    	   int musicid = sp.getInt(Constant.SHARED_ID, -1);
    	   TextView tv_gequ = (TextView)ma.findViewById(R.id.main_textview_gequ);
    	   TextView tv_geshou = (TextView)ma.findViewById(R.id.main_textview_geshou);
    	   tv_gequ.setText(DBUtil.getMusicInfo(musicid).get(1));
    	   tv_geshou.setText(DBUtil.getMusicInfo(musicid).get(2));
       }catch(Exception e)
       {
    	   e.printStackTrace();
       }
       switch(statustemp)
       {
       case Constant.STATUS_PLAY:
    	   status = statustemp;
    	   MusicUpdatePlay.status = statustemp;
    	   iv_play.setImageResource(R.drawable.player_pause_w);
    	   break;
       case Constant.STATUS_STOP:
    	   try
    	   {
    	   TextView tv_gequ = (TextView) ma.findViewById(R.id.main_textview_gequ);
			TextView tv_geshou = (TextView) ma.findViewById(R.id.main_textview_geshou);
			tv_gequ.setText("��������");
			tv_geshou.setText("����������");
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    	   }
       case Constant.STATUS_PAUSE:
    	   status = statustemp;
    	   MusicUpdatePlay.status = statustemp;
    	   iv_play.setImageResource(R.drawable.player_play_w);
    	   break;
       case Constant.COMMAND_GO:
    	   if(status != intent.getIntExtra("status2", Constant.STATUS_STOP))
    	   {
    		   status = intent.getIntExtra("status2", Constant.STATUS_STOP);
    		   MusicUpdatePlay.status = status;
    		   if(status == Constant.STATUS_PLAY)
    		   {
    			 iv_play.setImageResource(R.drawable.player_pause_w);  
    		   }
    	   }
    	  if(ma.Seekbar_touch)
    	  {
    		  SeekBar sb = (SeekBar)ma.findViewById(R.id.seekBar1);
    		  duration = intent.getIntExtra("duration", 0);
    		  current = intent.getIntExtra("current", 0);
    		  sb.setMax(duration);
    		  sb.setProgress(current);
    		  updateTime++;
              if(updateTime > 10)
              {
            	  updateTime = 0;
            	  try
            	  {
            		  MusicFragmentLocalmusic.ba.notifyDataSetChanged();
            	  }catch(Exception e)
            	  {
            	  }
            	  try
            	  {
            		  MusicFragmentFour.ba.notifyDataSetChanged();
            	  }catch(Exception e)
            	  {
            	  }
            	  
              }
    	  }
    	   
       }
       
	}
	public String fromMsToMinuteStr(int ms) 
	{
		ms = ms / 1000;
		int minute = ms / 60;
		int second = ms % 60;
		return minute + ":" + ((second > 9) ? second : "0" + second);
	}

}
