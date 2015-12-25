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
	 * ���¶�����ģ��
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageIcon src;									//����һ��ͼƬ
	public static Object[][] data;							//����һ����ά����		
	String head[]={"���","ר����","����","ͼƬ",""};				//�����б����ַ�������
	//������ʾ���������͵���������
	@SuppressWarnings("rawtypes")
	Class[] typeArray={String.class,String.class,String.class,Icon.class,Object.class};
	public MyTableModel(){		
		List<String[]> ls=new ArrayList<String[]>();		//��ȡר���б���Ϣ
		ls=NetInfoUtil.getAlbumsList();
		data=new Object[ls.size()][ls.get(0).length+2];		//����һ����С������
		for(int i=0;i<ls.size();i++){
			for(int j=0;j<ls.get(i).length-1;j++){
				data[i][j+1]=ls.get(i)[j];					//�������е���ר����Ϣ
		}}
		for(int i=0;i<ls.size();i++){
			data[i][0]=i+1+"";
			if(!ls.get(i)[2].equals("null")){
				src=new ImageIcon(ls.get(i)[2]);			//��ȡͼƬ,�ı�ͼƬ�Ĵ�С
				src.setImage(src.getImage().getScaledInstance(40, 30,Image.SCALE_DEFAULT));
				data[i][3]=src;								//��ͼƬ��ӵ�������
	}}}
	//��дgetRowCount()����
	@Override
	public int getRowCount() 
	{
		return data.length;
	}
	//��дgetRowCount()����
	@Override
	public int getColumnCount() {
		return head.length;
	}
	//��дgetValueAt()����
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	//��дgetColumnName()����
	@SuppressWarnings({ })
	@Override
	public String getColumnName(int col)
	{
		return head[col];
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//��дgetColumnClass()����
	@Override
	public Class getColumnClass(int c)
	{
		return typeArray[c];
	}
	//��дisCellEditable()����
	@Override
	public boolean isCellEditable(int r,int c)
	{
		return true;
	}
	//��дsetValueAt()����
	@Override
	public void setValueAt(Object Value,int r,int c)
	{
		data[r][c]=Value;
		this.fireTableCellUpdated(r, c);
	}
}
