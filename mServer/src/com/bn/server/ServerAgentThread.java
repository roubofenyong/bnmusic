package com.bn.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bn.db.DBUtil;
import com.bn.util.Constant;
import com.bn.util.ImageUtil;
import com.bn.util.StrListChange;

public class ServerAgentThread extends Thread {
     Socket sc;
     DataInputStream din;
     DataOutputStream dout;
     FileInputStream fis;
     String msg;
     String songPath;
     String picPath;
     String lyrPath;
     String content = "";
     String mess = "";
     File file;
     String[] array;
     List<String[]> ls = new ArrayList<String[]>();
     
	public ServerAgentThread(Socket sc) {
		// TODO Auto-generated constructor stub
	  this.sc = sc;
	}
	@Override
	public void run() {
         try
         {
        	 
		din= new DataInputStream(sc.getInputStream());
        dout = new DataOutputStream(sc.getOutputStream());
        msg = din.readUTF();
        File fpath = new File("resource");
        songPath = fpath.getAbsolutePath() + "\\SONG\\";
        picPath = fpath.getAbsolutePath() + "\\IMG\\";
        lyrPath = fpath.getAbsolutePath() + "\\LYRIC\\";
        if(msg.startsWith(Constant.AddPicture))
        {
        	content = msg.substring(15,msg.length());
        	array = StrListChange.StrToArray(content);
        	String pid;
        	pid = DBUtil.nextPictureID(DBUtil.getmaxPicture());
        	if(!DBUtil.isPicture(array[0]).equals(""))
        	{
        		DBUtil.updatePicture(array);
        	}else
        	{
        		DBUtil.addPicture(array);
        	}
        	byte[] data = ImageUtil.readBytes(din);
        	file = new File(picPath);
        	if(!file.exists())
        	{
        		file.mkdirs();
        	}
        	ImageUtil.saveImage(data,picPath+pid+".jpg");
        }
        else if(msg.startsWith(Constant.ConditionalSearchSong))
        {
            content = msg.substring(25,msg.length());
            array = StrListChange.StrToArray(content);
            ls = DBUtil.conditionalSearchSong(array);
            mess = StrListChange.ListToStr(ls);
            dout.writeUTF(mess);
        }
        else if(msg.startsWith(Constant.ConditionalSearch))
        {
          content = msg.substring(21,msg.length());
          array = StrListChange.StrToArray(content);
          ls = DBUtil.conditionalSearch(array);
          mess = StrListChange.ListToStr(ls);
          dout.writeUTF(mess);
        }
        else if(msg.startsWith(Constant.IsUser))
        {
        	content = msg.substring(11,msg.length());
        	array = StrListChange.StrToArray(content);
        	dout.writeUTF(DBUtil.isUser(array));
        }
        else if(msg.startsWith(Constant.AddSong))
        {
        	content = msg.substring(12,msg.length());
        	array = StrListChange.StrToArray(content);
        	String filename = array[3];
        	byte[] buf = new byte[4096*2];
        	long lengthTotal = din.readLong();
        	int tempLength = 0;
        	long currLength = 0;
        	file = new File(songPath);
        	if(!file.exists())
        	{
        		file.mkdirs();
        	}
        	file = new File(songPath+filename);
        	FileOutputStream fos = new FileOutputStream(file);
        	while(currLength < lengthTotal)
        	{
        		tempLength = din.read(buf);
        		currLength = currLength + tempLength;
                fos.write(buf, 0, tempLength);
        	}
            fos.close();
            
            DBUtil.addSong(array);
        	dout.writeUTF("ok");
        }
        else if(msg.startsWith(Constant.AddLyric))
        {
        	content = msg.substring(13,msg.length());
        	array = StrListChange.StrToArray(content);
        	String filename = array[1];
        	byte[] buf = new byte[4096];
        	long lengthTotal = din.readLong();
        	int tempLength = 0;
        	long currLength = 0;
        	file = new File(lyrPath);
        	if(!file.exists())
        	{
        		file.mkdirs();
        	}
        	file = new File(lyrPath+filename);
        	FileOutputStream fos = new FileOutputStream(file);
        	while(currLength < lengthTotal)
        	{
        		tempLength = din.read(buf);
        		currLength = currLength + tempLength;
        		fos.write(buf,0,tempLength);
        	}
        	fos.close();
            DBUtil.updateSong(array);        	
        }
        else if(msg.startsWith(Constant.DownloadMP3))
        {
        	content = msg.substring(16,msg.length());
        	File f = new File(songPath+content);
        	dout.writeLong(f.length());
        	FileInputStream fin = new FileInputStream(f);
        	byte[] b = new byte[1024*64];
        	int length = 0;
        	while((length = fin.read(b)) != -1)
        	{
        		dout.write(b,0,length);
        	}
        	dout.flush();
        }
        else if(msg.startsWith(Constant.DownloadLyric))
        {
        	content = msg.substring(18,msg.length());
        	File f = new File(lyrPath+content);
        	dout.writeLong(f.length());
        	FileInputStream fin = new FileInputStream(f);
        	byte[] b = new byte[1024*64];
        	int length = 0;
        	while((length = fin.read(b)) != -1)
        	{
        		dout.write(b,0,length);
        	}
        	dout.flush();
        }
        else if(msg.startsWith(Constant.SearchSong))
        {
        	content = msg.substring(15,msg.length());
        	ls = DBUtil.searchSong(content);
        	mess = StrListChange.ListToStr(ls);
        	dout.writeUTF(mess);
        }
        else if(msg.startsWith(Constant.GetAlbumNameForPic))
        {
        	ls = DBUtil.getAlbumNameForPic();
        	mess = StrListChange.ListToStr(ls);
        	dout.writeUTF(mess);
        }
        else if(msg.startsWith(Constant.GetPicture))
        {
        	String id = msg.substring(15,msg.length());
        	String picname = DBUtil.getPic(id);
        	if(!picname.equals(""))
        	{
        		file = new File(picPath+picname);
        		fis = new FileInputStream(file);
        		byte[] data = new byte[fis.available()];
        		StringBuilder str = new StringBuilder();
        		fis.read(data);
        		for(byte bs : data)
        		{
        			str.append(Integer.toBinaryString(bs));
        		}
        		dout.writeInt(data.length);
        		dout.write(data);
        		dout.flush();
        		
        	}
        }
        else if(msg.startsWith(Constant.GetManagePicture))
        {
        	String albumname = msg.substring(21,msg.length());
        	String picName = DBUtil.getEachAlbumPic(albumname);
        	if(!picName.equals(""))
        	{
        		file = new File(picPath+picName);
        		fis = new FileInputStream(file);
        		byte[] data = new byte[fis.available()];
        		StringBuilder str = new StringBuilder();
        		fis.read(data);
        		for(byte bs : data)
        		{
        			str.append(Integer.toBinaryString(bs));
        		}
        		dout.writeInt(data.length);
        		dout.write(data);
        		dout.flush();
        	}
        }
        else if(msg.startsWith(Constant.DeleteSinger))
        {	
         content = msg.substring(17,msg.length());
         array = StrListChange.StrToArray(content);
         DBUtil.deleteSinger(array);
        }
        else if(msg.startsWith(Constant.DeleteSong))
		{
			content=msg.substring(15,msg.length());
			array=StrListChange.StrToArray(content);
	        DBUtil.deleteSong(array);
		}
        else if(msg.startsWith(Constant.DeleteAlbum))
		{
			content=msg.substring(16,msg.length());
			array=StrListChange.StrToArray(content);
	        DBUtil.deleteAlbum(array);
		}
        else if(msg.startsWith(Constant.GetSingerList))
        {
              ls = DBUtil.getSingerList();
              mess = StrListChange.ListToStr(ls);
              dout.writeUTF(mess);
        }
        else if(msg.startsWith(Constant.GetSingerListTop))
		{
			ls=DBUtil.getSingerListTop();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
        else if(msg.startsWith(Constant.GetSongList))
		{
			ls=DBUtil.getSongList();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
        else if(msg.startsWith(Constant.GetAlbumList))
		{
			ls=DBUtil.getAlbumsList();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
        else if(msg.startsWith(Constant.GetAlbumListTop))
		{
			ls=DBUtil.getAlbumsListTop();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
        else if(msg.startsWith(Constant.AddSinger))
        {
          content = msg.substring(14,msg.length());
          array = StrListChange.StrToArray(content);
          DBUtil.insertSinger(array);
          dout.writeUTF("ok");
        }
        else if(msg.startsWith(Constant.AddAlbum))
		{
			content=msg.substring(13,msg.length());
			array=StrListChange.StrToArray(content);
			DBUtil.insertAlbum(array);
			dout.writeUTF("ok");
		}
        else if(msg.startsWith(Constant.ModifySinger))
		{
			content=msg.substring(17,msg.length());
			array=StrListChange.StrToArray(content);
	        DBUtil.modifySingerContent(array);
		}
        else if(msg.startsWith(Constant.ModifySong))
		{
			content=msg.substring(15,msg.length());
			array=StrListChange.StrToArray(content);
			DBUtil.modifySongContent(array);
		}
        else if(msg.startsWith(Constant.ModifyAlbum))
		{
			content=msg.substring(16,msg.length());
			array=StrListChange.StrToArray(content);
			DBUtil.modifyAlbumContent(array);
		}
        else if(msg.startsWith(Constant.GetSingerForList))
		{
			ls=DBUtil.getSingerNameForList();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
        else if(msg.startsWith(Constant.GetSongForList))
		{
			ls=DBUtil.getSongFileNameForList();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
		//获得专辑下拉条
		else if(msg.startsWith(Constant.GetAlbumForList))
		{
			ls=DBUtil.getAlbumsNameForList();
			mess=StrListChange.ListToStr(ls);
			dout.writeUTF(mess);
		}
		else if(msg.startsWith(Constant.GetSongPath))
		{
			//获得指定专辑图片名字
			content=msg.substring(16,msg.length())+".mp3";			
			//每次获得指定专辑的一张图片				
			file=new File("songPath"+content);
			//获取图片数据
			fis=new FileInputStream(file);
			byte[] data=new byte[fis.available()];
			StringBuilder str=new StringBuilder();
			fis.read(data);
			for(byte bs:data)
			{
				str.append(Integer.toBinaryString(bs));
			}
			dout.writeInt(data.length);
			dout.write(data);
			dout.flush();
		}
        
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }finally
         {
        	 try {din.close();} catch (IOException e) {e.printStackTrace();}
 			try {dout.close();} catch (IOException e) {e.printStackTrace();}
 			try {sc.close();} catch (IOException e) {e.printStackTrace();} 
         }
	}
}
