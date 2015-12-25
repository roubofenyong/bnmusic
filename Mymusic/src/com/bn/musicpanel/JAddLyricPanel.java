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
		
		String song=NetInfoUtil.getSongFileNameForList();				//��ȡ�����б�
		String []songlist=song.split("<#>\\|");
		
		this.setLayout(null);
		
		jtitle=new JLabel("��Ӹ��");
		this.add(jtitle);
		jtitle.setFont(new Font("����",Font.CENTER_BASELINE,25));
		jtitle.setBounds(350,50,200,50);
		
		jfilenameL=new JLabel("������  :");
		this.add(jfilenameL);
		jfilenameL.setFont(new Font("����",Font.CENTER_BASELINE,20));
		jfilenameL.setBounds(200,150,100,30);
		
		jlookfilenameC=new JComboBox(songlist);
		this.add(jlookfilenameC);
		jlookfilenameC.setBounds(300,150,300,30);
		
		
		jlyricnameL=new JLabel("����� :");
		this.add(jlyricnameL);
		jlyricnameL.setFont(new Font("����",Font.CENTER_BASELINE,20));
		jlyricnameL.setBounds(200,250,100,30);
		
		jlyricnameT=new JTextField();
		this.add(jlyricnameT);
		jlyricnameT.setBounds(300, 250, 250, 30);
		
		jOpen=new JButton("��");
		this.add(jOpen);
		jOpen.setBounds(550, 250, 100, 30);
		jOpen.addActionListener(this);
		
		JLabel ta=new JLabel("ע�⣺����*��Ϊ����ѡ��");
		ta.setFont(new Font("����",Font.BOLD,15));
		ta.setForeground(Color.blue);
		this.add(ta);
		ta.setBounds(300,330,400,30);
		
		jadd=new JButton("ȷ��");
		this.add(jadd);
		jadd.setBounds(350, 400, 100, 30);
		jadd.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jOpen)
		{
			//ֻ��krc,lrc���ļ��������ļ�ѡ����
			FileNameExtensionFilter filter=new FileNameExtensionFilter("(lrc & krc) LYRIC","LRC","KRC");
			jchooser=new JFileChooser();
			jchooser.setFileFilter(filter);
			
			//�ж��Ƿ�ѡ�����ļ�
			int jreturnVal=jchooser.showOpenDialog(this);
			
			if(jreturnVal==JFileChooser.APPROVE_OPTION)
			{
				System.out.println("chooser   "+JFileChooser.APPROVE_OPTION);
				jlyricnameT.setText(jchooser.getSelectedFile().getName());
				
			}
	
		}else if(e.getSource()==jadd)
		{
			if(jlyricnameT.getText().equals(""))								//������û����ӣ�����ʾû��ѡ���ļ�
			{
				JOptionPane.showMessageDialog(null, "��ѡ�����ļ�");
			}else
			{
				String all=jlookfilenameC.getSelectedItem()+"<#>";				//�ռ���ӵ���Ϣ
				all+=jlyricnameT.getText();
				
				//��ȡ�ļ�·�����ļ���
				File f=new File(jchooser.getCurrentDirectory()+"\\"+jchooser.getSelectedFile().getName());
				
				NetInfoUtil.addLyric(f,all);									//�ϴ���ʵ����������������ݿ�
				
				JOptionPane.showMessageDialog(null, "��ӳɹ�");					//��ʾ�ᰢ��ӳɹ�
			}
		}
	}
}
