package com.bn.render;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.bn.musicpanel.JLookMusicEditFrame;

public class MyLookMusicCellEditor implements TableCellEditor,ActionListener
{
	JButton jLookMusiceditor=new JButton("±à¼­");
	String edit="edit";
	
	public MyLookMusicCellEditor()
	{
		jLookMusiceditor.addActionListener(this);		//°´Å¥Ìí¼Ó¼àÌý
		jLookMusiceditor.setActionCommand(edit);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		new JLookMusicEditFrame();						//´ò¿ª¸èÇú±à¼­½çÃæ
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{
		return jLookMusiceditor;
	}
	@SuppressWarnings("deprecation")
	@Override
	public Object getCellEditorValue() 
	{
		return jLookMusiceditor.getLabel();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent)
	{
		return true;
	}

	@Override
	public boolean stopCellEditing() 
	{
		return true;
	}

	@Override
	public void cancelCellEditing() 
	{
	}

	@Override
	public void addCellEditorListener(CellEditorListener l)
	{
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l)
	{
	}
}
