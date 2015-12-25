package com.bn.musicpanel;

import javax.swing.JFrame;

public class JAddLyricFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JAddLyricPanel jt;
	
	public JAddLyricFrame()
	{
		jt=new JAddLyricPanel();
		
		this.setTitle("Ìí¼Ó¸è´Ê");		
		
		this.add(jt);
		this.setBounds(250,100,850,500);
		this.setVisible(true);	
	}
}
