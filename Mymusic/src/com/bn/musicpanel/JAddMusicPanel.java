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
	 * ����/��Ӹ���
	 */
	private static final long serialVersionUID = 1L;
	
	//���֣�ר���б�����
	public static String[] singerlist;
	public static String[] albumlist;
	
	JTextField j_filename=new JTextField();
	
	public static JComboBox j_singerC=new JComboBox();       	//���������б�
	public static JComboBox j_albumC=new JComboBox();          	//ר�������б�
	JButton jadd=new JButton("ȷ��");
	JButton jOpen;           									// ���ļ�
	JFileChooser jchooser = new JFileChooser();
	
	public JAddMusicPanel()
	{
		String singer=NetInfoUtil.getSingerNameForList();		//��ȡ��������
		String[] singerlist=singer.split("<#>\\|");
		j_singerC=new JComboBox(singerlist);
		
		String album=NetInfoUtil.getAlbumsNameForList();		//��ȡ��������
		String[] albumlist=album.split("<#>\\|");
		
		
		this.setLayout(null);		
		
		JLabel j_title=new JLabel("��Ӹ���");
		j_title.setFont(new Font("����",Font.BOLD,25));
		this.add(j_title);
		j_title.setBounds(370,50,150,50);

		JLabel j_singer=new JLabel("��������");
		j_singer.setFont(new Font("����",Font.BOLD,20));
		this.add(j_singer);
		j_singer.setBounds(220,120,120,30);
		
		j_albumC=new JComboBox(albumlist);
		this.add(j_singerC);
		j_singerC.setBounds(320,120,300,30);
		
		JLabel j_singer_need=new JLabel("*");
		j_singer_need.setFont(new Font("����",Font.BOLD,20));
		j_singer_need.setForeground(Color.red);
		this.add(j_singer_need);
		j_singer_need.setBounds(620,120,50,30);

		JLabel j_music=new JLabel("ר����:");
		j_music.setFont(new Font("����",Font.BOLD,20));
		this.add(j_music);
		j_music.setBounds(220,200,120,30);
		
		
		this.add(j_albumC);
		j_albumC.setBounds(320,200,300,30);
		
		JLabel j_music_need=new JLabel("*");
		j_music_need.setFont(new Font("����",Font.BOLD,20));
		j_music_need.setForeground(Color.red);
		this.add(j_music_need);
		j_music_need.setBounds(620,200,50,30);		

		JLabel j_music_path=new JLabel("��������");
		j_music_path.setFont(new Font("����",Font.BOLD,20));
		this.add(j_music_path);
		j_music_path.setBounds(220,280,120,30);
		
		
		this.add(j_filename);
		j_filename.setBounds(320,280,260,30);
		
		jOpen=new JButton("���ļ���");						//���ļ���ť
		this.add(jOpen);
		jOpen.setBounds(580,280,100,30);
		jOpen.addActionListener(this);
		
		JLabel ta=new JLabel("ע�⣺����*��Ϊ����ѡ��");
		ta.setFont(new Font("����",Font.BOLD,18));
		ta.setForeground(Color.blue);
		this.add(ta);
		ta.setBounds(300,330,400,30);

		this.add(jadd);										//ȷ��
		jadd.setFont(new Font("����",Font.BOLD,25));	
		jadd.setBounds(370,400,100,50);
		jadd.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==jOpen){						//������ļ���ť
			//ֻ��ʾ��׺��MP3��ʽ���ļ�	
			FileNameExtensionFilter filter = new FileNameExtensionFilter( "MP3 Music", "mp3");		
			jchooser.setFileFilter(filter);
			int returnVal = jchooser.showOpenDialog(this);				//�ж��Ƿ�ѡ�����ļ�
			if(returnVal == JFileChooser.APPROVE_OPTION){				//���ѡ�����ļ�
				//�����������ļ���
				j_filename.setText(jchooser.getSelectedFile().getName());    
		}}else if(e.getSource()==jadd){									//���ȷ����ť
			if(j_filename.getText().equals("")){						//�ж�������Ƿ�Ϊ��
				JOptionPane.showMessageDialog(null, "��ѡ��Ҫ��ӵĸ���");
			}else{
				String []song=j_filename.getText().split("-|\\.");		//���ļ����н�ȡ���������ַ���������
				String all=j_singerC.getSelectedItem()+"<#>";			//����һ���ַ��������ռ���ӵĸ�����Ϣ
				all+=j_albumC.getSelectedItem()+"<#>";					//��ר������ӵ��ַ�����
				all+=song[1].trim()+"<#>";								//����������ӵ��ַ�����
				all+=j_filename.getText();								//���ļ�����ӵ��ַ�����
				//��ȡ�����ļ�·���������ļ���
				File file=new File(jchooser.getCurrentDirectory()+"\\"+jchooser.getSelectedFile().getName());
				boolean bb=NetInfoUtil.addSong(file,all);					//�ϴ������������������������ݿ�
				if(bb){														//����ϴ��ɹ�
					JOptionPane.showMessageDialog(null, "���ݿ��Ѿ��ɹ����գ�");	
					JAddLyricPanel.jlookfilenameC.addItem(j_filename.getText());
				}else{
					JOptionPane.showMessageDialog(null, "���ݿ�û�н�����Ϣ��");
				}
				
			}
		}			
	}
}