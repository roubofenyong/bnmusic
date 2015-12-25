package com.bn.frame;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.bn.loginpanel.JLoginPanel;

public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
    JLoginPanel jLoginPanel = new JLoginPanel(); 
	static LoginFrame login;
	JLabel jLoginPicL = new JLabel();
	String lookAndFeel;
	ImageIcon imgBackground;
	public LoginFrame()
	{
		imgBackground = new ImageIcon("resource/pic/bg.jpg");
		this.setTitle("µÇÂ¼½çÃæ");
		this.add(jLoginPicL,-1);
		jLoginPicL.setIcon(imgBackground);
		jLoginPicL.setBounds(0,0,500,300);
		this.add(jLoginPanel,0);
		jLoginPanel.setOpaque(false);
		imgBackground.setImage(imgBackground.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
	    this.setBounds(400,170,500,300);
	    this.setVisible(true);
	    try
	    {
	    	lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	    	UIManager.setLookAndFeel(lookAndFeel);
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    this.setIconImage(imgBackground.getImage());
	    
	}
	
	public static void main(String[] args) {
        login = new LoginFrame();
	}
}
