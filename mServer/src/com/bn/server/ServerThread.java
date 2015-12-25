package com.bn.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.bn.util.Constant;

public class ServerThread extends Thread {
     ServerSocket ss;
     public void run() {
    	// TODO Auto-generated method stub
    	 try
    	 {
          ss  = new ServerSocket(8888);
          while(Constant.flag)
          {
        	  Socket sc = ss.accept();
        	  new ServerAgentThread(sc).start();
          }
    	 }catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
     
     }
     public static void main(String[] args)
     {
    	 new ServerThread().start();
     }

}
