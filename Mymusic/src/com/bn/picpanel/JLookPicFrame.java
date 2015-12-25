package com.bn.picpanel;

import javax.swing.JFrame;
import com.bn.albumpanel.JLookAlbumPanel;
import com.bn.albumpanel.MyTableModel;

public class JLookPicFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLookPicPanel jlookpic;
	public JLookPicFrame()
	{
		jlookpic=new JLookPicPanel();
		String albumname=(String)MyTableModel.data[JLookAlbumPanel.rows][1];		
		this.setTitle(albumname);		
		this.add(jlookpic);
		this.setBounds(310, 80, 800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);	
	}
}
