package com.bn.render;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyLookAlbumCellRenderer 
implements TableCellRenderer
{
	static JButton jlookalbumrender;
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{		
		jlookalbumrender=new JButton("±à¼­");				//´´½¨±à¼­°´Å¥
		return jlookalbumrender;
	}
}
