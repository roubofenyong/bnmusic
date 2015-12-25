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
	 *  ��������
	 */
	private static final long serialVersionUID = 1L;	
	
	//�������ģ��
	public static DefaultTableModel dtm_Music;
	public static JTable jt_Music;
	public JScrollPane jsp_Music;
	
	//�ռ���Ҫ�鿴������
	public static String song;
	
	JLabel jtitleL;
	JLabel jsingerL;
	JLabel jalbumL;
	JLabel jlyricL;
	
	//���������б�
	public static JComboBox j_singerC=new JComboBox();
	public static JComboBox j_albumC=new JComboBox();
	public static JComboBox j_lyricC=new JComboBox();
	
	JPanel jtoolbarsong=new JPanel();
	JButton jaddmusic;
	JButton jaddlyric;
	JButton jsearch;
	public JLookMusicPanel()
	{
		//������������б������
		String[] lyriclist={"����","��","��"};
		
		//��ȡ���������б������
		String singer=NetInfoUtil.getSingerNameForList();		
		String[] singerlist=singer.split("<#>\\|");
		//��ȡר�������б������
		String album=NetInfoUtil.getAlbumsNameForList();		//��ȡ����
		String[] albumlist=album.split("<#>\\|");
		
		dtm_Music=new DefaultTableModel();
		jt_Music=new JTable(dtm_Music);							//��ʾ�����ı��	
		jsp_Music=new JScrollPane(jt_Music,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );		
		
		this.setLayout(null);
		
		jtitleL=new JLabel("�����鿴");
		jtitleL.setFont(new Font("����",Font.CENTER_BASELINE,15));
		jtitleL.setBounds(5,7,70,30);
		
		jsingerL=new JLabel("������:");
		jsingerL.setFont(new Font("����",Font.BOLD,12));
		jsingerL.setBounds(80,5,50,30);
		
		j_singerC=new JComboBox(singerlist);
		j_singerC.insertItemAt("����", 0);
		j_singerC.setSelectedIndex(0);
		j_singerC.setBounds(135,11,100, 20);
		
		jalbumL=new JLabel("ר����:");
		jalbumL.setFont(new Font("����",Font.BOLD,12));
		jalbumL.setBounds(240,5,50, 30);
		
		j_albumC=new JComboBox(albumlist);
		j_albumC.insertItemAt("����", 0);
		j_albumC.setSelectedIndex(0);
		j_albumC.setBounds(295,11,120, 20);
		
		jlyricL=new JLabel("���:");
		jlyricL.setFont(new Font("����",Font.BOLD,12));
		jlyricL.setBounds(420,5,50,30);
		
		j_lyricC=new JComboBox(lyriclist);
		j_lyricC.setBounds(460,11,60, 20);
		
		
		jsearch=new JButton("����");
		jsearch.setBounds(530,7,70,30);
		jsearch.setFont(new Font("����",Font.CENTER_BASELINE,13));
		jsearch.addActionListener(this);
			
		
		jaddmusic=new JButton("��Ӹ���");		
		jaddmusic.setBounds(610,7,90,30);
		jaddmusic.setFont(new Font("����",Font.CENTER_BASELINE,13));
		jaddmusic.addActionListener(this);
		
		jaddlyric=new JButton("��Ӹ��");
		jaddlyric.setBounds(710,7,90,30);
		jaddlyric.setFont(new Font("����",Font.CENTER_BASELINE,13));
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
		
		this.add(jtoolbarsong);									//��ӹ�����
		this.add(jsp_Music);									//��ӱ��
		
		jt_Music.setRowHeight(30);								//�����и�		
		jt_Music.setAutoResizeMode(0);							//���ÿ������ɸı��п�
		jsp_Music.setBounds(20,50,780,560);						//���ñ���С	
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jaddmusic)
		{
			new JAddMusicFrame();								//����Ӹ���JFrame
		}else if(e.getSource()==jaddlyric)
		{
			new JAddLyricFrame();								//����Ӹ��JFrame
		}else if(e.getSource()==jsearch)
		{
			//�ж����������л��Ǿֲ�����
			if(j_singerC.getSelectedItem().equals("����")&&j_albumC.getSelectedItem().equals("����")&&j_lyricC.getSelectedItem().equals("����"))
			{
				JMakeMusicTable.s=0;						
				new JMakeMusicTable();							//�������ֱ����Ϣ
			}else 	
			{
				song=j_singerC.getSelectedItem()+"<#>";			//�ռ���Ӳ鿴����Ϣ
				song+=j_albumC.getSelectedItem()+"<#>";
				song+=j_lyricC.getSelectedItem();
				
				JMakeMusicTable.s=1;
				new JMakeMusicTable();							//���������б����Ϣ
			}
		}
	}	
}
