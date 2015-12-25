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

	int rows=JLookMusicPanel.jt_Music.getSelectedRow();					//��ȡѡ�����
	JComboBox j_albumlist;          									//�����б�	
	
	JLabel j_song;
	JLabel j_singer;
	JTextField j_album=new JTextField();

	JButton j_ok=new JButton("ȷ��");
	
	public JMakeMusicEditPanel()
	{
		String album=NetInfoUtil.getAlbumsNameForList();				//��ȡר��
		String[] albumlist=album.split("<#>\\|");
		
		this.setLayout(null);

		Label title=new Label("�༭����");
		this.add(title);
		title.setFont(new Font("����",Font.BOLD,25));
		title.setBounds(320,0,120, 50);
		
		JLabel songname=new JLabel("������:");
		this.add(songname);
		songname.setFont(new Font("����",Font.BOLD,20));
		songname.setBounds(130,80,120, 30);
		
		j_song=new JLabel(JMakeMusicTable.content[rows][1]);
		this.add(j_song);
		j_song.setFont(new Font("����",Font.ITALIC,18));
		j_song.setBounds(250,80,300,30);
		
		
		JLabel singer=new JLabel("������:");
		this.add(singer);
		singer.setFont(new Font("����",Font.BOLD,20));
		singer.setBounds(130,150,120, 30);
		
		j_singer=new JLabel(JMakeMusicTable.content[rows][2]);
		this.add(j_singer);
		j_singer.setFont(new Font("����",Font.ITALIC,18));
		j_singer.setBounds(250,150,300,30);
		

		JLabel albumname=new JLabel("ר����:");
		this.add(albumname);
		albumname.setFont(new Font("����",Font.BOLD,20));
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
				String song="";												//����һ���ַ��������ռ�������Ϣ
				song=j_song.getText()+"<#>";
				song+=j_singer.getText()+"<#>";
				song+=j_albumlist.getSelectedItem();

				NetInfoUtil.modifySong(song);								//���¸������ݿ�
				JOptionPane.showMessageDialog(null,"�޸ĳɹ�");				//��ʾ�޸ĳɹ�
			}
		}
	}
}
