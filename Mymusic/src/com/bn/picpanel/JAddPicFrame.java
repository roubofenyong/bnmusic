package com.bn.picpanel;

import javax.swing.JFrame;

import com.bn.picpanel.JAddPicPanel;

public class JAddPicFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JAddPicPanel jpic;
	public JAddPicFrame()
	{
		jpic=new JAddPicPanel();
		
		this.setTitle("ÃÌº”Õº∆¨");		
		this.add(jpic);
		this.setBounds(250,100,850,500);
		this.setVisible(true);	
	}
}
