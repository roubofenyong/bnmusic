package com.bn.musicpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.bn.util.NetInfoUtil;

public class JLookMusicPanel extends JPanel 
implements ActionListener
{
	/**
	 *  歌曲管理
	 */
	private static final long serialVersionUID = 1L;	
	
	//创建表格模型
	public static DefaultTableModel dtm_Music;
	public static JTable jt_Music;
	public JScrollPane jsp_Music;
	
	//收集需要查看的条件
	public static String song;
	
	JLabel jtitleL;
	JLabel jsingerL;
	JLabel jalbumL;
	JLabel jlyricL;
	
	//创建下拉列表
	public static JComboBox j_singerC=new JComboBox();
	public static JComboBox j_albumC=new JComboBox();
	public static JComboBox j_lyricC=new JComboBox();
	
	JPanel jtoolbarsong=new JPanel();
	JButton jaddmusic;
	JButton jaddlyric;
	JButton jsearch;
	public JLookMusicPanel()
	{
		//创建歌词下拉列表的内容
		String[] lyriclist={"所有","有","无"};
		
		//获取歌手下拉列表的内容
		String singer=NetInfoUtil.getSingerNameForList();		
		String[] singerlist=singer.split("<#>\\|");
		//获取专辑下拉列表的内容
		String album=NetInfoUtil.getAlbumsNameForList();		//获取歌手
		String[] albumlist=album.split("<#>\\|");
		
		dtm_Music=new DefaultTableModel();
		jt_Music=new JTable(dtm_Music);							//显示歌曲的表格	
		jsp_Music=new JScrollPane(jt_Music,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );		
		
		this.setLayout(null);
		
		jtitleL=new JLabel("条件查看");
		jtitleL.setFont(new Font("宋体",Font.CENTER_BASELINE,15));
		jtitleL.setBounds(5,7,70,30);
		
		jsingerL=new JLabel("歌手名:");
		jsingerL.setFont(new Font("宋体",Font.BOLD,12));
		jsingerL.setBounds(80,5,50,30);
		
		j_singerC=new JComboBox(singerlist);
		j_singerC.insertItemAt("所有", 0);
		j_singerC.setSelectedIndex(0);
		j_singerC.setBounds(135,11,100, 20);
		
		jalbumL=new JLabel("专辑名:");
		jalbumL.setFont(new Font("宋体",Font.BOLD,12));
		jalbumL.setBounds(240,5,50, 30);
		
		j_albumC=new JComboBox(albumlist);
		j_albumC.insertItemAt("所有", 0);
		j_albumC.setSelectedIndex(0);
		j_albumC.setBounds(295,11,120, 20);
		
		jlyricL=new JLabel("歌词:");
		jlyricL.setFont(new Font("宋体",Font.BOLD,12));
		jlyricL.setBounds(420,5,50,30);
		
		j_lyricC=new JComboBox(lyriclist);
		j_lyricC.setBounds(460,11,60, 20);
		
		
		jsearch=new JButton("查找");
		jsearch.setBounds(530,7,70,30);
		jsearch.setFont(new Font("宋体",Font.CENTER_BASELINE,13));
		jsearch.addActionListener(this);
			
		
		jaddmusic=new JButton("添加歌曲");		
		jaddmusic.setBounds(610,7,90,30);
		jaddmusic.setFont(new Font("宋体",Font.CENTER_BASELINE,13));
		jaddmusic.addActionListener(this);
		
		jaddlyric=new JButton("添加歌词");
		jaddlyric.setBounds(710,7,90,30);
		jaddlyric.setFont(new Font("宋体",Font.CENTER_BASELINE,13));
		jaddlyric.addActionListener(this);
		
		jtoolbarsong.add(jtitleL);
		jtoolbarsong.add(jsingerL);
		jtoolbarsong.add(j_singerC);
		jtoolbarsong.add(jalbumL);
		jtoolbarsong.add(j_albumC);		
		jtoolbarsong.add(jlyricL);
		jtoolbarsong.add(j_lyricC);
		jtoolbarsong.add(jsearch);
		jtoolbarsong.add(jaddmusic);		
		jtoolbarsong.add(jaddlyric);
		jtoolbarsong.setLayout(null);
		jtoolbarsong.setBounds(0,0,800,50);
		
		this.add(jtoolbarsong);									//添加工具条
		this.add(jsp_Music);									//添加表格
		
		jt_Music.setRowHeight(30);								//设置行高		
		jt_Music.setAutoResizeMode(0);							//设置可以自由改变列宽
		jsp_Music.setBounds(20,50,780,560);						//设置表格大小	
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jaddmusic)
		{
			new JAddMusicFrame();								//打开添加歌曲JFrame
		}else if(e.getSource()==jaddlyric)
		{
			new JAddLyricFrame();								//打开添加歌词JFrame
		}else if(e.getSource()==jsearch)
		{
			//判断是搜索所有还是局部搜索
			if(j_singerC.getSelectedItem().equals("所有")&&j_albumC.getSelectedItem().equals("所有")&&j_lyricC.getSelectedItem().equals("所有"))
			{
				JMakeMusicTable.s=0;						
				new JMakeMusicTable();							//跟新音乐表格信息
			}else 	
			{
				song=j_singerC.getSelectedItem()+"<#>";			//收集添加查看的信息
				song+=j_albumC.getSelectedItem()+"<#>";
				song+=j_lyricC.getSelectedItem();
				
				JMakeMusicTable.s=1;
				new JMakeMusicTable();							//更新音乐列表的信息
			}
		}
	}	
}
