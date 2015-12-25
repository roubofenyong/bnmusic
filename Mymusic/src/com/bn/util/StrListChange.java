package com.bn.util;

import java.util.ArrayList;
import java.util.List;

public class StrListChange {
	public static String[] StrToArray(String info) {
		// TODO Auto-generated method stub
		int num = 0;
		String[] first = info.split("\\|");
		for(int i = 0 ; i < first.length ; i++)
		{
			String[] temp1 = first[i].split("<#>");
			num += temp1.length;
		}
		String[] temp2 = new String[num];
		num = 0;
		for(String second : first)
		{
			String[] temp3 = second.split("<#>");
			for(String third : temp3)
			{
				temp2[num] = third;
				num++;
			}
		}
		return temp2;
	}

	public static List<String[]> StrToList(String info) {
		// TODO Auto-generated method stub
	List<String[]> list = new ArrayList<String[]>();
	String[] s = info.split("\\|");
	int num = 0;
	for(String ss : s)
	{
	 num = 0;
	 String[] temp = ss.split("<#>");
	 String[] midd = new String[temp.length];
	 for(String a : temp)
	 {
		 midd[num++] = a;
	 }
	 list.add(midd);
	}
		return list;
	}

}
