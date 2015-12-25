package com.example.service;

import com.example.receiver.MusicUpdateMedia;
import com.example.util.Constant;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.IBinder;

public class MusicService extends Service {
    MusicUpdateMedia mc; 
    public Visualizer mVisualizer;
    public Equalizer mEqualizer;
    private static final int SPEED_SHRESHOLD = 10;
    private static final int SPACE_TIME = 200;
    private static final int SPACE_MUSIC = 2000;
    private SensorManager mySensorManager;
    private Sensor mySensor;
    private long currentTime,lastTime,lastTime2,duration,duration2;
    private final static int RECT_COUNT = 32;
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	mc = new MusicUpdateMedia(this);
    	mc.mp = new MediaPlayer();
    	mc.status = Constant.STATUS_STOP;
    	initSensor();
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(Constant.MUSIC_CONTROL);
    	this.registerReceiver(mc, filter);
    }
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// TODO Auto-generated method stub
    	mc.UpdateUI(this.getApplicationContext());
    	return super.onStartCommand(intent, flags, startId);
    }
    public void startSensor()
    {
    	mySensorManager.registerListener(mySel, mySensor,SensorManager.SENSOR_DELAY_UI);
    }
	private void initSensor() {
		// TODO Auto-generated method stub
		mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
    private SensorEventListener mySel = new SensorEventListener()
    {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			currentTime = System.currentTimeMillis();
			duration = currentTime - lastTime;
			duration2 = currentTime - lastTime2;
			if(duration2 < SPACE_TIME)
			{
				return;
			}
			if(duration < SPACE_MUSIC)
			{
				return;
			}
			lastTime2 = currentTime;
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			double speed = Math.abs(Math.sqrt(x*x+y*y+z*z)-10);
			if(speed > SPEED_SHRESHOLD)
			{
				lastTime = currentTime;
				mc.NumberRandom();
				mc.onComplete(mc.mp);
				mc.UpdateUI();
			}
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
    	
    	
    };
    public void canalSensor()
    {
    	mySensorManager.unregisterListener(mySel);
    	if(mEqualizer != null)
    	{
    		mEqualizer.setEnabled(false);
    	}
    	if(mVisualizer != null)
    	{
    		mVisualizer.setEnabled(false);
    	}
    	
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	mc.NumberRandom();
    	canalSensor();
    	if(mEqualizer != null)
    	{
    		mEqualizer.release();
    	}
    	if(mVisualizer != null)
    	{
    		mVisualizer.release();
    	}
    	if(mc.mp != null)
    	{
    		mc.mp.release();
    	}
    	this.unregisterReceiver(mc);
    }
	public void canalVisualizer() {
		// TODO Auto-generated method stub
		if(mEqualizer != null)
		{
			mEqualizer.release();
		}
		if(mVisualizer != null)
		{
			mVisualizer.release();
		}
	}
	public void StartVisualizer()
	{
		mVisualizer.setEnabled(true);
		mEqualizer.setEnabled(true);
	}
     @Override
    public void onStart(Intent intent, int startId) {
    	// TODO Auto-generated method stub
    	mc.UpdateUI(this.getApplicationContext());
    }
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public void  initVisualizer(MediaPlayer mp)
	{
		if(mp != null)
		{
			mEqualizer = new Equalizer(0,mp.getAudioSessionId());
			mVisualizer = new Visualizer(mp.getAudioSessionId());
			mVisualizer.setCaptureSize(512);
			mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
				
				@Override
				public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
						int samplingRate) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Constant.UPDATE_VISUALIZER);
					intent.putExtra("visualizerwave", bytes);
					MusicService.this.sendBroadcast(intent);
				}
				
				@Override
				public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
						int samplingRate) {
					// TODO Auto-generated method stub
					byte[] byt = new byte[RECT_COUNT];
					for(int i = 0 ; i < RECT_COUNT ; i++)
					{
						byt[i] = (byte)Math.hypot(bytes[2*(i+1)], bytes[2*(i+1)+1]);
					}
					Intent intent = new Intent(Constant.UPDATE_VISUALIZER);
					intent.putExtra("visualizerfft", byt);
					MusicService.this.sendBroadcast(intent);
				}
			}, Visualizer.getMaxCaptureRate() / 2, true, true);
			StartVisualizer();
			startSensor();
		}
	}

}
