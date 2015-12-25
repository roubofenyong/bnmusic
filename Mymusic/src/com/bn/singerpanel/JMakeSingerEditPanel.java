package com.bn.singerpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.bn.singerpanel.JMakeSingerTable;
import com.bn.util.NetInfoUtil;

public class JMakeSingerEditPanel extends JPanel
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//���ѡ�����
	int rows=JLookSingerPanel.jt_Singer.getSelectedRow();		//��
	
	JLabel j_xuhao;
	JTextField j_singer;
	//���������б�
	JComboBox j_gender;
	JComboBox j_nation;
	JComboBox j_sort;
	
	JButton j_ok=new JButton("ȷ��");
	
	public JMakeSingerEditPanel()
	{
		//���������б������
		String[] sex={"��","Ů","N/A"};
		String[] nation={"����","����","����","����","����","����","����","����"};
		String[] sort={"����","ҡ��","��ʿ","����","����","˵��","����"};
		
		this.setLayout(null);
		
		JLabel title=new JLabel("�༭����");
		this.add(title);
		title.setFont(new Font("����",Font.BOLD,25));
		title.setBounds(320,0,120, 50);
		
		JLabel xuhao=new JLabel("���:");
		this.add(xuhao);
		xuhao.setFont(new Font("����",Font.BOLD,20));
		xuhao.setBounds(130,50,120, 30);
		
		j_xuhao=new JLabel(JMakeSingerTable.content[rows][0]);
		this.add(j_xuhao);
		j_xuhao.setBounds(250,50,300,30);
		
		
		JLabel singer=new JLabel("������:");
		this.add(singer);
		singer.setFont(new Font("����",Font.BOLD,20));
		singer.setBounds(130,100,120, 30);
		
		j_singer=new JTextField(JMakeSingerTable.content[rows][1]);		//����Ϊѡ���еĸ�����
		this.add(j_singer);
		j_singer.setBounds(250,100,300,30);
		
		
		JLabel gender=new JLabel("�Ա�:");
		this.add(gender);
		gender.setFont(new Font("����",Font.BOLD,20));
		gender.setBounds(130,150,120, 30);
		
		j_gender=new JComboBox(sex);
		j_gender.setSelectedItem(JMakeSingerTable.content[rows][2]);	//����Ϊѡ���е��Ա���
		this.add(j_gender);
		j_gender.setBounds(250,150,300,30);
		
		
		JLabel nationL=new JLabel("����:");
		this.add(nationL);
		nationL.setFont(new Font("����",Font.BOLD,20));
		nationL.setBounds(130,200,120, 30);
		
		j_nation=new JComboBox(nation);
		j_nation.setSelectedItem(JMakeSingerTable.content[rows][3]);	//����Ϊѡ���еļ���
		this.add(j_nation);
		j_nation.setBounds(250,200,300,30);
		
		
		JLabel sortL=new JLabel("���:");
		this.add(sortL);
		sortL.setFont(new Font("����",Font.BOLD,20));
		sortL.setBounds(130,250,120, 30);
		
		j_sort=new JComboBox(sort);
		this.add(j_sort);
		j_sort.setBounds(250,250,300,30);
		
		this.add(j_ok);
		j_ok.setBounds(320,300, 120,30);
		j_ok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==j_ok)
		{
			if(j_singer.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "�����������,δ֪������null");
			}else
			{
				//���Ҫ�޸ĵĸ�����Ϣ
				String singer;
				singer=j_xuhao.getText()+"<#>";
				singer+=j_singer.getText()+"<#>";
				singer+=j_gender.getSelectedItem()+"<#>";
				singer+=j_nation.getSelectedItem()+"<#>";
				singer+=j_sort.getSelectedItem();
				
				//��Ӹ�����Ϣ
				NetInfoUtil.modifySinger(singer);
				//��ʾ��ӳɹ�
				JOptionPane.showMessageDialog(null,"�޸ĳɹ�");				
			}
		}
	}
}