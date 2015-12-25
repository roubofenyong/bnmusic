package com.bn.albumpanel;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import com.bn.util.NetInfoUtil;

public class MyTableModel extends AbstractTableModel
{
	/**
	 * 重新定义表格模型
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageIcon src;									//定义一个图片
	public static Object[][] data;							//定义一个二维数组		
	String head[]={"序号","专辑名","歌手","图片",""};				//创建列表题字符串数组
	//创建表示各个列类型的类型数组
	@SuppressWarnings("rawtypes")
	Class[] typeArray={String.class,String.class,String.class,Icon.class,Object.class};
	public MyTableModel(){		
		List<String[]> ls=new ArrayList<String[]>();		//获取专辑列表信息
		ls=NetInfoUtil.getAlbumsList();
		data=new Object[ls.size()][ls.get(0).length+2];		//创建一定大小的数组
		for(int i=0;i<ls.size();i++){
			for(int j=0;j<ls.get(i).length-1;j++){
				data[i][j+1]=ls.get(i)[j];					//向数组中导入专辑信息
		}}
		for(int i=0;i<ls.size();i++){
			data[i][0]=i+1+"";
			if(!ls.get(i)[2].equals("null")){
				src=new ImageIcon(ls.get(i)[2]);			//获取图片,改变图片的大小
				src.setImage(src.getImage().getScaledInstance(40, 30,Image.SCALE_DEFAULT));
				data[i][3]=src;								//把图片添加到数组中
	}}}
	//重写getRowCount()方法
	@Override
	public int getRowCount() 
	{
		return data.length;
	}
	//重写getRowCount()方法
	@Override
	public int getColumnCount() {
		return head.length;
	}
	//重写getValueAt()方法
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	//重写getColumnName()方法
	@SuppressWarnings({ })
	@Override
	public String getColumnName(int col)
	{
		return head[col];
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//重写getColumnClass()方法
	@Override
	public Class getColumnClass(int c)
	{
		return typeArray[c];
	}
	//重写isCellEditable()方法
	@Override
	public boolean isCellEditable(int r,int c)
	{
		return true;
	}
	//重写setValueAt()方法
	@Override
	public void setValueAt(Object Value,int r,int c)
	{
		data[r][c]=Value;
		this.fireTableCellUpdated(r, c);
	}
}
