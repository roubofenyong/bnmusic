package com.bn.loginpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bn.frame.PrimaryFrame;
import com.bn.util.NetInfoUtil;

public class JLoginPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
    JLabel jTitle = new JLabel("���ֲ�������̨����");
    JLabel jAdminL = new JLabel("�û���:");
    JLabel jPasswordL = new JLabel("����:");
    JLabel jWarningL = new JLabel();
    JTextField jAdminT = new JTextField();
    JPasswordField jPasswordT = new JPasswordField();
    JButton jLoginOk = new JButton("��¼");
    JButton jLoginRe = new JButton("����");
    public JLoginPanel() {
		// TODO Auto-generated constructor stub
	this.setLayout(null);
	this.add(jTitle);
	jTitle.setFont(new Font("����",Font.BOLD,25));
	jTitle.setBounds(120,20,300,50);
	this.add(jAdminL);
	jAdminL.setBounds(100,100,70,30);
	this.add(jAdminT);
	jAdminT.setBounds(170,100,200,30);
	this.add(jPasswordL);
	jPasswordL.setBounds(100,150,70,30);
	this.add(jPasswordT);
     jPasswordT.setBounds(170,150,200,30);
     this.add(jWarningL);
     jWarningL.setBounds(150,200,200,30);
     this.add(jLoginOk);
     jLoginOk.setBounds(150,220,70,30);
     jLoginOk.addActionListener(this);    
      this.add(jLoginRe);
      jLoginRe.setBounds(250,220,70,30);
      jLoginRe.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jLoginRe)
		{
			jAdminT.setText("");
			jPasswordT.setText("");
			jWarningL.setText("��ʾ ���������û���,����");
		}else if(e.getSource() == jLoginOk)
		{
			String user = jAdminT.getText()+"<#>";
			user += jPasswordT.getText();
			if(NetInfoUtil.isUser(user))
			{
				new PrimaryFrame();
				JOptionPane.showMessageDialog(null, "��½�ɹ�!");
				
			}
			else
			{
				jWarningL.setText("��ʾ:�û������������!");
			}
		}
	}

}
