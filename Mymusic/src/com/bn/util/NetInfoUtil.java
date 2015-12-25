package com.bn.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.List;


public class NetInfoUtil {
    public static Socket ss = null;
    public static DataInputStream din = null;
    public static DataOutputStream dos = null;
    public static String message = "";
    public static byte[] data;
    public static void connect() throws Exception
    {
       ss = new Socket("10.150.45.76",8888);
       din = new DataInputStream(ss.getInputStream());
       dos = new DataOutputStream(ss.getOutputStream());
    }
    public static void disConnect()
    {
    	if(dos != null)
    	{
    		try
    		{
    			dos.flush();
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	if(din != null)
    	{
    		try
    		{
    			din.close();
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	if(ss != null)
    	{
    		try
    		{
    			ss.close();
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
	public static boolean isUser(String info) {
		try
		{
			connect();
			dos.writeUTF(Constant.IsUser+info);
			message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
		return message.equals("ok");
	}

	public static List<String[]> getSingerList() {
		// TODO Auto-generated method stub
		try 
		{
			connect();
			dos.writeUTF(Constant.GetSingerList);
			message = din.readUTF();
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally 
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}

	public static List<String[]> conditionalSearch(String qua) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.ConditionalSearch+qua);
		    message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}

	public static boolean addSinger(String info) {
		// TODO Auto-generated method stub

		try 
		{
			connect();
			dos.writeUTF(Constant.AddSinger+info);
			message = din.readUTF();
		} catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		} finally 
		{
			disConnect();
		}
		return message.equals("ok");
	}

	// 获得歌手下拉单
	public static String getSingerNameForList() 
	{
		try 
		{
			connect();
			dos.writeUTF(Constant.GetSingerForList);
			message = din.readUTF();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			disConnect();
		}
		return message;
	}
	// 获得歌曲文件名下拉单
	public static String getSongFileNameForList() 
	{
		try 
		{
			connect();
			dos.writeUTF(Constant.GetSongForList);
			message = din.readUTF();
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally 
		{
			disConnect();
		}
		return message;
	}
	// 获得专辑下拉单
	public static String getAlbumsNameForList() 
	{
		try 
		{
			connect();
			dos.writeUTF(Constant.GetAlbumForList);
			message = din.readUTF();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally 
		{
			disConnect();
		}
		return message;
	}
	public static List<String[]> getSongList() {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.GetSongList);
			message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}

	public static List<String[]> conditionalSearchSong(String qua) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.ConditionalSearchSong+qua);
			message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}

	public static void deleteSong(String song) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.DeleteSong + song);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			disConnect();
		}
	}

	public static boolean addSong(File f, String song) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.AddSong+song);
			dos.writeLong(f.length());			
			FileInputStream fin = new FileInputStream(f);
			byte[] b = new byte[1024*64];
			int length = 0;
			while((length = fin.read(b)) != -1)
			{
				dos.write(b,0,length);
				dos.flush();
			}
			message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
		return message.equals("ok");
	}

	public static void addLyric(File f, String all) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.AddLyric+all);
			dos.writeLong(f.length());
			FileInputStream fin = new FileInputStream(f);
			byte[] b = new byte[1024*64];
			int length = 0;
			while((length = fin.read(b)) != -1)
			{
				dos.write(b , 0 , length);
			}
			dos.flush();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
	}

	public static boolean addAlbum(String info) {
		// TODO Auto-generated method stub
		try 
		{
			connect();
			dos.writeUTF(Constant.AddAlbum+ info);
			System.out.println("hahnet");
			message = din.readUTF();
		} catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		} finally
		{
			disConnect();
		}
		return message.equals("ok");
	}

	public static List<String[]> getAlbumsList() {
		try 
		{
			connect();
			dos.writeUTF(Constant.GetAlbumList);
			message = din.readUTF();
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}

	public static byte[] getManagePicture(String picname) {
		// TODO Auto-generated method stub
		try
		{
		   connect();
		   dos.writeUTF(Constant.GetManagePicture + picname);
		   data = IOUtil.readBytes(din);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
		return data;
	}

	public static void addPicture(byte[] data, String pic) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF(Constant.AddPicture + pic);
			dos.writeInt(data.length);
			dos.write(data);
			dos.flush();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disConnect();
		}
	}

	// 修改歌手信息
		public static void modifySinger(String info) 
		{
			try
			{
				connect();
				dos.writeUTF(Constant.ModifySinger+ info);
			} catch (Exception e) 
			{
				e.printStackTrace();
			} finally 
			{
				disConnect();
			}
		}
		// 修改歌曲信息
		public static void modifySong(String info) 
		{
			try 
			{
				connect();
				dos.writeUTF(Constant.ModifySong + info);
			} catch (Exception e) 
			{
				e.printStackTrace();
			} finally
			{
				disConnect();
			}
		}
		// 修改专辑信息
		public static void modifyAlbum(String info) 
		{
			try 
			{
				connect();
				dos.writeUTF(Constant.ModifyAlbum + info);
			} catch (Exception e) 
			{
				e.printStackTrace();
			} finally 
			{
				disConnect();
			}
		}
	public static List<String[]> searchSong(String info)
	{
		try
		{
			connect();
			dos.writeUTF(Constant.SearchSong+info);
			message = din.readUTF();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			disConnect();
		}
		return StrListChange.StrToList(message);
	}
}
