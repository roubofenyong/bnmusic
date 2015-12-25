package com.example.fragment;

import java.util.List;

import com.example.activity.MusicActivityMain;
import com.example.bnmusic.R;
import com.example.util.Constant;
import com.example.util.DBUtil;
import com.example.util.DownloadMP3;
import com.example.util.NetInfoUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MusicFragmentSearch extends Fragment {
	String searchStr;
	ListView lv_search;
	ImageButton ib_back;
	ImageView iv_search;
	LinearLayout ll_search;
	EditText et_search;
	private ProgressDialog loadDialog;
	private Handler handle;
	RelativeLayout rl_search;
	BaseAdapter ba;
	LinearLayout ll_error;
	List<String[]> musicList;
	int selectTemp;
	public MusicFragmentSearch()
	{
		searchStr = null;
	}
	public MusicFragmentSearch(String searchStr) {
		// TODO Auto-generated constructor stub
		this.searchStr = searchStr;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search , container , false);
        rl_search = (RelativeLayout)inflater.inflate(R.layout.loading, null);
        lv_search = (ListView) v.findViewById(R.id.search_listview_resultlist);
		ib_back = (ImageButton) v.findViewById(R.id.search_imagebutton_back_l);
		iv_search = (ImageView) v.findViewById(R.id.search_imageview_search);
		ll_search = (LinearLayout) v.findViewById(R.id.search_linearlayou_resultlist);
		et_search=(EditText)v.findViewById(R.id.main_textview_search);
        loadDialog = new ProgressDialog(getActivity());
        handle = new Handler()
        {
        	public void handleMessage(android.os.Message msg) {
        		super.handleMessage(msg);
        		switch(msg.what)
        		{
        		case Constant.LOAD_PREPARE:
        			ll_search.removeAllViews();
        			ll_search.addView(rl_search);
        			break;
        		case Constant.LOAD_COMPLETE:
        			ll_search.removeAllViews();
        			ll_search.addView(lv_search);
        			ba.notifyDataSetChanged();
        			break;
        		case Constant.DOWNLOAD_UPDATE:
        			Bundle b = msg.getData();
        			int progressTemp =b.getInt("download");
        			loadDialog.setProgress(progressTemp);
        			break;
        		case Constant.LOAD_ERROR:
        			ll_search.removeAllViews();
        			ll_error.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        		    ll_search.addView(ll_error);
        		    break;
        		}
        	}
        };
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() 
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) 
			{
				if(hasFocus)
				{
					et_search.setText("");
				}
			}
		});
        ib_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				if(getFragmentManager().getBackStackEntryCount() > 1)
				{
					fragmentManager.popBackStack();
				}
			}
		});
        iv_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
                     handle.sendEmptyMessage(Constant.LOAD_PREPARE);
					 try
					 {
						 musicList = NetInfoUtil.searchSong(et_search.getText().toString().trim());
						 if(musicList == null)
						 {
							 handle.sendEmptyMessage(Constant.LOAD_ERROR);
							 return;
						 }
						 ba = new BaseAdapter()
						 {
							 LayoutInflater inflater = LayoutInflater.from(getActivity());
							 int count = 1;
							 public int getCount()
							 {
								 count = musicList.size() + 1;
								 return count;
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
							public View getView(int position, View convertView,
									ViewGroup parent) {
								// TODO Auto-generated method stub
								if(count == 1)
								{
									LinearLayout lll = (LinearLayout)inflater.inflate(R.layout.listview_count, null).findViewById(R.id.linearlayout_null);
									TextView tv_sum = (TextView)lll.getChildAt(0);
									tv_sum.setText("很遗憾，结果空空如也" + "\n\n\n");
									return lll;
								}
								if(position == musicList.size())
								{
									LinearLayout lll = (LinearLayout)inflater.inflate(R.layout.listview_count, null).findViewById(R.id.linearlayout_null);
									TextView tv_sum = (TextView)lll.getChildAt(0);
									tv_sum.setText("共有" +(count-1)+"个结果"+"\n\n\n");
									return lll;
								}
								String musicName = musicList.get(position)[1]+"-"+musicList.get(position)[0];
								LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.fragment_localmusic_listview_row, null);
								TextView tv = (TextView)ll.getChildAt(1);
								tv.setText(musicName);
								return ll;
							}
							 
						 };
						 lv_search.setAdapter(ba);
						 lv_search.setOnItemClickListener(new OnItemClickListener(){

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								if(position == parent.getCount() -1)
								{
									return;
								}
								selectTemp = position;
								AlertDialog.Builder builder = new Builder(getActivity());
								builder.setMessage(musicList.get(selectTemp)[0]);
								builder.setTitle("下载");
								builder.setPositiveButton("确定", new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										loadDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
										loadDialog.setTitle("正在下载");
										loadDialog.setMessage(musicList.get(selectTemp)[4]);
										loadDialog.setIcon(R.drawable.webmusic_download);
										loadDialog.setIndeterminate(true);
										loadDialog.setMax(100);
										loadDialog.setCancelable(false);
										loadDialog.show();
										new Thread()
										{
											public void run()
											{
												int musicid = -1;
												try
												{
													musicid = DownloadMP3.download(musicList.get(selectTemp)[3],musicList.get(selectTemp)[4],handle);
												}
												catch(Exception e)
												{
													e.printStackTrace();
												}
												finally
												{
													loadDialog.dismiss();
												}
												if(musicid != -1)
												{
													SharedPreferences sp = getActivity().getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
													SharedPreferences.Editor spEditor = sp.edit();
													spEditor.putInt(Constant.SHARED_LIST, Constant.LIST_ALLMUSIC);
													spEditor.putInt(Constant.SHARED_ID, musicid);
													spEditor.commit();
													String playpath = DBUtil.getMusicPath(musicid);
													Intent intentstop = new Intent(Constant.MUSIC_CONTROL);
													intentstop.putExtra("cmd", Constant.COMMAND_PLAY);
													intentstop.putExtra("path", playpath);
													getActivity().sendBroadcast(intentstop);
													try
													{
														FragmentManager fm = getFragmentManager();
														fm.popBackStack(MusicActivityMain.mainFragmentId,0);
													}catch(Exception e)
													{
														e.printStackTrace();
													}
												}
												
											}
										
										}.start();
									}
								});
								builder.setNegativeButton("取消",new OnClickListener()
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
						 
						 
					 }catch(Exception e)
					 {
						 e.printStackTrace();
					 }
					handle.sendEmptyMessage(Constant.LOAD_COMPLETE);
					}
				}.start();
			}
		});
        if(searchStr!=null && !searchStr.isEmpty())
		{
			et_search.setText(searchStr);
			iv_search.callOnClick();
		}
    	return v;
    }
}
