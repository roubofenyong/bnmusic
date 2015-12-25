package com.bn.albumpanel;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.bn.render.MyLookAlbumCellEditor;
import com.bn.render.MyLookAlbumCellRenderer;

public class JMakeAlbumTable
{
	int size;
	public JMakeAlbumTable()
	{
		
		//��ȡ������
		int size=JLookAlbumPanel.jt_Album.getColumnCount();								
		
		//��ӱ༭�б�
		JLookAlbumPanel.jt_Album.getColumnModel().getColumn(size-1).setCellRenderer(new MyLookAlbumCellRenderer());
		JLookAlbumPanel.jt_Album.getColumnModel().getColumn(size-1).setCellEditor(new MyLookAlbumCellEditor());
		
		JLookAlbumPanel.jt_Album.getTableHeader().setFont(new Font("����",Font.BOLD,15));
		
		//�ı��п�
		for(int i=0;i<size;i++)
		{
			TableColumn setColsize=JLookAlbumPanel.jt_Album.getColumnModel().getColumn(i);
			if(i==0)
			{
				setColsize.setPreferredWidth(60);
			}else if(i==1)
			{
				setColsize.setPreferredWidth(410);
			}else if(i==2)
			{
				setColsize.setPreferredWidth(120);
			}else if(i==3)
			{
				setColsize.setPreferredWidth(40);
			}else if(i==4)
			{
				setColsize.setPreferredWidth(150);
			}
		}
	}
}
