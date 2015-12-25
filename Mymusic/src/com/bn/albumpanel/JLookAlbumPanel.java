package com.bn.albumpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.bn.picpanel.JAddPicFrame;
import com.bn.picpanel.JLookPicFrame;

public class JLookAlbumPanel extends JPanel 
implements ActionListener, MouseListener
{
	/**
	 * ר��/�鿴ר��
	 */
	private static final long serialVersionUID = 1L;
	
	public static MyTableModel dtm_Album;
	public static JTable jt_Album;
	public static JScrollPane jsp_Album;
	
	int col;
	public static int rows;										//��ȡѡ�����
	
	JPanel jtoolalbum=new JPanel();						
	
	JButton jaddalbum;
	JButton jaddpic;	
	
	public JLookAlbumPanel()
	{
		
		this.setLayout(null);
		
		jaddalbum=new JButton("���ר��");
		jaddalbum.setFont(new Font("����",Font.BOLD,15));
		jaddalbum.setBounds(10,7,100, 30);
		jaddalbum.addActionListener(this);
		
		jaddpic=new JButton("���ͼƬ");
		jaddpic.setFont(new Font("����",Font.BOLD,15));
		jaddpic.setBounds(150,7,100, 30);
		jaddpic.addActionListener(this);
		
		jtoolalbum.setLayout(null);
		
		jtoolalbum.add(jaddalbum);
		jtoolalbum.add(jaddpic);
		this.add(jtoolalbum);
		jtoolalbum.setBounds(0, 0,800, 50);
		
		
		dtm_Album=new MyTableModel();
		jt_Album=new JTable(dtm_Album);
		jsp_Album=new JScrollPane(jt_Album);		
		
		jt_Album.setRowHeight(30);
		jt_Album.addMouseListener(this);
		
		this.add(jsp_Album);		
		jsp_Album.setBounds(20,50,780,560);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jaddalbum)
		{
			new JAddAlbumFrame();										//�����ר���Ľ���
		}else if(e.getSource()==jaddpic)
		{
			rows=JLookAlbumPanel.jt_Album.getSelectedRow();
			if(rows==-1)
			{
				JOptionPane.showMessageDialog(null, "��ѡ����Ҫ��ӵ�ͼƬ��ר��");//���û��ѡ��ĳ�У�����ʾ
			}else
			{
				new JAddPicFrame();										//����Ӹ�ʵĽ���
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) 								//������
	{
		col=jt_Album.getSelectedColumn();								//��ȡѡ�����
		if(col==3)
		{
			rows=jt_Album.getSelectedRow();								//��ȡѡ�����
			new JLookPicFrame();										//��ͼƬ����
		}	
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
