package com.bn.singerpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JLookSingerPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
    public static DefaultTableModel dtm_Singer;
    public static JTable jt_Singer;
    JPanel jtoolsinger = new JPanel();
    public JScrollPane jsp_Singer;
    public static String search;
    JLabel jtitleL;
    JLabel jsexL;
    JLabel jnationL;
    JLabel jsortL;
    public static JComboBox jsexC;
    public static JComboBox jnationC;
    public static JComboBox jsortC;
    JButton jaddsinger;
    JButton jsearch;
    public JLookSingerPanel()
    {
    	String[] sex = {"����","��","Ů","N/A"};
    	String[] nation = {"����","����","����","����","����","����","����","����","����"};
    	String[] sort = {"����","����","ҡ��","��ʿ","����","����","˵��","����"};
    	dtm_Singer = new DefaultTableModel();
    	jt_Singer = new JTable(dtm_Singer);
    	jsp_Singer = new JScrollPane(jt_Singer);
    	this.setLayout(null);
    	jtitleL = new JLabel("�����鿴");
    	jtitleL.setFont(new Font("����",Font.CENTER_BASELINE,15));
    	jtitleL.setBounds(5,7,80,30);
    	jsexL = new JLabel("�Ա�:");
    	jsexL.setFont(new Font("����",Font.BOLD,12));
    	jsexL.setBounds(90,5,50,30);
    	jsexC = new JComboBox(sex);
    	jsexC.setBounds(140,11,60,20);
    	jnationL = new JLabel("����");
    	jnationL.setFont(new Font("����",Font.BOLD,12));
    	jnationL.setBounds(210,5,50,30);
    	jnationC = new JComboBox(nation);
    	jnationC.setBounds(260,11,80,20);
    	jsortL = new JLabel("���:");
    	jsortL.setFont(new Font("����",Font.BOLD,12));
    	jsortL.setBounds(350,5,50,30);
    	jsortC = new JComboBox(sort);
    	jsortC.setBounds(400,11,60,20);
    	jsearch = new JButton("����");
    	jsearch.setBounds(500,7,70,30);
    	jsearch.setFont(new Font("����",Font.CENTER_BASELINE,13));
    	jsearch.addActionListener(this);
    	jaddsinger = new JButton("��Ӹ���");
    	jaddsinger.setBounds(600,7,100,30);
    	jaddsinger.setFont(new Font("����",Font.CENTER_BASELINE,13));
    	jaddsinger.addActionListener(this);
    	jtoolsinger.setLayout(null);
    	jtoolsinger.add(jaddsinger);
    	jtoolsinger.add(jtitleL);
    	jtoolsinger.add(jsexL);
    	jtoolsinger.add(jsexC);
    	jtoolsinger.add(jnationL);
		jtoolsinger.add(jnationC);
		jtoolsinger.add(jsortL);
		jtoolsinger.add(jsortC);
		jtoolsinger.add(jsearch);
		this.add(jtoolsinger);
		jtoolsinger.setBounds(0,0,800,50);
    	this.add(jsp_Singer);
    	jt_Singer.setRowHeight(30);
    	jt_Singer.setAutoResizeMode(0);
    	jsp_Singer.setBounds(20,50,780,560);
    	
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jaddsinger)
		{
			new JAddSingerFrame();
		}else if(e.getSource() == jsearch)
		{
			if(jsexC.getSelectedItem().equals("����") && jnationC.getSelectedItem().equals("����") && jsortC.getSelectedItem().equals("����"))
			{
				JMakeSingerTable.s = 0;
				new JMakeSingerTable();
			}
			else
			{
				search = jsexC.getSelectedItem()+"<#>";
				search += jnationC.getSelectedItem()+"<#>";
				search += jsortC.getSelectedItem();
				JMakeSingerTable.s = 1;
				new JMakeSingerTable();
			}
		}
		
	}
}
