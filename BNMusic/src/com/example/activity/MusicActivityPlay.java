package com.example.activity;

import java.util.ArrayList;

import com.example.bnmusic.R;
import com.example.receiver.MusicUpdatePlay;
import com.example.receiver.MusicUpdateVisualizer;
import com.example.service.MusicService;
import com.example.util.Constant;
import com.example.util.DBUtil;
import com.example.util.MusicApplication;
import com.example.view.VisualizerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivityPlay extends Activity {
	public VisualizerView mVisualizerView;
	MusicUpdatePlay pur;
	MusicUpdateVisualizer muv;
	public boolean like = false;
	int seek_progress;
	PopupWindow popupWindow;
	ArrayList<Integer> musiclist;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_play);
    	mVisualizerView = (VisualizerView)findViewById(R.id.visualizerview);
        pur = new MusicUpdatePlay(this);
        muv = new MusicUpdateVisualizer(this);
        ImageView iv_list = (ImageView) findViewById(R.id.player_imageView_list_w);
		ImageView iv_back = (ImageView) findViewById(R.id.imageView_back);
		final ImageView iv_like = (ImageView) findViewById(R.id.player_imageView_like_w);

		ImageView iv_play = (ImageView) findViewById(R.id.player_iv_play);
		ImageView iv_next = (ImageView) findViewById(R.id.player_iv_next);
		ImageView iv_pre = (ImageView) findViewById(R.id.player_iv_pre);
		ImageView iv_shunxu = (ImageView) findViewById(R.id.player_iv_shunxu);
		final RelativeLayout rl_lyric_1 = (RelativeLayout) findViewById(R.id.player_relativelayout_change_1);
		final RelativeLayout rl_lyric_2 = (RelativeLayout) findViewById(R.id.player_relativelayout_change_2);
		ImageView iv_lyric_1 = (ImageView) findViewById(R.id.player_imageview_change_1);
		ImageView iv_lyric_2 = (ImageView) findViewById(R.id.player_imageview_change_2);

		//�л������ʾ����
		iv_lyric_1.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rl_lyric_1.setVisibility(View.INVISIBLE);
				rl_lyric_2.setVisibility(View.VISIBLE);
				muv.visualzerFlag=false;
			}
		});
		iv_lyric_2.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				rl_lyric_1.setVisibility(View.VISIBLE);
				rl_lyric_2.setVisibility(View.INVISIBLE);
				muv.visualzerFlag=true;
			}
		});
    	// ����������
		iv_back.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				MusicActivityPlay.this.finish();
				overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
			}
		});
        initPlayMode();
        iv_shunxu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageView iv_shunxu = (ImageView)findViewById(R.id.player_iv_shunxu);
				SharedPreferences sp = getSharedPreferences("music",Context.MODE_MULTI_PROCESS);
			    SharedPreferences.Editor spEditor = sp.edit();
			    int bofang = sp.getInt("playmode", Constant.PLAYMODE_SEQUENCE);
			    switch(bofang)
			    {
			    case Constant.PLAYMODE_REPEATSINGLE:
			    	spEditor.putInt("playmode", Constant.PLAYMODE_SEQUENCE);
			    	spEditor.commit();
			    	iv_shunxu.setImageResource(R.drawable.player_shunxu_w);
			    	Toast.makeText(MusicActivityPlay.this, "˳�򲥷�", Toast.LENGTH_SHORT).show();
			    	break;
			    case Constant.PLAYMODE_SEQUENCE:
					spEditor.putInt("playmode", Constant.PLAYMODE_REPEATALL);
					spEditor.commit();
					iv_shunxu.setImageResource(R.drawable.player_liebiao_w);
					Toast.makeText(MusicActivityPlay.this, "�б�ѭ��",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.PLAYMODE_REPEATALL:
					spEditor.putInt("playmode", Constant.PLAYMODE_RANDOM);
					spEditor.commit();
					iv_shunxu.setImageResource(R.drawable.player_suiji_w);
					Toast.makeText(MusicActivityPlay.this, "�������",
							Toast.LENGTH_SHORT).show();
					break;
				case Constant.PLAYMODE_RANDOM:
					spEditor.putInt("playmode", Constant.PLAYMODE_REPEATSINGLE);
					spEditor.commit();
					iv_shunxu.setImageResource(R.drawable.player_danqu_w);
					Toast.makeText(MusicActivityPlay.this, "����ѭ��",
							Toast.LENGTH_SHORT).show();
					break;
			    }
			}
		});
        iv_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int musicid = getShared(Constant.SHARED_ID);
				if(musicid == -1)
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityPlay.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "����������", Toast.LENGTH_LONG ).show();
					return;
				}
				if(MusicUpdatePlay.status == Constant.STATUS_PLAY)
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_PAUSE);
					MusicActivityPlay.this.sendBroadcast(intent);
				}
				else if(MusicUpdatePlay.status == Constant.STATUS_PAUSE)
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_PLAY);
					MusicActivityPlay.this.sendBroadcast(intent);
				}
				else
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_PLAY);
					intent.putExtra("path", DBUtil.getMusicPath(musicid));
					MusicActivityPlay.this.sendBroadcast(intent);
				}
			}
		});
        iv_next.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//��ȡ��һ�׸���ID
				int playMode = getShared("playmode");
				int musicid=getShared(Constant.SHARED_ID);
				ArrayList<Integer> musiclist = DBUtil.getMusicList(getShared(Constant.SHARED_LIST));
				if(playMode==Constant.PLAYMODE_RANDOM)
				{
					musicid = DBUtil.getRandomMusic(musiclist, musicid);
				}else
				{
					musicid = DBUtil.getNextMusic(musiclist, musicid);
				}
				
				//��¼��һ�׸���ID
				setShared(Constant.SHARED_ID,musicid);
				
				if (musicid == -1) 
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityPlay.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "����������",Toast.LENGTH_LONG).show();
					return;
				}
				
				//���¸���������Ϣ
				ArrayList<String> musicinfo = DBUtil.getMusicInfo(musicid);
				TextView tv_play_gequ = (TextView) findViewById(R.id.player_textView_gequ_w);
				TextView tv_play_geshou = (TextView) findViewById(R.id.player_textView_geshou_w);
				tv_play_gequ.setText(musicinfo.get(1));
				tv_play_geshou.setText(musicinfo.get(2));

				//���Ͳ�������
				String path = DBUtil.getMusicPath(musicid);
				Intent intent = new Intent(Constant.MUSIC_CONTROL);
				intent.putExtra("cmd", Constant.COMMAND_PLAY);
				intent.putExtra("path", path);
				MusicActivityPlay.this.sendBroadcast(intent);
			}
		});
    	// pre��ť
		iv_pre.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//��ȡ��һ�׸���ID
				int playMode = getShared("playmode");
				int musicid=getShared(Constant.SHARED_ID);
				ArrayList<Integer> musiclist = DBUtil.getMusicList(getShared(Constant.SHARED_LIST));
				if(playMode==Constant.PLAYMODE_RANDOM)
				{
					musicid = DBUtil.getRandomMusic(musiclist, musicid);
				}
				else
				{
					musicid = DBUtil.getPreviousMusic(musiclist, musicid);
				}
				
				//��¼��һ�׸���ID
				setShared(Constant.SHARED_ID,musicid);
				
				if (musicid == -1) 
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityPlay.this.sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "����������",Toast.LENGTH_LONG).show();
					return;
				}

				//���¸���������Ϣ
				ArrayList<String> musicinfo = DBUtil.getMusicInfo(musicid);
				TextView tv_play_gequ = (TextView) findViewById(R.id.player_textView_gequ_w);
				TextView tv_play_geshou = (TextView) findViewById(R.id.player_textView_geshou_w);
				tv_play_gequ.setText(musicinfo.get(1));
				tv_play_geshou.setText(musicinfo.get(2));

				//���Ͳ�������
				String path = DBUtil.getMusicPath(musicid);
				Intent intent = new Intent(Constant.MUSIC_CONTROL);
				intent.putExtra("cmd", Constant.COMMAND_PLAY);
				intent.putExtra("path", path);
				MusicActivityPlay.this.sendBroadcast(intent);
			}
		});
		//������ϲ��
				iv_like.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						int musicid=getShared(Constant.SHARED_ID);
						if(DBUtil.setILikeStatus(musicid))
						{
							if (like) 
							{
								iv_like.setImageResource(R.drawable.player_like_w);
								Toast.makeText(MusicActivityPlay.this, "�Ѵӡ���ϲ�����б��Ƴ�",
										Toast.LENGTH_SHORT).show();
								like = false;
							} 
							else 
							{
								iv_like.setImageResource(R.drawable.player_liked_w);
								Toast.makeText(MusicActivityPlay.this, "�Ѽ��롰��ϲ�����б�",
										Toast.LENGTH_SHORT).show();
								like = true;
							}
						}
						else
						{
							Toast.makeText(MusicActivityPlay.this, "��ӡ���ϲ����ʧ��",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				final SeekBar sb = (SeekBar)this.findViewById(R.id.player_seekBar_w);
				sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						pur.seek_play_touch = true;
						int musicid = getShared(Constant.SHARED_ID);
						if(musicid == -1)
						{
							sb.setProgress(0);
							Intent intent = new Intent(Constant.MUSIC_CONTROL);
							intent.putExtra("cmd", Constant.COMMAND_STOP);
							MusicActivityPlay.this.sendBroadcast(intent);
							Toast.makeText(getApplicationContext(), "����������",Toast.LENGTH_LONG).show();
							return;
						}
						Intent intent = new Intent(Constant.MUSIC_CONTROL);
						intent.putExtra("cmd", Constant.COMMAND_PROGRESS);
						intent.putExtra("current", seek_progress);
						MusicActivityPlay.this.sendBroadcast(intent);
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						pur.seek_play_touch = false;
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						// TODO Auto-generated method stub
						seek_progress = progress;
						TextView tv_timetiao = (TextView)findViewById(R.id.tv_timetiao);
						if(fromUser)
						{
							String time = pur.fromMsToMinuteStr(progress);
							tv_timetiao.setText(time);
							tv_timetiao.setVisibility(View.VISIBLE);
							
						}
						else
						{
							tv_timetiao.setVisibility(View.INVISIBLE);
						}
					}
				});
				iv_list.setOnClickListener(new OnClickListener() 
				{
					public void onClick(View v) 
					{
						showDialog();
					}
				});
				ImageView iv_shared=(ImageView)findViewById(R.id.player_imageView_share_w);
				iv_shared.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						Toast.makeText(MusicActivityPlay.this, "��������δ����", Toast.LENGTH_SHORT).show();
					}
				});
				mVisualizerView.setClickable(true);
				mVisualizerView.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						if(muv.visualzerMode)
						{
							muv.visualzerMode=false;
						}else
						{
							muv.visualzerMode=true;
						}
					}
				});

      }
	protected void showDialog() {
		// TODO Auto-generated method stub
		musiclist = DBUtil.getMusicList(this.getShared(Constant.SHARED_LIST));
	    LinearLayout linearlayout_list_w = new LinearLayout(this);
	    linearlayout_list_w.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	    linearlayout_list_w.setLayoutDirection(0);
	    ListView listview = new ListView(MusicActivityPlay.this);
	    listview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	    listview.setFadingEdgeLength(0);
	    linearlayout_list_w.setBackgroundColor(getResources().getColor(R.color.gray_shen));
	    linearlayout_list_w.addView(listview);
	    final AlertDialog dialog = new AlertDialog.Builder(MusicActivityPlay.this).create();
	    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
	    params.width = 200;
	    params.height = 400;
	    dialog.setTitle("�����б�(" + musiclist.size()+")");
	    dialog.setIcon(R.drawable.player_current_playlist_w);
	    dialog.setView(linearlayout_list_w);
	    dialog.getWindow().setAttributes(params);
	    dialog.show();
	    BaseAdapter ba = new BaseAdapter()
	    {
            LayoutInflater inflater = LayoutInflater.from(MusicActivityPlay.this);
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return musiclist.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				String musicName = DBUtil.getMusicInfo(musiclist.get(position)).get(1);
				LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.list_w, null);
				TextView tvtemp = (TextView)ll.getChildAt(0);
				tvtemp.setText((position+1)+"");
				TextView tv = (TextView)ll.getChildAt(1);
				tv.setText(musicName);
				return ll;
			}
	    	
	    };
	    listview.setAdapter(ba);
	    listview.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				dialog.dismiss();
				
				int musicid = musiclist.get(arg2);

				ArrayList<String> musicinfo = DBUtil.getMusicInfo(musicid);
				TextView tv_play_gequ = (TextView) findViewById(R.id.player_textView_gequ_w);
				TextView tv_play_geshou = (TextView) findViewById(R.id.player_textView_geshou_w);
				tv_play_gequ.setText(musicinfo.get(1));
				tv_play_geshou.setText(musicinfo.get(2));

				setShared(Constant.SHARED_ID,musicid);

				String path = DBUtil.getMusicPath(musicid);
				Intent intent = new Intent(Constant.MUSIC_CONTROL);
				intent.putExtra("cmd", Constant.COMMAND_PLAY);
				intent.putExtra("path", path);
				MusicActivityPlay.this.sendBroadcast(intent);
			}
		});
	}
	public int getShared(String key)
	{
		SharedPreferences sp = this.getSharedPreferences("music",
				Context.MODE_MULTI_PROCESS);
		int value = sp.getInt(key, -1);
		return value;
	}
	public void initPlayMode()
	{
		ImageView iv_shunxu = (ImageView) findViewById(R.id.player_iv_shunxu);
		SharedPreferences sp = getSharedPreferences("music",
				Context.MODE_MULTI_PROCESS);
		int bofang=sp.getInt("playmode", Constant.PLAYMODE_SEQUENCE);
		switch (bofang) {
		case Constant.PLAYMODE_SEQUENCE:
			iv_shunxu.setImageResource(R.drawable.player_shunxu_w);
			break;
		case Constant.PLAYMODE_REPEATALL:;
			iv_shunxu.setImageResource(R.drawable.player_liebiao_w);
			break;
		case Constant.PLAYMODE_RANDOM:
			iv_shunxu.setImageResource(R.drawable.player_suiji_w);
			break;
		case Constant.PLAYMODE_REPEATSINGLE:
			iv_shunxu.setImageResource(R.drawable.player_danqu_w);
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ArrayList<String> musicinfo = DBUtil.getMusicInfo(getShared(Constant.SHARED_ID));
		TextView tv_play_gequ = (TextView) findViewById(R.id.player_textView_gequ_w);
		TextView tv_play_geshou = (TextView) findViewById(R.id.player_textView_geshou_w);
		tv_play_gequ.setText(musicinfo.get(1));
		tv_play_geshou.setText(musicinfo.get(2));
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.UPDATE_STATUS);
		this.registerReceiver(pur, filter);
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(Constant.UPDATE_VISUALIZER);
		this.registerReceiver(muv, filter2);
	}
	public void onStop() 
	{
		super.onStop();
		// ע�����ղ��š���ͣ��ֹͣ״̬����Intent��UIUpdateReceiver
		this.unregisterReceiver(pur);
		this.unregisterReceiver(muv);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_MENU)
		{
			//�����˳��˵�
			LayoutInflater inflater = LayoutInflater.from(MusicActivityPlay.this);
			View parentView = inflater.inflate(R.layout.activity_main, null);
			
			//����˵���˵���ʧ
			RelativeLayout mpopupwindow = (RelativeLayout) inflater.inflate(R.layout.menu, null);
			mpopupwindow.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					popupWindow.dismiss();
				}
			});
			
			//�˵���Ч
			Animation popAnim=AnimationUtils.loadAnimation(MusicActivityPlay.this, R.anim.pop_menu_main);
			RelativeLayout ll_main=(RelativeLayout)mpopupwindow.getChildAt(0);
			ll_main.setAnimation(popAnim);
			
			//�˳���ť
			LinearLayout ll_exit = (LinearLayout) mpopupwindow
					.findViewById(R.id.menu_linearlayout_exit);
			ll_exit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// ��¼λ��;
					popupWindow.dismiss();
					SeekBar sb = (SeekBar) findViewById(R.id.player_seekBar_w);
					SharedPreferences sp = getSharedPreferences("music",
							Context.MODE_PRIVATE);
					SharedPreferences.Editor spEditor = sp.edit();
					spEditor.putInt("current", sb.getProgress());
					spEditor.commit();
					
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					MusicActivityPlay.this.sendBroadcast(intent);
					
					NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					notificationManager.cancel(0);
					stopService(new Intent(MusicActivityPlay.this,MusicService.class));
					MusicApplication mApp=(MusicApplication)getApplication();
					mApp.setExit(true);
					finish();
				}
			});
			
			//���������б�
			popupWindow = new PopupWindow(mpopupwindow, LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT, true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			popupWindow.setAnimationStyle(R.anim.none);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));
			popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
		}
		return super.onKeyDown(keyCode, event);
	}
	public void setShared(String key, int value)
	{
		SharedPreferences sp = this.getSharedPreferences("music",
				Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor spEditor=sp.edit();
		spEditor.putInt(key, value);
		spEditor.commit();
	}
}
