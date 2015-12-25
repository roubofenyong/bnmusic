package com.bn.render;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyLookMusicCellRendererDel 
implements TableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{
		JButton jlookmusicrender_del=new JButton("É¾³ý");			//·µ»ØÉ¾³ý°´Å¥
		return jlookmusicrender_del;
	}
}
