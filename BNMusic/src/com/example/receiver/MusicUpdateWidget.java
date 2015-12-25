package com.example.receiver;

import com.example.activity.MusicActivityMain;
import com.example.bnmusic.R;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MusicUpdateWidget extends AppWidgetProvider{
	RemoteViews rv;
	int status;
	@Override
	public void onEnabled(Context context)
	{
		status=Constant.STATUS_PAUSE;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if(rv == null)
		{
		   rv = new RemoteViews(context.getPackageName(),R.layout.widget);	
		}
		if(intent.getAction().equals(Constant.WIDGET_STATUS))
		{
			setStatus(context,intent);
		}
		else if(intent.getAction().equals(Constant.WIDGET_SEEK))
		{
			if(status != intent.getIntExtra("status", status))
			{
				setStatus(context, intent);
			}
			int duration = intent.getIntExtra("duration", 0);
			int current = intent.getIntExtra("current", 0);
			rv.setProgressBar(R.id.widget_progressbar_progress, duration, current, false);
		}
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,MusicUpdateWidget.class));
		appWidgetManager.updateAppWidget(appIds, rv);
	}
	private void setStatus(Context context, Intent intent) {
		// TODO Auto-generated method stub
		status = intent.getIntExtra("status", Constant.STATUS_STOP);
		if(status == Constant.STATUS_PLAY)
		{
			rv.setImageViewResource(R.id.widget_iv_play, R.drawable.player_pause_w);
		}
		else
		{
			rv.setImageViewResource(R.id.widget_iv_play,R.drawable.player_play_w);
		}
		SharedPreferences sp = context.getSharedPreferences("music", context.MODE_MULTI_PROCESS);
		int musicid = sp.getInt(Constant.SHARED_ID, -1);
		if(musicid != -1)
		{
			rv.setTextViewText(R.id.widget_textview_gequ, DBUtil.getMusicInfo(musicid).get(1));
			rv.setTextViewText(R.id.widget_textview_geshou, DBUtil.getMusicInfo(musicid).get(2));
		}
		Intent playIntent = new Intent(Constant.UPDATE_WIDGET);
		playIntent.putExtra("control", Constant.WIDGET_PLAY);
		playIntent.putExtra("status",status);
		PendingIntent playPending = PendingIntent.getBroadcast(context,-1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widget_iv_play,playPending);		
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		rv=new RemoteViews(context.getPackageName(),R.layout.widget);
		Intent intent = new Intent(context,MusicActivityMain.class);
		PendingIntent pendingIntent = PendingIntent.getActivity//����ť��Intent�����ʹ�䷢��
				(
					context, 
					0, 
					intent, 
					PendingIntent.FLAG_UPDATE_CURRENT
				);
		rv.setOnClickPendingIntent(R.id.widget_iv_image, pendingIntent);
		Intent playIntent = new Intent(Constant.UPDATE_WIDGET);
		playIntent.putExtra("control", Constant.WIDGET_PLAY);
		playIntent.putExtra("status", status);
		PendingIntent playPending = PendingIntent.getBroadcast//������Ű�ť�����Ͳ�������
		(
			context, 
			1, 
			playIntent, 
			PendingIntent.FLAG_UPDATE_CURRENT
		);
		rv.setOnClickPendingIntent(R.id.widget_iv_play, playPending);
		
		Intent nextIntent = new Intent(Constant.UPDATE_WIDGET);
		nextIntent.putExtra("control", Constant.WIDGET_NEXT);
		PendingIntent nextPending = PendingIntent.getBroadcast//������һ�װ�ť
		(
			context, 
			3, 
			nextIntent, 
			PendingIntent.FLAG_UPDATE_CURRENT
		);
		rv.setOnClickPendingIntent(R.id.widget_iv_next, nextPending);
		
		Intent previousIntent = new Intent(Constant.UPDATE_WIDGET);
		previousIntent.putExtra("control", Constant.WIDGET_PREVIOUS);
		PendingIntent proviousPending = PendingIntent.getBroadcast//������һ�װ�ť
		(
			context, 
			2, 
			previousIntent, 
			PendingIntent.FLAG_UPDATE_CURRENT
		);
		rv.setOnClickPendingIntent(R.id.widget_iv_pre, proviousPending);

		appWidgetManager.updateAppWidget(appWidgetIds, rv);//��������widget
	}
	
	
	
}
