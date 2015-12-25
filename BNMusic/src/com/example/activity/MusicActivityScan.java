package com.example.activity;

import java.io.File;
import java.util.ArrayList;

import com.example.bnmusic.R;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivityScan extends Activity {
  boolean thread_flag;
  int degrees = 0;
  Handler handler;
  SQLiteDatabase musicData;
  ArrayList<String> Music_bianhao = new ArrayList<String>();
  ArrayList<String> Music_wenjian = new ArrayList<String>();
  ArrayList<String> Music_gequ = new ArrayList<String>();
  ArrayList<String> Music_geshou = new ArrayList<String>();
  ArrayList<String> Music_lujing = new ArrayList<String>();
  ArrayList<String> Music_geci = new ArrayList<String>();
  ArrayList<String> Music_gecilujing = new ArrayList<String>();
  ArrayList<String> geci = new ArrayList<String>();
  ArrayList<String> gecilujing = new ArrayList<String>();
  String scanPath = "";
  int progress = 0;
  int music_number = 0;
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_before);
		thread_flag = false;
		ImageButton ib_exit = (ImageButton) findViewById(R.id.title_imagebutton_exit_l);
		ib_exit.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				MusicActivityScan.this.finish();
			}
		});
		LinearLayout ll_scan = (LinearLayout)findViewById(R.id.scan_linearlayout_all);
		ll_scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.activity_scan_scan);
				thread_flag = true;
				handler = new Handler()
				{
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						switch(msg.what)
						{
						case Constant.DATABASE_ERROR:
							Toast.makeText(MusicActivityScan.this, "Êý¾Ý¿â´íÎó", Toast.LENGTH_LONG);
						case Constant.DATABASE_COMPLETE:
							setContentView(R.layout.activity_scan_after);
							TextView tv_sum2 = (TextView)findViewById(R.id.scan_textview_musicsum2);
							tv_sum2.setText(music_number + "");
							TextView tv_canal = (TextView)findViewById(R.id.scan_textview_complete);
							tv_canal.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									MusicActivityScan.this.finish();
									thread_flag = false;
								}
								
							});
							break;
						case Constant.PROGRESS_UPDATE:
							ProgressBar pb = (ProgressBar)findViewById(R.id.scan_progressbar_scanning);
							pb.setProgress(progress);
							TextView tv_path = (TextView)findViewById(R.id.scan_textview_musicpath);
							tv_path.setText(scanPath);
							TextView tv_sum = (TextView)findViewById(R.id.scan_textview_musicsum);
							tv_sum.setText(music_number+"");
							break;
						}
					}
				};
				new Thread()
				{
					public void run() {
						try
						{
							File sdcardFile = Environment.getExternalStorageDirectory();
							scanMp3List(sdcardFile,10000);
							String gequtemp;
							String gecitemp;
							DBUtil.deleteTable();
							for(int i = 0 ; i < Music_wenjian.size() ; i++)
							{
								gequtemp = Music_wenjian.get(i);
								Music_geci.add("");
								Music_gecilujing.add("");
								for(int j = 0 ; j < geci.size() ; j++)
								{
									gecitemp = geci.get(j).substring(0,geci.get(j).length()-4);
									if(gequtemp.equals(gecitemp))
									{
										Music_geci.set(i, geci.get(j));
										Music_gecilujing.set(i,gecilujing.get(j));
										break;
									}
								}
								String temp[] = {Music_wenjian.get(i),Music_gequ.get(i),Music_geshou.get(i),Music_lujing.get(i),Music_geci.get(i),Music_gecilujing.get(i)};
								DBUtil.setMusic(temp);
							}
							SharedPreferences sp = getSharedPreferences("music",Context.MODE_MULTI_PROCESS);
							SharedPreferences.Editor spEditor = sp.edit();
							spEditor.putInt(Constant.SHARED_ID, 1);
							spEditor.commit();
							handler.sendEmptyMessage(Constant.DATABASE_COMPLETE);
						}catch(Exception e) 
						{
			                e.printStackTrace();
			                handler.sendEmptyMessage(Constant.DATABASE_ERROR);
						}
						finally
						{
							if(musicData != null)
							{
								musicData.close();
							}
						}
					
					}

					private void scanMp3List(File sdcardFile, int max) {
						// TODO Auto-generated method stub
						if(sdcardFile.listFiles() != null)
						{
						   File[] files = sdcardFile.listFiles();
						   for(File filetemp : files)
						   {
							   if(!thread_flag)
							   {
								   return;
							   }
							   int min = max/files.length;
							   if(filetemp.isDirectory())
							   {
								   scanMp3List(filetemp, min);
							   }
							   else
							   {
								   String filepath = filetemp.getAbsolutePath().toString();
								   if(filepath.endsWith(".mp3"))
								   {
								   String filename = filetemp.getName();
								   File fileTemp = new File(filepath);
								   if(fileTemp.length() < 10)
								   {
									   continue;
								   }
								   String[] fileinfo =  filename.split("-");
								   if(fileinfo.length != 1)
								   {
									   music_number++;
									   Music_bianhao.add(music_number+"");
									   Music_wenjian.add(filename.substring(0,filename.length()-4));
									   Music_gequ.add(fileinfo[1].substring(0,fileinfo[1].length()-4).trim());
									   Music_geshou.add(fileinfo[0].trim());
									   Music_lujing.add(filepath);
								   }
								   }
								   if(filepath.endsWith(".lrc"))
								   {
									   String filename = filetemp.getName();
									   geci.add(filename);
									   gecilujing.add(filepath);
								   }
								   scanPath = filepath;
								   progress += min;
								   handler.sendEmptyMessage(Constant.PROGRESS_UPDATE);
							   }
						   }
						}
					}

					
				}.start();
				TextView tv_canal = (TextView)findViewById(R.id.scan_textview_canal);
				tv_canal.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SharedPreferences sp = getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
						SharedPreferences.Editor spEditor = sp.edit();
						spEditor.putInt(Constant.SHARED_ID, -1);
						spEditor.commit();
						MusicActivityScan.this.finish();
						thread_flag = false;
					}
				});
			}
		});
		
	}
 
}
