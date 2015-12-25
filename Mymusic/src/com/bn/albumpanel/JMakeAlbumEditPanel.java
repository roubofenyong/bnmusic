package com.bn.albumpanel;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bn.albumpanel.JLookAlbumPanel;
import com.bn.util.NetInfoUtil;

public class JMakeAlbumEditPanel extends JPanel
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int rows=JLookAlbumPanel.jt_Album.getSelectedRow();			//��ȡѡ�е���
	
	JLabel j_album;
	JComboBox j_singername;
	JButton j_ok=new JButton("ȷ��");
	
	public JMakeAlbumEditPanel()
	{		
		String singer=NetInfoUtil.getSingerNameForList();		//��ȡ����
		String[] singerlist=singer.split("<#>\\|");
		
		this.setLayout(null);

		Label title=new Label("�༭ר��");
		this.add(title);
		title.setFont(new Font("����",Font.BOLD,25));
		title.setBounds(320,0,120, 50);
		
		Label xuhao=new Label("ר����:");
		this.add(xuhao);
		xuhao.setFont(new Font("����",Font.BOLD,20));
		xuhao.setBounds(130,100,120, 30);
		
		j_album=new JLabel((String) MyTableModel.data[rows][1]);
		this.add(j_album);
		j_album.setBounds(250,100,300,30);
		
		
		Label singern=new Label("������:");
		this.add(singern);
		singern.setFont(new Font("����",Font.BOLD,20));
		singern.setBounds(130,180,120, 30);
		
		j_singername=new JComboBox(singerlist);
		j_singername.setSelectedItem(MyTableModel.data[rows][2]);
		j_singername.setMaximumRowCount(10);
		this.add(j_singername);
		j_singername.setBounds(250,180,300,30);
		
		this.add(j_ok);
		j_ok.setBounds(320,280, 120,30);
		j_ok.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==j_ok)
		{
			String album;										//����һ���ַ����������ռ��޸ĺ��ר����Ϣ
			album=j_singername.getSelectedItem()+"<#>";
			album+=j_album.getText();
			
			NetInfoUtil.modifyAlbum(album);						//����ר��������ݿ�
			
			JOptionPane.showMessageDialog(null,"�޸ĳɹ�");		//��ʾ�޸ĳɹ�
			
		}
	}
}
