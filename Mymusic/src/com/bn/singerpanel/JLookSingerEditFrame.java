package com.bn.singerpanel;

import javax.swing.JFrame;

public class JLookSingerEditFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JMakeSingerEditPanel je;
	
	public JLookSingerEditFrame()
	{
		//�����༭���ֵ�JPanel
		je=new JMakeSingerEditPanel();
		this.setTitle("�޸ĸ�����Ϣ");
		
		//���JPanel�ڴ�����
		this.add(je);
		this.setBounds(320,120,750,460);
		this.setVisible(true);			
	}
}