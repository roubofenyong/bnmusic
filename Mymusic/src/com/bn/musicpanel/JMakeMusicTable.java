package com.bn.musicpanel;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.bn.render.MyLookMusicCellEditor;
import com.bn.render.MyLookMusicCellRenderer;
import com.bn.render.MyLookMusicCellRendererDel;
import com.bn.render.MyLookMusicCellEditorDel;
import com.bn.util.Constant;
import com.bn.util.NetInfoUtil;

public class JMakeMusicTable{
	
	public static String[][] content;									//歌曲内容
	public static int s;
	int size;
	
	public JMakeMusicTable()
	{
		String title[]={"序号","歌名","歌手","专辑名","歌词","",""}; 			//表格标题
		
		List<String[]> ls=new ArrayList<String[]>();
		
		if(s==0)
		{
			ls=NetInfoUtil.getSongList();								//获取歌曲的信息
		}else if(s==1)
		{
			ls=NetInfoUtil.conditionalSearchSong(JLookMusicPanel.song);
			System.out.println("s==1");
		}
					
		
		content=new String[ls.size()][ls.get(0).length+1];				//根据歌曲内容大小，创建数组。
		ArrayList<Integer> t=new ArrayList<Integer>();
		t.size();
		for(int i=0;i<ls.size();i++)									//加载内容到数组中
		{
			for(int j=0;j<ls.get(i).length;j++)
			{
				content[i][j+1]=ls.get(i)[j];
			}
		}
		for(int i=0;i<ls.size();i++)									//歌曲的序号
		{
			content[i][0]=i+1+"";
		}
		
		JLookMusicPanel.dtm_Music.setDataVector(content, title);		//把标题数组，内容数组加到表格中
		
		JLookMusicPanel.jt_Music.getTableHeader().setFont(new Font("宋体",Font.BOLD,15));
		
		size=JLookMusicPanel.jt_Music.getColumnCount();					//获取表格总的列数
		//添加编辑按钮
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-2).setCellRenderer(new MyLookMusicCellRenderer());		
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-2).setCellEditor(new MyLookMusicCellEditor());
		//添加删除按钮
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-1).setCellRenderer(new MyLookMusicCellRendererDel());
		JLookMusicPanel.jt_Music.getColumnModel().getColumn(size-1).setCellEditor(new MyLookMusicCellEditorDel());
		
		
		for(int i=0;i<size;i++)											//设置每一列的宽度
		{
			//获取第i列的索引
			TableColumn setColSize=JLookMusicPanel.jt_Music.getColumnModel().getColumn(i);
			
			if(i==0)
			{
				setColSize.setPreferredWidth(67);
			}
			else if(i==1)
			{
				setColSize.setPreferredWidth(150);
			}else if(i==2)
			{
				setColSize.setPreferredWidth(100);
			}else if(i==3)
			{
				setColSize.setPreferredWidth(180);
			}else if(i==4)
			{
				setColSize.setPreferredWidth(60);
			}else if(i==5)
			{
				setColSize.setPreferredWidth(110);
			}else if(i==6)
			{
				setColSize.setPreferredWidth(110);
			}
		}
	}
}
