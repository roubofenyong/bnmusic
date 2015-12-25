package com.bn.singerpanel;

import javax.swing.JFrame;

public class JLookSingerEditFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JMakeSingerEditPanel je;
	
	public JLookSingerEditFrame()
	{
		//创建编辑歌手的JPanel
		je=new JMakeSingerEditPanel();
		this.setTitle("修改歌手信息");
		
		//添加JPanel在窗口中
		this.add(je);
		this.setBounds(320,120,750,460);
		this.setVisible(true);			
	}
}