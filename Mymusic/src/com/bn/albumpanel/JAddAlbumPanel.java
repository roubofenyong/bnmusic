package com.bn.albumpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bn.musicpanel.JAddMusicPanel;
import com.bn.picpanel.JAddPicPanel;
import com.bn.util.NetInfoUtil;

public class JAddAlbumPanel extends JPanel
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JComboBox j_singerC=new JComboBox();
	
	JButton jadd=new JButton("确定");
	JTextField j_albumname=new JTextField();
	
	public JAddAlbumPanel()
	{
		this.setLayout(null);
		
		String singer=NetInfoUtil.getSingerNameForList();		//获取歌手
		String[] singerlist=singer.split("<#>\\|");

		JLabel j_title=new JLabel("添加专辑");
		j_title.setFont(new Font("宋体",Font.BOLD,25));
		this.add(j_title);
		j_title.setBounds(370,50,150,50);
		
		JLabel j_singer=new JLabel("歌手名：");
		j_singer.setFont(new Font("宋体",Font.BOLD,20));
		this.add(j_singer);
		j_singer.setBounds(220,120,120,30);
		
		j_singerC=new JComboBox(singerlist);
		j_singerC.setMaximumRowCount(10);
		this.add(j_singerC);
		j_singerC.setBounds(320,120,300,30);
		
		JLabel j_music=new JLabel("专辑名:");
		j_music.setFont(new Font("宋体",Font.BOLD,20));
		this.add(j_music);
		j_music.setBounds(220,200,120,30);
				
		this.add(j_albumname);
		j_albumname.setBounds(320,200,300,30);
		
		JLabel j_music_need=new JLabel("*");
		j_music_need.setFont(new Font("宋体",Font.BOLD,20));
		j_music_need.setForeground(Color.red);
		this.add(j_music_need);
		j_music_need.setBounds(620,200,50,30);
		
		JLabel ta=new JLabel("注意：带“*”为必填选项");
		ta.setFont(new Font("宋体",Font.BOLD,18));
		ta.setForeground(Color.blue);
		this.add(ta);
		ta.setBounds(300,360,400,30);
				
		jadd.setFont(new Font("宋体",Font.BOLD,25));
		this.add(jadd);
		jadd.setBounds(370,300,100,50);
	
		jadd.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jadd)
		{
			if(j_albumname.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "请输入专辑名");
			}else
			{
				String all=j_singerC.getSelectedItem()+"<#>";		//收集专辑信息
				all+=j_albumname.getText();
				
				String albumname=j_albumname.getText();				//把专辑名赋给一个字符串
				
				boolean bb=NetInfoUtil.addAlbum(all);				//添加专辑，并判断是否添加成功
				
				if(bb)
				{
					JAddPicPanel.j_albumC.addItem(albumname);
					JAddMusicPanel.j_albumC.addItem(albumname);
					
					j_albumname.setText("");						//专辑框设置为空
						
					JOptionPane.showMessageDialog(null, "添加成功");	//提示添加成功	
					
					
					new MyTableModel();
				}else
				{
					JOptionPane.showMessageDialog(null, "添加失败");	//提示添加失败
				}
					
			}
		}
	}
}
