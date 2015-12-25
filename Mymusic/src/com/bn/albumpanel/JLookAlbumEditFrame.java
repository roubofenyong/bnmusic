package com.bn.albumpanel;

import javax.swing.JFrame;

public class JLookAlbumEditFrame extends JFrame
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JMakeAlbumEditPanel jt;
	public JLookAlbumEditFrame()
	{
		jt=new JMakeAlbumEditPanel();
		
		this.setTitle("ÐÞ¸Ä¸èÇúÐÅÏ¢");
		this.add(jt);
		this.setBounds(320,120,750,460);
		this.setVisible(true);	
	}
}
