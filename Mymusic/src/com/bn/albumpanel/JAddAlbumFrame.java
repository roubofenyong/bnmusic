package com.bn.albumpanel;

import javax.swing.JFrame;

public class JAddAlbumFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JAddAlbumPanel jalbum;
	public JAddAlbumFrame()
	{
		jalbum=new JAddAlbumPanel();
		this.setTitle("Ìí¼Ó×¨¼­");		
		this.add(jalbum);
		this.setBounds(250,100,850,500);
		this.setVisible(true);	
	}
}
