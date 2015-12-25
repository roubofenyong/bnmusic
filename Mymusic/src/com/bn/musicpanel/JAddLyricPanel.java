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

public class JAddLyricPanel extends JPanel 
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel jtitle;
	JLabel jfilenameL;
	JLabel jlyricnameL;
	
	JTextField jlyricnameT;
	public static JComboBox jlookfilenameC=new JComboBox();
	JButton jOpen;
	JButton jadd;
	
	JFileChooser jchooser;
	public JAddLyricPanel()
	{
		
		String song=NetInfoUtil.getSongFileNameForList();				//获取歌曲列表
		String []songlist=song.split("<#>\\|");
		
		this.setLayout(null);
		
		jtitle=new JLabel("添加歌词");
		this.add(jtitle);
		jtitle.setFont(new Font("黑体",Font.CENTER_BASELINE,25));
		jtitle.setBounds(350,50,200,50);
		
		jfilenameL=new JLabel("歌曲名  :");
		this.add(jfilenameL);
		jfilenameL.setFont(new Font("黑体",Font.CENTER_BASELINE,20));
		jfilenameL.setBounds(200,150,100,30);
		
		jlookfilenameC=new JComboBox(songlist);
		this.add(jlookfilenameC);
		jlookfilenameC.setBounds(300,150,300,30);
		
		
		jlyricnameL=new JLabel("歌词名 :");
		this.add(jlyricnameL);
		jlyricnameL.setFont(new Font("黑体",Font.CENTER_BASELINE,20));
		jlyricnameL.setBounds(200,250,100,30);
		
		jlyricnameT=new JTextField();
		this.add(jlyricnameT);
		jlyricnameT.setBounds(300, 250, 250, 30);
		
		jOpen=new JButton("打开");
		this.add(jOpen);
		jOpen.setBounds(550, 250, 100, 30);
		jOpen.addActionListener(this);
		
		JLabel ta=new JLabel("注意：带“*”为必填选项");
		ta.setFont(new Font("宋体",Font.BOLD,15));
		ta.setForeground(Color.blue);
		this.add(ta);
		ta.setBounds(300,330,400,30);
		
		jadd=new JButton("确定");
		this.add(jadd);
		jadd.setBounds(350, 400, 100, 30);
		jadd.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jOpen)
		{
			//只看krc,lrc的文件，创建文件选择器
			FileNameExtensionFilter filter=new FileNameExtensionFilter("(lrc & krc) LYRIC","LRC","KRC");
			jchooser=new JFileChooser();
			jchooser.setFileFilter(filter);
			
			//判断是否选择了文件
			int jreturnVal=jchooser.showOpenDialog(this);
			
			if(jreturnVal==JFileChooser.APPROVE_OPTION)
			{
				System.out.println("chooser   "+JFileChooser.APPROVE_OPTION);
				jlyricnameT.setText(jchooser.getSelectedFile().getName());
				
			}
	
		}else if(e.getSource()==jadd)
		{
			if(jlyricnameT.getText().equals(""))								//如果歌词没有添加，则提示没有选择文件
			{
				JOptionPane.showMessageDialog(null, "请选择歌词文件");
			}else
			{
				String all=jlookfilenameC.getSelectedItem()+"<#>";				//收集添加的信息
				all+=jlyricnameT.getText();
				
				//获取文件路径和文件名
				File f=new File(jchooser.getCurrentDirectory()+"\\"+jchooser.getSelectedFile().getName());
				
				NetInfoUtil.addLyric(f,all);									//上传歌词到服务区，更新数据库
				
				JOptionPane.showMessageDialog(null, "添加成功");					//提示提阿添加成功
			}
		}
	}
}
