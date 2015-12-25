package com.bn.musicpanel;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.bn.render.MyLookMusicCellEditor;
import com.bn.render.MyLookMusicCellRenderer;
import com.bn.render.MyLookMusicCellRendererDel;
import com.bn.render.MyLookMusicCellEditorDel;
import com.bn.util.Constant;
import com.bn.util.NetInfoUtil;

public class JMakeMusicTable{
	
	public static String[][] content;									//��������
	public static int s;
	int size;
	
	public JMakeMusicTable()
	{
		String title[]={"���","����","����","ר����","���","",""}; 			//������
		
		List<String[]> ls=new ArrayList<String[]>();
		
		if(s==0)
		{
			ls=NetInfoUtil.getSongList();								//��ȡ��������Ϣ
		}else if(s==1)
		{
			ls=NetInfoUtil.conditionalSearchSong(JLookMusicPanel.song);
			System.out.println("s==1");
		}
					
		
		content=new String[ls.size()][ls.get(0).length+1];				//���ݸ������ݴ�С���������顣
		ArrayList<Integer> t=new ArrayList<Integer>();
		t.size();
		for(int i=0;i<ls.size();i++)									//�������ݵ�������
		{
			for(int j=0;j<ls.get(i).length;j++)
			{
				content[i][j+1]=ls.get(i)[j];
			}
		}
		for(int i=0;i<ls.size();i++)									//���������
		{
			content[i][0]=i+1+"";
		}
		
		JLookMusicPanel.dtm_Music.setDataVector(content, title);		//�ѱ������飬��������ӵ������
		
		JLookMusicPanel.jt_Music.getTableHeader().setFont(new Font("����",Font.BOLD,15));
		
		size=JLookMusicPanel.jt_Music.getColumnCount();					//��ȡ����ܵ�����
		//��ӱ༭��ť
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-2).setCellRenderer(new MyLookMusicCellRenderer());		
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-2).setCellEditor(new MyLookMusicCellEditor());
		//���ɾ����ť
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-1).setCellRenderer(new MyLookMusicCellRendererDel());
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-1).setCellEditor(new MyLookMusicCellEditorDel());
		
		
		for(int i=0;i<size;i++)											//����ÿһ�еĿ��
		{
			//��ȡ��i�е�����
			TableColumn setColSize=JLookMusicPanel.jt_Music.getColumnModel().getColumn(i);
			
			if(i==0)
			{
				setColSize.setPreferredWidth(67);
			}
			else if(i==1)
			{
				setColSize.setPreferredWidth(150);
			}else if(i==2)
			{
				setColSize.setPreferredWidth(100);
			}else if(i==3)
			{
				setColSize.setPreferredWidth(180);
			}else if(i==4)
			{
				setColSize.setPreferredWidth(60);
			}else if(i==5)
			{
				setColSize.setPreferredWidth(110);
			}else if(i==6)
			{
				setColSize.setPreferredWidth(110);
			}
		}
	}
}
