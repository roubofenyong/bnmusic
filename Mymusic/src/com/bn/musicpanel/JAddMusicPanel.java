package com.bn.musicpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.bn.util.NetInfoUtil;

public class JAddMusicPanel extends JPanel
implements ActionListener{
	/**
	 * 歌曲/添加歌曲
	 */
	private static final long serialVersionUID = 1L;
	
	//歌手，专辑列表内容
	public static String[] singerlist;
	public static String[] albumlist;
	
	JTextField j_filename=new JTextField();
	
	public static JComboBox j_singerC=new JComboBox();       	//歌手下拉列表
	public static JComboBox j_albumC=new JComboBox();          	//专辑下拉列表
	JButton jadd=new JButton("确定");
	JButton jOpen;           									// 打开文件
	JFileChooser jchooser = new JFileChooser();
	
	public JAddMusicPanel()
	{
		String singer=NetInfoUtil.getSingerNameForList();		//获取歌手内容
		String[] singerlist=singer.split("<#>\\|");
		j_singerC=new JComboBox(singerlist);
		
		String album=NetInfoUtil.getAlbumsNameForList();		//获取歌手内容
		String[] albumlist=album.split("<#>\\|");
		
		
		this.setLayout(null);		
		
		JLabel j_title=new JLabel("添加歌曲");
		j_title.setFont(new Font("宋体",Font.BOLD,25));
		this.add(j_title);
		j_title.setBounds(370,50,150,50);

		JLabel j_singer=new JLabel("歌手名：");
		j_singer.setFont(new Font("宋体",Font.BOLD,20));
		this.add(j_singer);
		j_singer.setBounds(220,120,120,30);
		
		j_albumC=new JComboBox(albumlist);
		this.add(j_singerC);
		j_singerC.setBounds(320,120,300,30);
		
		JLabel j_singer_need=new JLabel("*");
		j_singer_need.setFont(new Font("宋体",Font.BOLD,20));
		j_singer_need.setForeground(Color.red);
		this.add(j_singer_need);
		j_singer_need.setBounds(620,120,50,30);

		JLabel j_music=new JLabel("专辑名:");
		j_music.setFont(new Font("宋体",Font.BOLD,20));
		this.add(j_music);
		j_music.setBounds(220,200,120,30);
		
		
		this.add(j_albumC);
		j_albumC.setBounds(320,200,300,30);
		
		JLabel j_music_need=new JLabel("*");
		j_music_need.setFont(new Font("宋体",Font.BOLD,20));
		j_music_need.setForeground(Color.red);
		this.add(j_music_need);
		j_music_need.setBounds(620,200,50,30);		

		JLabel j_music_path=new JLabel("歌曲名：");
		j_music_path.setFont(new Font("宋体",Font.BOLD,20));
		this.add(j_music_path);
		j_music_path.setBounds(220,280,120,30);
		
		
		this.add(j_filename);
		j_filename.setBounds(320,280,260,30);
		
		jOpen=new JButton("打开文件夹");						//打开文件按钮
		this.add(jOpen);
		jOpen.setBounds(580,280,100,30);
		jOpen.addActionListener(this);
		
		JLabel ta=new JLabel("注意：带“*”为必填选项");
		ta.setFont(new Font("宋体",Font.BOLD,18));
		ta.setForeground(Color.blue);
		this.add(ta);
		ta.setBounds(300,330,400,30);

		this.add(jadd);										//确认
		jadd.setFont(new Font("宋体",Font.BOLD,25));	
		jadd.setBounds(370,400,100,50);
		jadd.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==jOpen){						//点击打开文件按钮
			//只显示后缀是MP3格式的文件	
			FileNameExtensionFilter filter = new FileNameExtensionFilter( "MP3 Music", "mp3");		
			jchooser.setFileFilter(filter);
			int returnVal = jchooser.showOpenDialog(this);				//判断是否选择了文件
			if(returnVal == JFileChooser.APPROVE_OPTION){				//如果选择了文件
				//输入框中添加文件名
				j_filename.setText(jchooser.getSelectedFile().getName());    
		}}else if(e.getSource()==jadd){									//点击确定按钮
			if(j_filename.getText().equals("")){						//判断输入框是否为空
				JOptionPane.showMessageDialog(null, "请选择要添加的歌曲");
			}else{
				String []song=j_filename.getText().split("-|\\.");		//从文件名中截取歌曲名到字符串数组中
				String all=j_singerC.getSelectedItem()+"<#>";			//创建一个字符串用于收集添加的歌曲信息
				all+=j_albumC.getSelectedItem()+"<#>";					//将专辑名添加到字符串中
				all+=song[1].trim()+"<#>";								//将歌曲名添加到字符串中
				all+=j_filename.getText();								//将文件名添加到字符串中
				//获取歌曲文件路径及歌曲文件名
				File file=new File(jchooser.getCurrentDirectory()+"\\"+jchooser.getSelectedFile().getName());
				boolean bb=NetInfoUtil.addSong(file,all);					//上传歌曲到服务器，并更新数据库
				if(bb){														//如果上传成功
					JOptionPane.showMessageDialog(null, "数据库已经成功接收！");	
					JAddLyricPanel.jlookfilenameC.addItem(j_filename.getText());
				}else{
					JOptionPane.showMessageDialog(null, "数据库没有接收信息！");
				}
				
			}
		}			
	}
}