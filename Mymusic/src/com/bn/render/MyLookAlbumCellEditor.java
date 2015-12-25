package com.bn.render;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.bn.albumpanel.JLookAlbumEditFrame;

public class MyLookAlbumCellEditor 
implements TableCellEditor,ActionListener
{
	JButton jlookalbumeditor=new JButton("�༭");
	
	String edit="edit";
	public MyLookAlbumCellEditor()
	{
		jlookalbumeditor.addActionListener(this);				//��ť��Ӽ���
		jlookalbumeditor.setActionCommand(edit);
	}

	@Override
	public Object getCellEditorValue() {
		return  jlookalbumeditor;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new JLookAlbumEditFrame();								//�򿪱༭ר���Ĵ���
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return jlookalbumeditor;
	}
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		return true;
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
	}	
}
