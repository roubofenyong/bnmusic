package com.bn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtil {

	public static byte[] readBytes(DataInputStream din) {
		// TODO Auto-generated method stub
		byte[] data = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try
		{
			int length = 0, tempRev = 0, size;
			length = din.readInt();
			byte[] buf = new byte[length - tempRev];
			while((size = din.read(buf)) != -1)
			{
				tempRev += size;
				out.write(buf,0,size);
				if(tempRev >= length)
				{
					break;
				}
				buf = new byte[length - tempRev];
			}
		data = out.toByteArray();
		out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public static void saveImage(byte[] data, String path) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.flush();
		fos.close();
	}

}
