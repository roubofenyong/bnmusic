package com.bn.render;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyLookMusicCellRenderer
implements TableCellRenderer
{
	static JButton jLookMusicCellRenderer;   
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) 
	{
		jLookMusicCellRenderer=new JButton("±à¼­");    
		return jLookMusicCellRenderer;
	}
}
