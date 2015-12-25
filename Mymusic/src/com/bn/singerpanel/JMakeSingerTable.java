package com.bn.singerpanel;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.bn.render.MyLookSingerCellEditor;
import com.bn.render.MyLookSingerCellRenderer;
import com.bn.util.NetInfoUtil;

public class JMakeSingerTable {
	 public static String[][] content;
     public static int s;
     public JMakeSingerTable()
     {
    	 String[] title = {"序号","姓名","性别","籍贯","类别"};
    	 List<String[]> ls = new ArrayList<String[]>();
    	 if(s == 0)
    	 {
    		 ls = NetInfoUtil.getSingerList();
    	 }
    	 else if(s == 1)
    	 {
    		 ls = NetInfoUtil.conditionalSearch(JLookSingerPanel.search);
    	 }
    	 content = new String[ls.size()][ls.get(0).length];
    	 for(int i = 0 ; i < ls.size() ; i++)
    	 {
    		 for(int j = 0 ; j < ls.get(i).length ; j++)
    		 {
    			 content[i][j] = ls.get(i)[j];
    		 }
    	 }
    	 JLookSingerPanel.dtm_Singer.setDataVector(content, title);
    	 JLookSingerPanel.jt_Singer.getTableHeader().setFont(new Font("宋体",Font.BOLD,15));
    	 int size = JLookSingerPanel.jt_Singer.getColumnCount();
    	 JLookSingerPanel.jt_Singer.getColumnModel().getColumn(size-1).setCellEditor(new MyLookSingerCellEditor());
    	 JLookSingerPanel.jt_Singer.getColumnModel().getColumn(size-1).setCellRenderer(new MyLookSingerCellRenderer());
         for(int i = 0;i < size ; i++)
         {
        	 TableColumn setColSize = JLookSingerPanel.jt_Singer.getColumnModel().getColumn(i);
        	 if(i == 0)
        	 {
        		 setColSize.setPreferredWidth(80);
        	 }
        	 else if(i == 1)
        	 {
        		 setColSize.setPreferredWidth(250);
        	 }
        	 else if(i == 2)
        	 {
        		 setColSize.setPreferredWidth(50);
        	 }
        	 else if(i == 3)
        	 {
        		 setColSize.setPreferredWidth(130);
        	 }
        	 else if(i == 4)
        	 {
        		 setColSize.setPreferredWidth(100);
        	 }
        	 else if(i == 5)
        	 {
        		 setColSize.setPreferredWidth(150);
        	 }
         }
     }
}
