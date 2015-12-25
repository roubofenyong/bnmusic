package com.bn.picpanel;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.bn.albumpanel.JLookAlbumPanel;
import com.bn.albumpanel.MyTableModel;
import com.bn.util.NetInfoUtil;

public class JLookPicPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel jLookPicLabel;
	JScrollPane jLookPicLabelP;
	JLabel jpointL;
	public JLookPicPanel()
	{
		this.setLayout(null);
		jLookPicLabel=new JLabel();
		jLookPicLabelP=new JScrollPane(jLookPicLabel);
		
		this.add(jLookPicLabelP,BorderLayout.CENTER);
		jLookPicLabelP.setBounds(0, 0, 800, 600);
		byte[] bb;
		
		String album=(String)MyTableModel.data[JLookAlbumPanel.rows][1];
		bb=NetInfoUtil.getManagePicture(album);
		if(bb!=null)
		{
			ImageIcon ii=new ImageIcon(bb);
			ii.setImage(ii.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT));
			jLookPicLabel.setIcon(ii);
		}
	}
}
