package com.bn.musicpanel;


import javax.swing.JFrame;

import com.bn.musicpanel.JMakeMusicEditPanel;

public class JLookMusicEditFrame extends JFrame
{
	private static final long serialVersionUID = 1L;	
	JMakeMusicEditPanel jt;
	public JLookMusicEditFrame()
	{
		jt=new JMakeMusicEditPanel();
		this.setTitle("�޸ĸ�����Ϣ");
		
		//���Jpanel��JFrame��
		this.add(jt);
		
		this.setBounds(320,120,750,460);
		this.setVisible(true);			
	}	
}
