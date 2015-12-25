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
	public static JComboBox j_albumC=new JComboBox();//下拉列表	
	
	JLabel JtitleL;
	
	JTextField jAddAlbumPic;        			//添加 新闻图片	
	JTextField jAddPicT;        				//添加 专辑图片
	
	JButton jOpen;           					//打开文件
	JButton jAddPicOK;            				//确定 按钮
	JButton jAddNewsRe;           				//重置按钮
	
	JFileChooser chooser = new JFileChooser();
	public JAddPicPanel()
	{
		
		String album=NetInfoUtil.getAlbumsNameForList();	//获取专辑
		String[] albumlist=album.split("<#>\\|");
		
		this.setLayout(null);
		
		JtitleL=new JLabel("添加图片");
		this.add(JtitleL);
		JtitleL.setFont(new Font("宋体",Font.BOLD,25));
		JtitleL.setBounds(350,20, 200, 50);
		
		j_albumC=new JComboBox(albumlist);
		j_albumC.setSelectedItem(MyTableModel.data[JLookAlbumPanel.rows][1]);
		this.add(j_albumC);
		j_albumC.setBounds(250,80,400,30);
		
		jOpen=new JButton("打开文件夹");
		this.add(jOpen);
		jOpen.setBounds(570,150,100,30);
		jOpen.addActionListener(this);
		
		jAddPicT=new JTextField();
		this.add(jAddPicT);
		jAddPicT.setBounds(250,150,300,30);
		
		jAddPicOK=new JButton("确定");
		this.add(jAddPicOK);                      	 		//确定按钮
		jAddPicOK.setBounds(350,300,150,40);
		jAddPicOK.setFont(new Font("宋体",Font.BOLD,25));
		jAddPicOK.addActionListener(this);			
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jOpen)             				//点击打开文件按钮
		{	
			//只显示文件名后缀是JPG,GIF,PNG的文件
			FileNameExtensionFilter filter
				=new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif","png");			
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this);	//判断是否选择了文件
			if(returnVal == JFileChooser.APPROVE_OPTION) 	//如果选择了图片文件
			{
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName()); 
				File file=chooser.getSelectedFile();		//获取文件名
				BufferedImage bi=null;						//定义一个图片
				try
				{
					bi=ImageIO.read(file);					//读取选中的图片文件
					double picHeight=bi.getHeight();		//获取图片的高
					double picWidth=bi.getWidth();			//获取图片的宽
					if(picHeight>600||picWidth>800)			//如果高大于600或宽大于800
					{
						JOptionPane.showMessageDialog(null,"图片像素高不能大于600，宽不能大于800");
					}else if(!(picHeight/3==picWidth/4))	//如果宽高比例不为4:3
					{
						JOptionPane.showMessageDialog(null, "图片宽高比例应为4:3");
					}
					else									//满足宽高比例，大小
					{	
						//添加图片文件名到输入框
						jAddPicT.setText(chooser.getSelectedFile().getName());    
					}	
				}catch(Exception e1)
				{
					e1.printStackTrace();					
				}
			}
						
		}else if(e.getSource()==jAddPicOK)
		{
			if(jAddPicT.getText().equals(""))				//如果输入框为空
			{
				JOptionPane.showMessageDialog(null, "请选择图片");
			}else
			{
				String newsContent=j_albumC.getSelectedItem()+"<#>";	//添加专辑名到字符串中 						
				newsContent+=jAddPicT.getText();						//添加文件名到字符串中
				//获取文件路径及文件名
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
				NetInfoUtil.addPicture(data, newsContent); 	//上传文件和专辑信息到服务器
				JOptionPane.showMessageDialog(null, "数据库已经成功接收！");
			}
		}	
	}
}
