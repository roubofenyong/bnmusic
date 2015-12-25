package com.bn.frame;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.bn.albumpanel.JMakeAlbumTable;
import com.bn.albumpanel.MyTableModel;
import com.bn.musicpanel.JMakeMusicTable;
import com.bn.singerpanel.JMakeSingerTable;

public class PrimaryTree implements TreeSelectionListener{
   JTree jt = new JTree();
   DefaultMutableTreeNode node_singer = new DefaultMutableTreeNode("歌手管理");
   DefaultMutableTreeNode node_music = new DefaultMutableTreeNode("歌曲管理");
   DefaultMutableTreeNode node_album = new DefaultMutableTreeNode("专辑管理");
   
    public PrimaryTree()
    {
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode("信息浏览");
    	TreeModel all = new DefaultTreeModel(top);
    	top.add(node_singer);
    	top.add(node_music);
    	top.add(node_album);
    	jt.setModel(all);
    	jt.setShowsRootHandles(true);
    	jt.addTreeSelectionListener(this);
    }
 	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
	 DefaultMutableTreeNode node = (DefaultMutableTreeNode)jt.getLastSelectedPathComponent();
	 if(node.equals(node_singer))
	 {
		 JMakeSingerTable.s = 0;
		 new JMakeSingerTable();
		 PrimaryFrame.cl.show(PrimaryFrame.jall,"jsingermanage");
	 }
	 else if(node.equals(node_music))
	 {
		 JMakeMusicTable.s = 0;
		 new JMakeMusicTable();
		 PrimaryFrame.cl.show(PrimaryFrame.jall,"jmusicmanage");
	 }
	 else if(node.equals(node_album))
	 {
		 new MyTableModel();
		 new JMakeAlbumTable();
		 PrimaryFrame.cl.show(PrimaryFrame.jall,"jalbummanage");
	 }
	}

}
