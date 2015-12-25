package com.bn.singerpanel;

import javax.swing.JFrame;

public class JAddSingerFrame extends JFrame {
   JAddSingerPanel jt;
   public JAddSingerFrame()
   {
	   jt = new JAddSingerPanel();
	   this.setTitle("ÃÌº”∏Ë ÷");
	   this.add(jt);
	   this.setBounds(250,100,820,550);
	   this.setVisible(true);
   }
}
