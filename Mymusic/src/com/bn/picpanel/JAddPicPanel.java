package com.bn.picpanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.bn.albumpanel.JLookAlbumPanel;
import com.bn.albumpanel.MyTableModel;
import com.bn.util.NetInfoUtil;

public class JAddPicPanel extends JPanel
implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JComboBox j_albumC=new JComboBox();//�����б�	
	
	JLabel JtitleL;
	
	JTextField jAddAlbumPic;        			//��� ����ͼƬ	
	JTextField jAddPicT;        				//��� ר��ͼƬ
	
	JButton jOpen;           					//���ļ�
	JButton jAddPicOK;            				//ȷ�� ��ť
	JButton jAddNewsRe;           				//���ð�ť
	
	JFileChooser chooser = new JFileChooser();
	public JAddPicPanel()
	{
		
		String album=NetInfoUtil.getAlbumsNameForList();	//��ȡר��
		String[] albumlist=album.split("<#>\\|");
		
		this.setLayout(null);
		
		JtitleL=new JLabel("���ͼƬ");
		this.add(JtitleL);
		JtitleL.setFont(new Font("����",Font.BOLD,25));
		JtitleL.setBounds(350,20, 200, 50);
		
		j_albumC=new JComboBox(albumlist);
		j_albumC.setSelectedItem(MyTableModel.data[JLookAlbumPanel.rows][1]);
		this.add(j_albumC);
		j_albumC.setBounds(250,80,400,30);
		
		jOpen=new JButton("���ļ���");
		this.add(jOpen);
		jOpen.setBounds(570,150,100,30);
		jOpen.addActionListener(this);
		
		jAddPicT=new JTextField();
		this.add(jAddPicT);
		jAddPicT.setBounds(250,150,300,30);
		
		jAddPicOK=new JButton("ȷ��");
		this.add(jAddPicOK);                      	 		//ȷ����ť
		jAddPicOK.setBounds(350,300,150,40);
		jAddPicOK.setFont(new Font("����",Font.BOLD,25));
		jAddPicOK.addActionListener(this);			
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jOpen)             				//������ļ���ť
		{	
			//ֻ��ʾ�ļ�����׺��JPG,GIF,PNG���ļ�
			FileNameExtensionFilter filter
				=new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif","png");			
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this);	//�ж��Ƿ�ѡ�����ļ�
			if(returnVal == JFileChooser.APPROVE_OPTION) 	//���ѡ����ͼƬ�ļ�
			{
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName()); 
				File file=chooser.getSelectedFile();		//��ȡ�ļ���
				BufferedImage bi=null;						//����һ��ͼƬ
				try
				{
					bi=ImageIO.read(file);					//��ȡѡ�е�ͼƬ�ļ�
					double picHeight=bi.getHeight();		//��ȡͼƬ�ĸ�
					double picWidth=bi.getWidth();			//��ȡͼƬ�Ŀ�
					if(picHeight>600||picWidth>800)			//����ߴ���600������800
					{
						JOptionPane.showMessageDialog(null,"ͼƬ���ظ߲��ܴ���600�����ܴ���800");
					}else if(!(picHeight/3==picWidth/4))	//�����߱�����Ϊ4:3
					{
						JOptionPane.showMessageDialog(null, "ͼƬ��߱���ӦΪ4:3");
					}
					else									//�����߱�������С
					{	
						//���ͼƬ�ļ����������
						jAddPicT.setText(chooser.getSelectedFile().getName());    
					}	
				}catch(Exception e1)
				{
					e1.printStackTrace();					
				}
			}
						
		}else if(e.getSource()==jAddPicOK)
		{
			if(jAddPicT.getText().equals(""))				//��������Ϊ��
			{
				JOptionPane.showMessageDialog(null, "��ѡ��ͼƬ");
			}else
			{
				String newsContent=j_albumC.getSelectedItem()+"<#>";	//���ר�������ַ����� 						
				newsContent+=jAddPicT.getText();						//����ļ������ַ�����
				//��ȡ�ļ�·�����ļ���
				File f=new File(chooser.getCurrentDirectory()+"\\"+chooser.getSelectedFile().getName());   	
				FileInputStream fis = null;				
				byte[] data = null;						
				try {				
					fis = new FileInputStream(f);			
					data = new byte[fis.available()];	
					StringBuilder str = new StringBuilder();	
					fis.read(data);								
					for (byte bs : data) {
						str.append(Integer.toBinaryString(bs));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				NetInfoUtil.addPicture(data, newsContent); 	//�ϴ��ļ���ר����Ϣ��������
				JOptionPane.showMessageDialog(null, "���ݿ��Ѿ��ɹ����գ�");
			}
		}	
	}
}
