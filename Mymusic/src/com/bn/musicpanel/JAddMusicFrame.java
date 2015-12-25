package com.bn.musicpanel;

import javax.swing.JFrame;

public class JAddMusicFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JAddMusicPanel jt;
	public JAddMusicFrame()
	{
		jt=new JAddMusicPanel();
		
		this.setTitle("添加歌手");	
		
		//添加JPanel到Jframe
		this.add(jt);
		this.setBounds(250,100,850,500);
		this.setVisible(true);	
	}
}
