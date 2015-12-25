package com.bn.musicpanel;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bn.util.NetInfoUtil;

public class JMakeMusicEditPanel extends JPanel
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int rows=JLookMusicPanel.jt_Music.getSelectedRow();					//获取选择的行
	JComboBox j_albumlist;          									//下拉列表	
	
	JLabel j_song;
	JLabel j_singer;
	JTextField j_album=new JTextField();

	JButton j_ok=new JButton("确定");
	
	public JMakeMusicEditPanel()
	{
		String album=NetInfoUtil.getAlbumsNameForList();				//获取专辑
		String[] albumlist=album.split("<#>\\|");
		
		this.setLayout(null);

		Label title=new Label("编辑歌曲");
		this.add(title);
		title.setFont(new Font("宋体",Font.BOLD,25));
		title.setBounds(320,0,120, 50);
		
		JLabel songname=new JLabel("歌曲名:");
		this.add(songname);
		songname.setFont(new Font("宋体",Font.BOLD,20));
		songname.setBounds(130,80,120, 30);
		
		j_song=new JLabel(JMakeMusicTable.content[rows][1]);
		this.add(j_song);
		j_song.setFont(new Font("宋体",Font.ITALIC,18));
		j_song.setBounds(250,80,300,30);
		
		
		JLabel singer=new JLabel("歌手名:");
		this.add(singer);
		singer.setFont(new Font("宋体",Font.BOLD,20));
		singer.setBounds(130,150,120, 30);
		
		j_singer=new JLabel(JMakeMusicTable.content[rows][2]);
		this.add(j_singer);
		j_singer.setFont(new Font("宋体",Font.ITALIC,18));
		j_singer.setBounds(250,150,300,30);
		

		JLabel albumname=new JLabel("专辑名:");
		this.add(albumname);
		albumname.setFont(new Font("宋体",Font.BOLD,20));
		albumname.setBounds(130,220,120, 30);
				
		j_albumlist=new JComboBox(albumlist);
		j_albumlist.setSelectedItem(JMakeMusicTable.content[rows][3]);
		j_albumlist.setMaximumRowCount(10);
		this.add(j_albumlist);
		j_albumlist.setBounds(250,220,200,30);
				
		this.add(j_ok);
		j_ok.setBounds(320,300, 120,30);
		j_ok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==j_ok)
		{			
			{
				String song="";												//创建一个字符串用于收集歌曲信息
				song=j_song.getText()+"<#>";
				song+=j_singer.getText()+"<#>";
				song+=j_albumlist.getSelectedItem();

				NetInfoUtil.modifySong(song);								//更新歌曲数据库
				JOptionPane.showMessageDialog(null,"修改成功");				//提示修改成功
			}
		}
	}
}
