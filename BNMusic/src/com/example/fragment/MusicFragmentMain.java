package com.example.fragment;

import com.example.bnmusic.R;
import com.example.util.Constant;
import com.example.util.DBUtil;
import com.example.util.MusicApplication;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.style.TtsSpan.DateBuilder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MusicFragmentMain extends Fragment {
    EditText et_search;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
         View v = inflater.inflate(R.layout.fragment_main, container,false);
         et_search = (EditText)v.findViewById(R.id.main_edittext_search);
         et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				et_search.setText("");
			}
		});
         ImageView iv_ip = (ImageView)v.findViewById(R.id.main_imageview_skin);
         iv_ip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText etTemp = new EditText(getActivity());
				etTemp.setSingleLine();
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setTitle("����ip��ַ");
				builder.setView(etTemp);
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
                             dialog.dismiss();
                             MusicApplication.socketIp = etTemp.getText().toString();
					}
					
				});
				builder.setNegativeButton("ȡ��",new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						dialog.dismiss();
					}
				});
			builder.create().show();	
			}
		});
         ImageView iv_search = (ImageView)v.findViewById(R.id.main_imageview_search);
         iv_search.setOnClickListener(new OnClickListener()
         {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				MusicFragmentSearch fragment = new MusicFragmentSearch(et_search.getText().toString().trim());
			    changeFragment(fragmentTransaction,fragment);
			}
         });
         LinearLayout main_ll_localmusic= (LinearLayout)v.findViewById(R.id.main_linearlayout_localmusic);  
    	  ImageButton ib_localplay = (ImageButton)v.findViewById(R.id.main_imageview_play);
          ib_localplay.setOnClickListener(
          
        	  new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SharedPreferences sp = getActivity().getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
					int musicid = sp.getInt(Constant.SHARED_ID, -1);
					if(musicid == -1)
					{
						Intent intent = new Intent(Constant.MUSIC_CONTROL);
						intent.putExtra("cmd", Constant.COMMAND_STOP);
						getActivity().sendBroadcast(intent);
						Toast.makeText(getActivity(), "����������", Toast.LENGTH_LONG).show();
						return;
					}
					SharedPreferences.Editor spEditor = sp.edit();
					musicid = DBUtil.getRandomMusic(DBUtil.getMusicList(Constant.LIST_ALLMUSIC), musicid);
					spEditor.putInt(Constant.SHARED_ID, musicid);
					spEditor.putInt(Constant.SHARED_LIST, Constant.LIST_ALLMUSIC);
					spEditor.commit();
					String playpath = DBUtil.getMusicPath(musicid);
					Intent intentstop = new Intent(Constant.MUSIC_CONTROL);
					intentstop.putExtra("cmd",Constant.COMMAND_PLAY);
					intentstop.putExtra("path", playpath);
					getActivity().sendBroadcast(intentstop);	
				}
          }
        	  );
          main_ll_localmusic.setOnTouchListener(new OnTouchListener()
          {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				TextView main_tv_localmusic = (TextView)v.findViewById(R.id.main_textview_localmusic);
				TextView main_tv_musicCount = (TextView)v.findViewById(R.id.main_textview_summusic);
			    LinearLayout main_ll_localmusic = (LinearLayout)v.findViewById(R.id.main_linearlayout_localmusic);
			    switch(event.getAction())
			    {
			    case MotionEvent.ACTION_DOWN:
			    	main_tv_localmusic.setTextColor(getResources().getColor(R.color.white));
			    	main_tv_musicCount.setTextColor(getResources().getColor(R.color.white));
			    	main_ll_localmusic.setBackgroundColor(getResources().getColor(R.color.blue));
			      break;
			    case MotionEvent.ACTION_UP:
			    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			    	MusicFragmentLocalmusic fragment = new MusicFragmentLocalmusic();
			    	changeFragment(fragmentTransaction,fragment);
			    case MotionEvent.ACTION_CANCEL:
					main_tv_localmusic.setTextColor(getResources().getColor(R.color.black));
					main_tv_musicCount.setTextColor(getResources().getColor(R.color.black));
					main_ll_localmusic.setBackgroundColor(getResources().getColor(R.color.none));
					break;
			    }
				return false;
			}
        	  
          });
         LinearLayout main_ll_ilike = (LinearLayout)v.findViewById(R.id.main_linearlayout_ilove);
         main_ll_ilike.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				LinearLayout lltemp = (LinearLayout)v;
				TextView tvtemp = (TextView)lltemp.getChildAt(1);
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					tvtemp.setTextColor(getResources().getColor(R.color.white));
					break;
				case MotionEvent.ACTION_UP:
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					MusicFragmentFour fragment = new MusicFragmentFour(Constant.FRAGMENT_ILIKE);
					changeFragment(fragmentTransaction,fragment);
				case MotionEvent.ACTION_CANCEL:
					tvtemp.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				return false;
			}
         });
          LinearLayout main_ll_mylist = (LinearLayout)v.findViewById(R.id.main_linearlayout_mylist);
          main_ll_mylist.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				LinearLayout lltemp = (LinearLayout)v;
				TextView tvtemp = (TextView)lltemp.getChildAt(1);
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					tvtemp.setTextColor(getResources().getColor(R.color.white));
				    break;
				case MotionEvent.ACTION_UP:
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					MusicFragmentPlaylist fragment = new MusicFragmentPlaylist();
					changeFragment(fragmentTransaction,fragment);
				case MotionEvent.ACTION_CANCEL:
					tvtemp.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				
				return false;
			}
          }); 
          LinearLayout main_ll_download = (LinearLayout) v.findViewById(R.id.main_linearlayout_download);
  		main_ll_download.setOnTouchListener(new OnTouchListener() 
  		{
  			@Override
  			public boolean onTouch(View v, MotionEvent event) 
  			{
  				LinearLayout lltemp = (LinearLayout) v;
  				TextView tvtemp = (TextView) lltemp.getChildAt(1);
  				switch (event.getAction()) 
  				{
  				case MotionEvent.ACTION_DOWN:
  					tvtemp.setTextColor(getResources().getColor(R.color.white));
  					break;
  				case MotionEvent.ACTION_UP:
  					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
  					MusicFragmentFour fragment=new MusicFragmentFour(Constant.FRAGMENT_DOWNLOAD);
  					changeFragment(fragmentTransaction, fragment);
  				case MotionEvent.ACTION_CANCEL:
  					tvtemp.setTextColor(getResources().getColor(R.color.black));
  					break;
  				}
  				return false;
  			}
  		});	
  		LinearLayout main_ll_lastplay = (LinearLayout) v.findViewById(R.id.main_linearlayout_lastplay);
		main_ll_lastplay.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				LinearLayout lltemp = (LinearLayout) v;
				TextView tvtemp = (TextView) lltemp.getChildAt(1);
				switch (event.getAction()) 
				{
				case MotionEvent.ACTION_DOWN:
					tvtemp.setTextColor(getResources().getColor(R.color.white));
					break;
				case MotionEvent.ACTION_UP:
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					MusicFragmentFour fragment=new MusicFragmentFour(Constant.FRAGMENT_LASTPLAY);
					changeFragment(fragmentTransaction, fragment);
				case MotionEvent.ACTION_CANCEL:
					tvtemp.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				return false;
			}
		});
		LinearLayout main_ll_musiclibrary = (LinearLayout) v.findViewById(R.id.main_linearlayout_musiclibrary);
		main_ll_musiclibrary.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				LinearLayout lltemp = (LinearLayout) v;
				TextView tvtemp = (TextView) lltemp.getChildAt(1);
				switch (event.getAction()) 
				{
				case MotionEvent.ACTION_DOWN:
					tvtemp.setTextColor(getResources().getColor(R.color.white));
					break;
				case MotionEvent.ACTION_UP:
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					MusicFragmentWeb fragment = new MusicFragmentWeb();
					changeFragment(fragmentTransaction, fragment);
				case MotionEvent.ACTION_CANCEL:
					tvtemp.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				return false;
			}
		});
		LinearLayout main_ll_singerlibrary = (LinearLayout) v.findViewById(R.id.main_linearlayout_singerlibrary);
		main_ll_singerlibrary.setOnTouchListener(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				LinearLayout lltemp = (LinearLayout) v;
				TextView tvtemp = (TextView) lltemp.getChildAt(1);
				switch (event.getAction()) 
				{
				case MotionEvent.ACTION_DOWN:
					tvtemp.setTextColor(getResources().getColor(R.color.white));
					break;
				case MotionEvent.ACTION_UP:
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					MusicFragmentWebOther fragment = new MusicFragmentWebOther(Constant.FRAGMENT_SINGER);
					changeFragment(fragmentTransaction,fragment);
				case MotionEvent.ACTION_CANCEL:
					tvtemp.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				return false;
			}
		});
		LinearLayout temp1 = (LinearLayout) v.findViewById(R.id.main_linearlayout_mv);
		temp1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getActivity(), "MV������δ����", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout temp2 = (LinearLayout) v.findViewById(R.id.main_linearlayout_near);
		temp2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getActivity(), "����������δ����", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout temp3 = (LinearLayout) v.findViewById(R.id.main_linearlayout_more);
		temp3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getActivity(), "���๦����δ����", Toast.LENGTH_SHORT).show();
			}
		});
		LinearLayout temp4 = (LinearLayout) v.findViewById(R.id.main_linearlayout_game);
		temp4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getActivity(), "��Ϸ������δ����", Toast.LENGTH_SHORT).show();
			}
		});
        	  
    	  return v;
    }
    @Override
    public void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	TextView main_tv_musicCount = (TextView)getActivity().findViewById(R.id.main_textview_summusic);
    	main_tv_musicCount.setText(DBUtil.getMusicCount() + "��");
    }
	public static void changeFragment(FragmentTransaction fragmentTransaction,
			Fragment fragment) {
		// TODO Auto-generated method stub
		fragmentTransaction.setCustomAnimations(R.animator.click_enter,R.animator.click_exit,R.animator.back_enter,R.animator.back_exit);
		fragmentTransaction.replace(R.id.main_linearlayout_l, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}


