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
	
	int rows=JLookAlbumPanel.jt_Album.getSelectedRow();			//获取选中的行
	
	JLabel j_album;
	JComboBox j_singername;
	JButton j_ok=new JButton("确定");
	
	public JMakeAlbumEditPanel()
	{		
		String singer=NetInfoUtil.getSingerNameForList();		//获取歌手
		String[] singerlist=singer.split("<#>\\|");
		
		this.setLayout(null);

		Label title=new Label("编辑专辑");
		this.add(title);
		title.setFont(new Font("宋体",Font.BOLD,25));
		title.setBounds(320,0,120, 50);
		
		Label xuhao=new Label("专辑名:");
		this.add(xuhao);
		xuhao.setFont(new Font("宋体",Font.BOLD,20));
		xuhao.setBounds(130,100,120, 30);
		
		j_album=new JLabel((String) MyTableModel.data[rows][1]);
		this.add(j_album);
		j_album.setBounds(250,100,300,30);
		
		
		Label singern=new Label("歌手名:");
		this.add(singern);
		singern.setFont(new Font("宋体",Font.BOLD,20));
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
			String album;										//创建一个字符串，用于收集修改后的专辑信息
			album=j_singername.getSelectedItem()+"<#>";
			album+=j_album.getText();
			
			NetInfoUtil.modifyAlbum(album);						//更新专辑表的数据库
			
			JOptionPane.showMessageDialog(null,"修改成功");		//提示修改成功
			
		}
	}
}
