package com.bn.frame;

import java.awt.CardLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.bn.albumpanel.JLookAlbumPanel;
import com.bn.musicpanel.JLookMusicPanel;
import com.bn.singerpanel.JLookSingerPanel;

public class PrimaryFrame extends JFrame{
	private static final long serialVersionUID = 1L;
     PrimaryTree menu = new PrimaryTree();
     static JPanel jall = new JPanel();
     static CardLayout cl = new CardLayout();
     JScrollPane jspx = new JScrollPane(menu.jt);
     JScrollPane jspy = new JScrollPane(jall);
     JSplitPane js;
     JPanel jempty = new JPanel();
     JLookSingerPanel jsingermanager = new JLookSingerPanel();
     JLookMusicPanel jmusicmanage = new JLookMusicPanel();
     JLookAlbumPanel jalbummanager = new JLookAlbumPanel();
     ImageIcon imageTitle;
     public PrimaryFrame()
     {
    	 File file = new File("resource");
    	 String picPath = file.getAbsolutePath() + "\\pic";
    	 imageTitle = new ImageIcon(picPath+"\\imagetitle.jpg");
    	 this.setIconImage(imageTitle.getImage());
    	 this.setTitle("音乐播放器后台管理");
    	 jall.setLayout(cl);
    	 jall.add(jempty,"jempty");
    	 jall.add(jmusicmanage,"jmusicmanage");
    	 jall.add(jsingermanager,"jsingermanage");
    	 jall.add(jalbummanager,"jalbummanage");
    	 js = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspx,jspy);
    	 this.add(js);
    	 js.setBounds(0,0,1000,700);
    	 js.setDividerLocation(150);
    	 js.setDividerSize(4);
    	 this.setBounds(150,20,1000,700);
    	 this.setVisible(true);
    	 LoginFrame.login.dispose(); 
     }
     public static void main(String[] args) {
		new PrimaryFrame();
	}
     
     
}
