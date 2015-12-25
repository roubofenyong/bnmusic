package com.bn.singerpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bn.albumpanel.JAddAlbumPanel;
import com.bn.musicpanel.JAddMusicPanel;
import com.bn.musicpanel.JLookMusicPanel;
import com.bn.util.NetInfoUtil;

public class JAddSingerPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
    JTextField j_singername = new JTextField();
    JComboBox j_sexC = new JComboBox();
    JComboBox j_nation = new JComboBox();
    JComboBox j_sort = new JComboBox();
    JButton jadd = new JButton("ȷ��");
    public JAddSingerPanel()
    {
    	this.setLayout(null);
    	String[] sex = {"��","Ů","N/A"};
    	String[] nation = {"����","����","����","����","����","����","����","����"};
        String[] sort = {"����","ҡ��","��ʿ","����","����","˵��","����"};
        JLabel j_title = new JLabel("��Ӹ���");
        j_title.setFont(new Font("����",Font.BOLD,25));
        this.add(j_title);
        j_title.setBounds(370,50,150,50);
        
        JLabel j_music = new JLabel("����");
        j_music.setFont(new Font("����",Font.BOLD,20));
        this.add(j_music);
        j_music.setBounds(220,120,120,30);
    
        this.add(j_singername);
        j_singername.setBounds(320,120,300,30);
        JLabel j_music_need = new JLabel("*");
        j_music_need.setFont(new Font("����",Font.BOLD,12));
        j_music_need.setForeground(Color.red);
        this.add(j_music_need);
        j_music_need.setBounds(620,120,50,30);
        JLabel j_sexa = new JLabel("�Ա�");
        j_sexa.setFont(new Font("����",Font.BOLD,20));
        this.add(j_sexa);
        j_sexa.setBounds(220,200,120,30);
        j_sexC = new JComboBox(sex);
        this.add(j_sexC);
        j_sexC.setBounds(320,200,300,30);
        JLabel j_guojia = new JLabel("����:");
        j_guojia.setFont(new Font("����",Font.BOLD,20));
        this.add(j_guojia);
        j_guojia.setBounds(220,280,120,30);
        j_nation = new JComboBox(nation);
        this.add(j_nation);
        j_nation.setBounds(320,280,300,30);
        JLabel j_liebie = new JLabel("���:");
        j_liebie.setFont(new Font("����",Font.BOLD,20));
        this.add(j_liebie);
        j_liebie.setBounds(220,360,120,30);
        j_sort = new JComboBox(sort);
        this.add(j_sort);
        j_sort.setBounds(320,360,300,30);
        JLabel ta = new JLabel("ע��: ����*��Ϊ����ѡ��");      
        ta.setFont(new Font("����",Font.BOLD,15));
        ta.setForeground(Color.blue);
        this.add(ta);
        ta.setBounds(320,400,400,30);
        jadd.setFont(new Font("����",Font.BOLD,25));
        this.add(jadd);
        jadd.setBounds(370,440,100,30);
        jadd.addActionListener(this);    
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jadd)
		{
			if(j_singername.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "�������������");
			}else
			{
				String all = j_singername.getText()+"<#>";
				all += j_sexC.getSelectedItem()+"<#>";
				all += j_sort.getSelectedItem()+"<#>";
				all += j_nation.getSelectedItem();
				String singername = j_singername.getText();
				Boolean bb = NetInfoUtil.addSinger(all);
				if(bb)
				{
					JOptionPane.showMessageDialog(null, "��ӳɹ�");
					JAddMusicPanel.j_singerC.addItem(singername);
					JLookMusicPanel.j_singerC.addItem(singername);
				    JAddAlbumPanel.j_singerC.addItem(singername);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "���ʧ��");
				}
				
				
			}
		}
	}

}
