package com.example.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import android.telecom.DisconnectCause;

public class NetInfoUtil {
	public static Socket ss;
	public static DataInputStream din;
	public static DataOutputStream dos;
	public static String message;
    public static void connect() throws Exception
    {
    	ss = new Socket();
    	SocketAddress socketAddress = new InetSocketAddress(MusicApplication.socketIp,8888);
    	ss.connect(socketAddress,5000);
    	din = new DataInputStream(ss.getInputStream());
    	dos = new DataOutputStream(ss.getOutputStream());
    }
	public static List<String[]> searchSong(String info) {
		// TODO Auto-generated method stub
		try
		{
			connect();
			dos.writeUTF("<#SEARCH_SONG#>"+info);
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
	public static void disConnect() {
		// TODO Auto-generated method stub
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

}
