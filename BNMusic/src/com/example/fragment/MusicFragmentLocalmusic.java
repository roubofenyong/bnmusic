package com.example.fragment;

import java.util.ArrayList;

import com.example.activity.MusicActivityScan;
import com.example.bnmusic.R;
import com.example.receiver.MusicUpdateMain;
import com.example.util.Constant;
import com.example.util.DBUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MusicFragmentLocalmusic extends Fragment {

	public static BaseAdapter ba;
    private PopupWindow popupWindow;
    MusicUpdateMain mu;
    ArrayList<Integer> musiclist;
    ArrayList<String[]> playlist;
    ListView lv;
    int selectTemp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View v = inflater.inflate(R.layout.fragment_localmusic_listview,container,false);
    	LinearLayout ll_title_bar = (LinearLayout)v.findViewById(R.id.localmusic_linearlayout_titlebar);
    	LinearLayout ll_edit_bar = (LinearLayout)v.findViewById(R.id.localmusic_linearlayout_editbar);
    	LinearLayout ll_title = (LinearLayout)inflater.inflate(R.layout.fragment_localmusic_title,null);
    	LinearLayout ll_edit1 = (LinearLayout)inflater.inflate(R.layout.fragment_localmusic_edit_l1,null);
    	ImageButton ib_back = (ImageButton)ll_title.findViewById(R.id.title_imagebutton_back_l);
    	ib_back.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.popBackStack();
			}
    	});
    	ll_edit1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getActivity().getSharedPreferences("music",
						Context.MODE_MULTI_PROCESS);
				int musicid = sp.getInt(Constant.SHARED_ID, -1);
				if (musicid == -1) 
				{
					Intent intent = new Intent(Constant.MUSIC_CONTROL);
					intent.putExtra("cmd", Constant.COMMAND_STOP);
					getActivity().sendBroadcast(intent);
					Toast.makeText(getActivity(), "����������",Toast.LENGTH_LONG).show();
					return;
				}
				SharedPreferences.Editor spEditor = sp.edit();
				musicid = DBUtil.getRandomMusic(musiclist, musicid);
				spEditor.putInt(Constant.SHARED_ID, musicid);
				spEditor.putInt(Constant.SHARED_LIST, Constant.LIST_ALLMUSIC);
				spEditor.commit();
				sendintent(Constant.STATUS_STOP);
				for(int i=0;i<musiclist.size();i++)
				{
					if (musiclist.get(i)==musicid)
					{
						lv.setSelection(i);
					}
				}
			}
		});
    	ImageView iv_search = (ImageView) ll_title.findViewById(R.id.title_imagebutton_search_l);
		iv_search.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//��������
				FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
				MusicFragmentSearch fragment = new MusicFragmentSearch();
				MusicFragmentMain.changeFragment(fragmentTransaction, fragment);
			}
		});
        ImageView iv_menu = (ImageView)ll_title.findViewById(R.id.title_imagebutton_menu_l);
        iv_menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				RelativeLayout mpopupwindow = (RelativeLayout)inflater.inflate(R.layout.fragment_localmusic_popup, null);
				mpopupwindow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				});
				Animation popAnim = AnimationUtils.loadAnimation(getActivity() , R.anim.pop_menu);
				LinearLayout ll_main = (LinearLayout)mpopupwindow.getChildAt(0);
				ll_main.setAnimation(popAnim);
				LinearLayout ll_scan = (LinearLayout)mpopupwindow.findViewById(R.id.localmusic_popup_linearlayout_scan);
				ll_scan.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
						Intent intent = new Intent(getActivity(),MusicActivityScan.class);
					    getActivity().startActivity(intent);
					}
				});
				popupWindow = new PopupWindow(mpopupwindow, LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT, true);
				popupWindow.setOutsideTouchable(true);
				popupWindow.setFocusable(true);
				popupWindow.setAnimationStyle(R.anim.none);
				popupWindow.setBackgroundDrawable(new ColorDrawable(0));
				popupWindow.showAsDropDown(v, 0, 0);
			}
		});
      //Ƕ��view
      		ll_title_bar.addView(ll_title);
      		ll_edit_bar.addView(ll_edit1);

      		return v;
    }
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	musiclist = DBUtil.getMusicList(Constant.LIST_ALLMUSIC);
    	setListView();
    }
	private void setListView() {
		// TODO Auto-generated method stub
		ba = new BaseAdapter()
		{
           LayoutInflater inflater = LayoutInflater.from(getActivity());
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return musiclist.size() + 1;
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
				if(position == musiclist.size())
				{
					LinearLayout lll = (LinearLayout)inflater.inflate(R.layout.listview_count,null).findViewById(R.id.linearlayout_null);
				    TextView tv_sum = (TextView)lll.getChildAt(0);
				    tv_sum.setText("����" + musiclist.size() + "�׸���\n\n\n");
				    return lll;
				}
				SharedPreferences sp = getActivity().getSharedPreferences
						("music",Context.MODE_MULTI_PROCESS);
				int musicid = sp.getInt(Constant.SHARED_ID, -1);

				String musicName = DBUtil.getMusicInfo(musiclist.get(position)).get(2);
				musicName+="-"+DBUtil.getMusicInfo(musiclist.get(position)).get(1);

				LinearLayout ll = (LinearLayout) inflater.inflate
						(R.layout.fragment_localmusic_listview_row,
						null).findViewById(R.id.LinearLayout_row);
				TextView tv = (TextView) ll.getChildAt(1);
				tv.setText(musicName);

				if (musiclist.get(position)==musicid)
				{
					tv.setTextColor(getResources().getColor(R.color.blue));
				}
				return ll;
			}
			 
		};
		lv = (ListView)getActivity().findViewById(R.id.localmusci_listview_musiclist);
		lv.setAdapter(ba);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == parent.getCount() - 1)
				{
					return;
				}
				SharedPreferences sp = getActivity().getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
				SharedPreferences.Editor spEditor = sp.edit();
				spEditor.putInt(Constant.SHARED_LIST, Constant.LIST_ALLMUSIC);
				spEditor.commit();
				int musicid = musiclist.get(position);
				boolean flag;
				int oldmusicplay = sp.getInt(Constant.SHARED_ID,-1);
				if(oldmusicplay == musicid)
				{
					flag = false;
				}
				else
				{
					flag = true;
					spEditor.putInt(Constant.SHARED_ID, musicid);
					spEditor.commit();
				}
				ArrayList<String> musicinfo = DBUtil.getMusicInfo(musicid);
				TextView tv_gequ = (TextView) getActivity().findViewById(R.id.main_textview_gequ);
				TextView tv_geshou = (TextView) getActivity().findViewById(R.id.main_textview_geshou);
				tv_gequ.setText(musicinfo.get(1));
				tv_geshou.setText(musicinfo.get(2));
				if (flag) 
				{
					sendintent(Constant.STATUS_STOP);
				} 
				else 
				{
					if (MusicUpdateMain.status == Constant.STATUS_PLAY)
					{
						sendintent(Constant.STATUS_PLAY);
					} 
					else if (MusicUpdateMain.status == Constant.STATUS_STOP) 
					{
						sendintent(Constant.STATUS_STOP);
					}
					else 
					{
						sendintent(Constant.STATUS_PAUSE);
					}
			}
			}	
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectTemp = position;
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setTitle("���๦��");
				builder.setItems(new String[]{"ɾ������","��ӵ��赥"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(which)
						{
						case 0:
							int musicTemp = musiclist.get(selectTemp);
							DBUtil.deleteMusic(musicTemp);
							SharedPreferences sp = getActivity().getSharedPreferences("music", Context.MODE_MULTI_PROCESS);
							int musicid = sp.getInt(Constant.SHARED_ID, -1);
							musiclist = DBUtil.getMusicList(Constant.LIST_ALLMUSIC);
							if(musicid == musicTemp)
							{
								if(musiclist.isEmpty())
								{
									musicid = -1;
								}else
								{
									musicid = musiclist.get(0);
								}
								SharedPreferences.Editor spEditor = sp.edit();
								spEditor.putInt(Constant.SHARED_ID, musicid);
								spEditor.commit();
								Intent intent_start = new Intent(Constant.MUSIC_CONTROL);
								intent_start.putExtra("cmd", Constant.COMMAND_PLAY);
                                intent_start.putExtra("path", DBUtil.getMusicPath(musicid));
                                getActivity().sendBroadcast(intent_start);
                                Intent intent_pause = new Intent(Constant.MUSIC_CONTROL);
                                intent_pause.putExtra("cmd", Constant.COMMAND_PAUSE);
                                getActivity().sendBroadcast(intent_pause);
							}
							ba.notifyDataSetChanged();
							break;
						case 1:
							playlist = DBUtil.getPlayList();
							LinearLayout ll_list = new LinearLayout(getActivity());
							ll_list.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT,android.widget.LinearLayout.LayoutParams.FILL_PARENT));
							ll_list.setLayoutDirection(0);
							ListView listview = new ListView(getActivity());
							listview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
							ll_list.addView(listview);
							final AlertDialog aDialog = new AlertDialog.Builder(getActivity()).create();
						    aDialog.setTitle("�赥�б�("+playlist.size()+")");
						    aDialog.setView(ll_list);
						    aDialog.show();
						    BaseAdapter ba = new BaseAdapter()
						    {
                             LayoutInflater inflater = LayoutInflater.from(getActivity());
								@Override
								public int getCount() {
									// TODO Auto-generated method stub
									return  playlist.size();
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
								public View getView(int position,
										View convertView, ViewGroup parent) {
									// TODO Auto-generated method stub
									String musicName = playlist.get(position)[1];
									LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.list_w,null);
									TextView tvtemp = (TextView)ll.getChildAt(0);
									tvtemp.setText((position+1)+"");
									TextView tv = (TextView)ll.getChildAt(1);
									tv.setText(musicName);
									return ll;
								}
						    	
						    };
						    listview.setAdapter(ba);
						    listview.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									aDialog.cancel();
									int listid = Integer.parseInt(playlist.get(position)[0]);
									DBUtil.setMusicInPlaylist(musiclist.get(selectTemp), listid);
								}
						    	
							}); 
						}
						dialog.dismiss();
					}
				} ).create().show();
				return false;
			}
		});
		lv.setOnCreateContextMenuListener(this);
	}
	private void sendintent(int code) 
	{
		switch (code) {
		case Constant.STATUS_PLAY:
			Intent intentplay = new Intent(Constant.MUSIC_CONTROL);
			intentplay.putExtra("cmd", Constant.COMMAND_PAUSE);
			this.getActivity().sendBroadcast(intentplay);
			break;
		case Constant.STATUS_STOP:
			SharedPreferences sp = getActivity().getSharedPreferences
					("music",Context.MODE_MULTI_PROCESS);
			int musicid = sp.getInt(Constant.SHARED_ID, -1);
			String playpath = DBUtil.getMusicPath(musicid);
			Intent intentstop = new Intent(Constant.MUSIC_CONTROL);
			intentstop.putExtra("cmd", Constant.COMMAND_PLAY);
			intentstop.putExtra("path", playpath);
			this.getActivity().sendBroadcast(intentstop);
			break;
		case Constant.STATUS_PAUSE:
			Intent intentpause = new Intent(Constant.MUSIC_CONTROL);
			intentpause.putExtra("cmd", Constant.COMMAND_PLAY);
			this.getActivity().sendBroadcast(intentpause);
			break;
		}
	}
}
class MyOnClickListener implements OnClickListener
{
    LinearLayout box,edit;
    
	public MyOnClickListener(LinearLayout box, LinearLayout edit) {
		super();
		this.box = box;
		this.edit = edit;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		box.removeAllViews();
		box.addView(edit);
	}
	
}