package com.bn.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bn.util.StrListChange;

public class DBUtil {
    public static Connection getConnection()
    {
    	Connection con = null;
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicbase?useUnicode=true&characterEncoding=UTF-8","root","xuqiang");
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return con;
    }
	public static String getmaxPicture() {
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		String str=new String();
		
		try
		{
			st=con.createStatement();
			String sql="select max(picID) from picture";
			rs=st.executeQuery(sql);
			rs.next();
			if(rs.getString(1)==null)
			{
				str="";
			}
			else
			{
				str=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}

	public static String nextPictureID(String str) {
		String mss = null;
		if(str.length() == 0)
		{
        mss = "p1001";      			
		}
		else
		{
			mss = str.substring(1,str.length());
			mss = str.substring(0,1) + (Integer.parseInt(mss)+1) + "";
		}	
		return mss;
	}

	public static String isPicture(String albumname) {
		// TODO Auto-generated method stub
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		String aid;
		try
		{
			st = con.createStatement();
			String sql = "select Aid from Albums where AlbumName='"+albumname+"';";
		    rs = st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1) == null)
		    {
		    	aid = String.valueOf(0);
		    }else
		    {
		    	aid = rs.getString(1);
		    }
		    String sql2 = "select picID from picture where aid = '"+aid+"';";
		    rs = st.executeQuery(sql2);
		    if(rs.next())
		    {
		    	return rs.getString(1);
		    }
		    else 
		    {
		    	return "";
		    }
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return "";
	}
    private static Object ADDPIC = new Object();
	public static String updatePicture(String[] str) {
		// TODO Auto-generated method stub
		synchronized(ADDPIC)
		{
		  Connection con = getConnection();	
		  Statement st = null;
		  ResultSet rs = null;
		  String aid;
		  String picid = nextPictureID(getmaxPicture());
		  try
		  {
			  st = con.createStatement();
			  String sql = "select Aid from Albums where AlbumName = '"+str[0]+"';";
		      rs = st.executeQuery(sql);
		      rs.next();
		      if(rs.getString(1) == null)
		      {
		    	  aid = String.valueOf(0);
		      }
		      else
		      {
		    	  aid = rs.getString(1);
		      }
		      String sql2 = "update picture set picID = '"+picid+"',picName = '"+picid+".jpg"+"' where aid = '"+aid+"';";
		      st.executeUpdate(sql2);
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  finally
		  {
			    try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
		  }
		  return "ok";
		}
	}

	public static String addPicture(String[] str)
	{
		synchronized(ADDPIC)
		{
			Connection con=getConnection();
			Statement st=null;
			//获得指定歌手id
			String aid=getAlbumId(str[0].trim());
			String picid=nextPictureID(getmaxPicture());
			System.out.println("BDU  "+str[1]);
			try
			{
				st=con.createStatement();
				String sql="insert into picture values('"+aid+"','"+picid+"','"+(picid+".jpg")+"');";
				st.executeUpdate(sql);
			}
			catch(Exception e)
			{
				e.printStackTrace() ;
			}
			finally
			{
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
			
		}
	}

	public static String getAlbumId(String album)
	{
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		String str=null;
		try
		{
			st=con.createStatement();
			String sql="select Aid from Albums where AlbumName='"+album+"';";
			rs=st.executeQuery(sql);
			rs.next();
			if(rs.getString(1)==null)
			{
				str=String.valueOf(0);
			}
			else
			{
				str=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			try{st.close();}catch(Exception e) {e.printStackTrace();}
			try{rs.close();}catch(Exception e) {e.printStackTrace();}	
			try{con.close();}catch(Exception e) {e.printStackTrace();}
		}
		return str;
	}
	public static List<String[]> conditionalSearchSong(String[] qua) {
		// TODO Auto-generated method stub
	   	Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> lstr = new ArrayList<String[]>();
		try
		{
			st = con.createStatement();
			if(!qua[0].equals("所有") && qua[1].equals("所有") && qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where SingerName='"+qua[0]+"'" ;
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str = new String[4];
			    	for(int i = 0 ; i < 4 ; i++)
			    	{
			    		str[i] = rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}
			else if(qua[0].equals("所有") && !qua[1].equals("所有") && qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where Album='"+qua[1]+"'";
				rs = st.executeQuery(sql);
				while(rs.next())
				{
					String[] str = new String[4];
					for(int i = 0 ; i < 4 ; i++)
					{
						str[i] = rs.getString(i+1);
					}
					lstr.add(str);
				}
			}
			else if(qua[0].equals("所有")&&qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where Lyric='"+qua[2]+"';";
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str=new String[4];
			    	for(int i=0;i<4;i++)
			    	{
			    		str[i]=rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}
			else if(!qua[0].equals("所有")&&!qua[1].equals("所有")&&qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where SingerName='"+qua[0]+"' and Album='"+qua[1]+"';";
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str=new String[4];
			    	for(int i=0;i<4;i++)
			    	{
			    		str[i]=rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}//歌手+歌词有无
			else if(!qua[0].equals("所有")&&qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where SingerName='"+qua[0]+"' and Lyric='"+qua[1]+"';";
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str=new String[4];
			    	for(int i=0;i<4;i++)
			    	{
			    		str[i]=rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}//专辑+歌词有无
			else if(qua[0].equals("所有")&&!qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where Album='"+qua[1]+"' and Album='"+qua[2]+"';";
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str=new String[4];
			    	for(int i=0;i<4;i++)
			    	{
			    		str[i]=rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}//歌手+专辑+歌词有无
			else if(!qua[0].equals("所有")&&!qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				String sql = "select SongName,SingerName,Album,Lyric from Song where SingerName='"+qua[0]+"' and Album='"+qua[1]+"' and Lyric='"+qua[2]+"';";
			    rs = st.executeQuery(sql);
			    while(rs.next())
			    {
			    	String[] str=new String[4];
			    	for(int i=0;i<4;i++)
			    	{
			    		str[i]=rs.getString(i+1);
			    	}
			    	lstr.add(str);
			    }
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{rs.close();} catch(Exception e){e.printStackTrace();}
			try{st.close();} catch(Exception e){e.printStackTrace();}
			try{con.close();} catch(Exception e){e.printStackTrace();}
		}
		return lstr;
	}

	public static List<String[]> conditionalSearch(String[] qua) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
		
	    try
		{  
			st=con.createStatement();
			//条件：性别
			if(!qua[0].equals("所有")&&qua[1].equals("所有")&&qua[2].equals("所有"))
			{
				System.out.println("Choose 性别");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where gender='"+qua[0]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
			    }
			}//条件：国籍
			else if(qua[0].equals("所有")&&!qua[1].equals("所有")&&qua[2].equals("所有"))
			{
				System.out.println("Choose 国籍");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Nation='"+qua[1]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}//条件：类别
			else if(qua[0].equals("所有")&&qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				System.out.println("Choose 类别");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Category='"+qua[2]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}//条件：性别+国籍
			else if(!qua[0].equals("所有")&&!qua[1].equals("所有")&&qua[2].equals("所有"))
			{
				System.out.println("Choose 性别+国籍");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Gender='"+qua[0]+"' and Nation='"+qua[1]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}//条件：性别+类别
			else if(!qua[0].equals("所有")&&qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				System.out.println("Choose 性别+类别");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Gender='"+qua[0]+"' and Category='"+qua[2]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}//条件：国籍+类别
			else if(qua[0].equals("所有")&&!qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				System.out.println("Choose 国籍+类别");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Nation='"+qua[1]+"' and Category='"+qua[2]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}//条件：性别+国籍+类别
			else if(!qua[0].equals("所有")&&!qua[1].equals("所有")&&!qua[2].equals("所有"))
			{
				System.out.println("Choose 性别+国籍+类别");
				String sql="select sId,SingerName,Gender,Nation,Category from Singers where Gender='"+qua[0]+"' and Nation='"+qua[1]+"' and Category='"+qua[2]+"';";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[5];
					for(int i=0;i<5;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return lstr;
	}

	public static String isUser(String[] str) {
		// TODO Auto-generated method stub
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		try
		{
			st = con.createStatement();
			String sql = "select uid from user where username ='"+str[0]+"' and ukey = '"+str[1]+"';";
		    rs = st.executeQuery(sql);
		    if(rs.next())
		    {
		    	System.out.println("true");
		    	return "ok";
		    }
		    else 
		    {
		    	System.out.println("false");
		    	return "no";
		    }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return "no";
	}
     private static Object ADDSONG = new Object();
	public static String addSong(String[] ll) {
		// TODO Auto-generated method stub
		synchronized(ADDSONG)
		{
			Connection con = getConnection();
			Statement st = null;
			try
			{
				String sid = getSingerId(ll[0]);
				st = con.createStatement();
				String sql = "insert into song values('"+ll[3]+"','"+ll[2]+"','"+ll[0]+"','"+sid+"','"+ll[1]+"','无'"+");";
				st.executeUpdate(sql);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try{st.close();} catch(SQLException e){e.printStackTrace();}
				try{con.close();} catch(SQLException e){e.printStackTrace();}
			}
		}
		return "ok";
	}

	private static String getSingerId(String singer) {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try
		{
			st = con.createStatement();
			String sql = "select sId from singers where SingerName='"+singer+"'";
			rs = st.executeQuery(sql);
			rs.next();
			if(rs.getString(1) == null)
			{
				str = String.valueOf(0);
			}
			else
			{
				str = rs.getString(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try{st.close();}catch(Exception e) {e.printStackTrace();}
			try{rs.close();}catch(Exception e) {e.printStackTrace();}	
			try{con.close();}catch(Exception e) {e.printStackTrace();}
		}
		return str;
	}
	public static Object ADDLYRIC = new Object();
	public static String updateSong(String[] ll) {
		// TODO Auto-generated method stub
		synchronized(ADDLYRIC)
		{
			//ll[0]=歌手名     ll[1]=专辑名     ll[2]=歌曲名 ll[3]=文件名
			Connection con=getConnection();
			Statement st=null;				
			try
			{
				st=con.createStatement();
				System.out.println(ll);
				String sql="update song set Lyric='有' where FileName='"+ll[0]+"';";
				System.out.println(sql);
				st.executeUpdate(sql);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try{st.close();} catch(SQLException e){e.printStackTrace();}
				try{con.close();} catch(SQLException e){e.printStackTrace();}
			}
		}
		return "ok";
	}

	public static List<String[]> searchSong(String info)
	{
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> lstr=new ArrayList<String[]>();
		
		try
		{
			st = con.createStatement();
		    String sql = "select SongName,SingerName,Album,Lyric,FileName from Song where SingerName='"+info+"' or SongName='"+info+"' or Album='"+info+"';";
		    rs = st.executeQuery(sql);
		    while(rs.next())
		    {
		    	String[] str=new String[5];
		    	for(int i=0;i<5;i++)
		    	{
		    		str[i]=rs.getString(i+1);
		    		System.out.println(i+"======="+str[i]);
		    	}
		    	lstr.add(str);
		    }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{rs.close();} catch(Exception e){e.printStackTrace();}
			try{st.close();} catch(Exception e){e.printStackTrace();}
			try{con.close();} catch(Exception e){e.printStackTrace();}
		}
		
		return lstr;
	}

	public static List<String[]> getAlbumNameForPic() {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String[] aid = StrListChange.StrToArray(StrListChange.ListToStr(getAidList()));
		List<String[]> list = new ArrayList<String[]>();
		try
		{
			st = con.createStatement();
			for(int i = 0 ; i < aid.length ; i++)
			{
				String sql = "select albumname from albums where aid = '"+aid[i]+"';";
				rs = st.executeQuery(sql);
				while(rs.next())
				{
					String[] str = new String[1];
					for(int j = 0 ; j < 1 ; j++)
					{
						str[j] = rs.getString(j+1);
					}
					list.add(str);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}

	private static List<String[]> getAidList() {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String[]> lstr=new ArrayList<String[]>();
		    try
			{  
				st=con.createStatement();
				String sql ="select aid from picture;";
				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String[] str=new String[1];
					for(int i=0;i<1;i++)
					{
						str[i]=rs.getString(i+1);
						System.out.println(i+"======="+str[i]);
					}
					lstr.add(str);
			    }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
		    return lstr;
	}
	public static String  getPic(String id)
	{
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    String picname="";
	    try
		{  
			st=con.createStatement();
			String sql ="select picName from picture where aid='"+id+"';";
			System.out.println("ssql"+sql);
			rs=st.executeQuery(sql);			
			while(rs.next())
			{				
				picname=rs.getString(1);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return picname;
	}

	public static String getEachAlbumPic(String albumname) {
		// TODO Auto-generated method stub
		Connection con=getConnection();
		Statement st = null;
	    ResultSet rs = null;
	    String aid;
	    String str = null;
	    try
		{  
			st=con.createStatement();
			String sql="select aid from albums where albumname='"+albumname+"';";
			rs=st.executeQuery(sql);
			rs.next();
			if(rs.getString(1)==null)
			{
				aid=String.valueOf(0);
			}
			else
			{
				aid=rs.getString(1);
			}
			String sql2="select picName from picture where aid='"+aid+"';";
			rs=st.executeQuery(sql2);
			if(rs.next())
			{
				
				str=rs.getString(1);
				System.out.println("str1======="+str);
				
			}else
			{
				str="";
                System.out.println("str2======="+str);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return str;
	}
    private static Object DELETESINGERCONTENT = new Object();
	public static void deleteSinger(String[] ll) {
		// TODO Auto-generated method stub
		synchronized(DELETESINGERCONTENT)
		{
			Connection con = getConnection();
			Statement st = null;
			try
			{
				st = con.createStatement();
				String sql = "delete from singers where sId='"+ll[0]+"';";
			    st.executeUpdate(sql);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				 try{st.close();} catch(SQLException e){e.printStackTrace();}
				    try{con.close();} catch(SQLException e){e.printStackTrace();}
			}
			
		}
	}

	private static Object DELETESONGCONTENT=new Object();
	public static void deleteSong(String[] ll)
	{
		synchronized(DELETESONGCONTENT)
		{
			Connection con=getConnection();
		    Statement st=null;
		
		    try
		    {
			    st=con.createStatement();				  
			    String sql="delete from song where SongName='"+ll[0]+"' and SingerName='"+ll[1]+"';";
			    st.executeUpdate(sql);
		    }
		    catch(Exception e)
		    {
			    e.printStackTrace();
		    }
		    finally
		    {
			    try{st.close();} catch(SQLException e){e.printStackTrace();}
			    try{con.close();} catch(SQLException e){e.printStackTrace();}
		    }
		}
	}

	//删除专辑
			private static Object DELETEALBUMCONTENT=new Object();
			public static void deleteAlbum(String[] ll)
			{
				synchronized(DELETEALBUMCONTENT)
				{
					Connection con=getConnection();
				    Statement st=null;
				
				    try
				    {
					    st=con.createStatement();
					    String sql="delete from albums where AlbumName='"+ll[0]+"' and SingerName='"+ll[1]+"';";
					    st.executeUpdate(sql);
				    }
				    catch(Exception e)
				    {
					    e.printStackTrace();
				    }
				    finally
				    {
					    try{st.close();} catch(SQLException e){e.printStackTrace();}
					    try{con.close();} catch(SQLException e){e.printStackTrace();}
				    }
				}
			}

			public static List<String[]> getSingerList()
			{
				Connection con = getConnection();
			    Statement st = null;
			    ResultSet rs = null;
			    List<String[]> lstr=new ArrayList<String[]>();
			    try
				{  
					st=con.createStatement();
					String sql ="select sId,SingerName,Gender,Nation,Category from Singers;";
					rs=st.executeQuery(sql);
					while(rs.next())
					{
						String[] str=new String[5];
						for(int i=0;i<5;i++)
						{
							str[i]=rs.getString(i+1);
							System.out.println(i+"======="+str[i]);
						}
						lstr.add(str);
				    }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try {rs.close();} catch (SQLException e) {e.printStackTrace();}
					try {st.close();} catch (SQLException e) {e.printStackTrace();}
					try {con.close();} catch (SQLException e) {e.printStackTrace();}
				}
			    return lstr;
			}

	public static List<String[]> getSingerListTop() {
		// TODO Auto-generated method stub
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
	    try
	    {
	    	st = con.createStatement();
	    	String sql = "select sId,SingerName,Gender,Nation,Category from Singers limit 3;";
	    	rs=st.executeQuery(sql);
			while(rs.next())
			{
				String[] str=new String[5];
				for(int i=0;i<5;i++)
				{
					str[i]=rs.getString(i+1);
					System.out.println(i+"======="+str[i]);
				}
				lstr.add(str);
		    }
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally
	    {
	    	try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
	    }
		
		return null;
	}

	public static List<String[]> getSongList()
	{
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> lstr=new ArrayList<String[]>();
		
		try
		{
			st = con.createStatement();
		    String sql = "select SongName,SingerName,Album,Lyric,FileName from Song;";
		    rs = st.executeQuery(sql);
		    while(rs.next())
		    {
		    	String[] str=new String[5];
		    	for(int i=0;i<5;i++)
		    	{
		    		str[i]=rs.getString(i+1);
		    		System.out.println(i+"======="+str[i]);
		    	}
		    	lstr.add(str);
		    }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{rs.close();} catch(Exception e){e.printStackTrace();}
			try{st.close();} catch(Exception e){e.printStackTrace();}
			try{con.close();} catch(Exception e){e.printStackTrace();}
		}
		return lstr;
	}

	public static List<String[]> getAlbumsList() {
		// TODO Auto-generated method stub
		Connection con = getConnection();
		File fpath = new File("resource");
		String picPath = fpath.getAbsolutePath() + "\\IMG\\";
		Statement st = null;
		ResultSet rs = null;
		List<String[]> lstr = new ArrayList<String[]>();
		try
		{
		   st = con.createStatement();
		   String sql = "select AlbumName,SingerName from Albums;";
		   rs = st.executeQuery(sql);
		   while(rs.next())
		   {
			   String[] str = new String[3];
			   for(int i = 0 ; i < 2 ;i++)
			   {
				   str[i] = rs.getString(i+1);
			   }
			   if(!getEachAlbumPic(str[0]).equals(""))
			   {
				   str[2] = picPath + getEachAlbumPic(str[0]);
			   }
			   else
			   {
				   str[2] = null;
			   }
			   lstr.add(str);
		   }
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return lstr;
	}

	public static List<String[]> getAlbumsListTop()
	{
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
	    try
		{  
			st=con.createStatement();
			String sql ="select AlbumName,SingerName,Aid from Albums limit 3;";
			System.out.println(""+sql);
			rs=st.executeQuery(sql);
			
			while(rs.next())
			{
				String[] str=new String[3];
				for(int i=0;i<3;i++)
				{
					str[i]=rs.getString(i+1);
					System.out.println(i+"======="+str[i]);
				}
				lstr.add(str);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return lstr;
	}

	//添加歌手的歌手名，国籍，专辑，性别
		private static Object INSERTSINGER=new Object();
		public static String insertSinger(String[] ll)
		{
			synchronized(INSERTSINGER)
			{
				Connection con=getConnection();
				Statement st=null;
				String id=nextSingerID();
				String sql;
				for(int i=0;i<ll.length;i++)
				{
					System.out.println("insert:"+ll[i]);
				}
				
				try
				{
					st=con.createStatement();
					System.out.print(id+"=id");
					
					sql="insert into singers values('"+id+"','"+ll[0]+"','"+ll[3]+"','"+ll[1]+"','"+ll[2]+"');";	
												
					System.out.print(sql);
					st.executeUpdate(sql);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try{st.close();} catch(SQLException e){e.printStackTrace();}
					try{con.close();} catch(SQLException e){e.printStackTrace();}
				}
				return "ok";		
			}
		}

		public static String nextSingerID()
		{
			String str=maxSingerID();
			System.out.println("id:"+str);
			String mss=null;
			if(str==null)
			{
				mss="10001";
			}
			else
			{
				mss=Integer.parseInt(str)+1+"";
			}
			System.out.println("mss:"+mss);
			return mss;
		}
		public static String maxSingerID()
		{
			Connection con=getConnection();
			Statement st=null;
			ResultSet rs=null;
			String str=null;
			
			try
			{
				st=con.createStatement();
				String sql="select max(sId) from singers";
				rs=st.executeQuery(sql);
				rs.next();
				System.out.print(rs.getString(1));
				if(rs.getString(1)==null)
				{
					str=null;
				}
				else
				{
					str=rs.getString(1);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try{rs.close();} catch(SQLException e){e.printStackTrace();}
				try{st.close();} catch(SQLException e){e.printStackTrace();}
				try{con.close();} catch(SQLException e){e.printStackTrace();}
			}
			System.out.println("max id:"+str);
			return str;
		}
		private static Object INSERTALBUM=new Object();
		public static String insertAlbum(String[] ll)
		{
			synchronized(INSERTALBUM)
			{
				Connection con=getConnection();
				Statement st=null;
				System.out.println("hah");
				String aid=nextAlbumID(getmaxAlbum());
				String sid=getSingerId(ll[0]);
				try
				{
					st=con.createStatement();					
					String sql="insert into albums values('"+ll[1]+"','"+ll[0]+"','"+sid+"','"+aid+"');";
				    st.executeUpdate(sql);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try{st.close();} catch(SQLException e){e.printStackTrace();}
					try{con.close();} catch(SQLException e){e.printStackTrace();}
				}
				return "ok";		
			}
		}

		public static String nextAlbumID(String str)
		{
			String mss=null;
			if(str.length()==0)
			{
				mss="A1001";
			}
			else
			{
				mss=str.substring(1,str.length());
				mss=str.substring(0, 1)+(Integer.parseInt(mss)+1)+"";
			}
			return mss;
		}
	public static String getmaxAlbum()
	{
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		String str=new String();
		try
		{
			st=con.createStatement();
			String sql="select max(Aid) from Albums;";
			rs=st.executeQuery(sql);
			rs.next();
			if(rs.getString(1)==null)
			{
				str="";
			}
			else
			{
				str=rs.getString(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	private static Object MODIFYSINGERCONTENT=new Object();
	public static void modifySingerContent(String[] ll)
	{
		synchronized(MODIFYSINGERCONTENT)
		{
			Connection con=getConnection();
		    Statement st=null;
		
		    try
		    {
			    st=con.createStatement();
			    String sql="update singers set SingerName='"+ll[1]+"',Gender='"+ll[2]+"',Nation='"+ll[3]+"',Category='"+ll[4]+"' where sId='"+ll[0]+"';";
			    System.out.println("modifysinger"+sql);
			    st.executeUpdate(sql);
		    }
		    catch(Exception e)
		    {
			    e.printStackTrace();
		    }
		    finally
		    {
			    try{st.close();} catch(SQLException e){e.printStackTrace();}
			    try{con.close();} catch(SQLException e){e.printStackTrace();}
		    }
		}
	}

	private static Object MODIFYSONGCONTENT=new Object();
	public static void modifySongContent(String[] ll)
	{
		synchronized(MODIFYSONGCONTENT)
		{
			Connection con=getConnection();
		    Statement st=null;
		
		    try
		    {
			    st=con.createStatement();		    
			    String sql="update song set Album='"+ll[2]+"' where SongName='"+ll[0]+"' and  SingerName='"+ll[1]+"';";
			    System.out.println(sql);
			    st.executeUpdate(sql);
		    }
		    catch(Exception e)
		    {
			    e.printStackTrace();
		    }
		    finally
		    {
			    try{st.close();} catch(SQLException e){e.printStackTrace();}
			    try{con.close();} catch(SQLException e){e.printStackTrace();}
		    }
		}
	}
	private static Object MODIFYALBUMCONTENT=new Object();
	public static void modifyAlbumContent(String[] ll)
	{
		synchronized(MODIFYALBUMCONTENT)
		{
			Connection con=getConnection();
		    Statement st=null;
		
		    try
		    {
			    st=con.createStatement();
			    String sql="update albums set SingerName='"+ll[0]+"' where AlbumName='"+ll[1]+"';";
			    st.executeUpdate(sql);
		    }
		    catch(Exception e)
		    {
			    e.printStackTrace();
		    }
		    finally
		    {
			    try{st.close();} catch(SQLException e){e.printStackTrace();}
			    try{con.close();} catch(SQLException e){e.printStackTrace();}
		    }
		}
	}

	public static List<String[]> getSingerNameForList()
	{
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
	    try
		{  
			st=con.createStatement();
			String sql ="select SingerName from Singers;";
			rs=st.executeQuery(sql);
 			
			while(rs.next())
			{
				String[] str=new String[1];
				for(int i=0;i<1;i++)
				{
					str[i]=rs.getString(i+1);
					System.out.println(i+"======="+str[i]);
				}
				lstr.add(str);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return lstr;
	}
	public static List<String[]> getSongFileNameForList()
	{
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
	    try
		{  
			st=con.createStatement();
			String sql ="select FileName from Song;";
			rs=st.executeQuery(sql);
 			
			while(rs.next())
			{
				String[] str=new String[1];
				for(int i=0;i<1;i++)
				{
					str[i]=rs.getString(i+1);
					System.out.println(i+"======="+str[i]);
				}
				lstr.add(str);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return lstr;
	}
	public static List<String[]> getAlbumsNameForList()
	{
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> lstr=new ArrayList<String[]>();
	    try
		{  
			st=con.createStatement();
			String sql ="select AlbumName from Albums;";
			rs=st.executeQuery(sql);
 			
			while(rs.next())
			{
				String[] str=new String[1];
				for(int i=0;i<1;i++)
				{
					str[i]=rs.getString(i+1);
					System.out.println(i+"======="+str[i]);
				}
				lstr.add(str);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	    return lstr;
	}
	public static void main(String args[])
	{
		String a[]={"十年阿道夫","asf"};
		updatePicture(a);
	}
}

