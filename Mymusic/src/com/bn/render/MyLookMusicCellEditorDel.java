package com.bn.render;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.bn.musicpanel.JLookMusicPanel;
import com.bn.musicpanel.JMakeMusicTable;
import com.bn.util.NetInfoUtil;

public class MyLookMusicCellEditorDel 
implements TableCellEditor,ActionListener
{

	JButton jlookmusiceditor_del=new JButton("ɾ��");
	String del="del";
	int rows;
	public MyLookMusicCellEditorDel()
	{
		jlookmusiceditor_del.addActionListener(this);				//��������Ӽ���
		jlookmusiceditor_del.setActionCommand(del);
	}
	@SuppressWarnings("deprecation")
	@Override
	public Object getCellEditorValue() 
	{
		return jlookmusiceditor_del.getLabel();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		rows=JLookMusicPanel.jt_Music.getSelectedRow();				//��ȡѡ�����
		JLookMusicPanel.dtm_Music.removeRow(rows);					//�ڱ����ɾ��ѡ�����
		
		String album=JMakeMusicTable.content[rows][1]+"<#>";		//��ȡҪɾ����ר����Ϣ
		album+=JMakeMusicTable.content[rows][2];		
		
		NetInfoUtil.deleteSong(album);								//���ͷ�������ɾ�����е�����
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) 
	{
		return jlookmusiceditor_del;
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
