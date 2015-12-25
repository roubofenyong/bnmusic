package com.bn.util;

import java.util.ArrayList;
import java.util.List;

public class StrListChange 
{
	//将字符串转换成列表数据
	public static List<String[]> StrToList(String info)
	{
		List<String[]> list = new ArrayList<String[]>();//创建一个新列表
		String[] s = info.split("\\|");					//将字符串以“|”为界分隔开
		int num = 0;									//定义大小常量
		for(String ss:s)								//遍历数组
		{
			num = 0;									//计数器
			String[] temp = ss.split("<#>");			//将字符串以"<#>"为界分隔开
			String[] midd = new String[temp.length];	//创建临时数组
			for(String a:temp)							//遍历数组
			{
				midd[num++] = a;
			}	
			list.add(midd);								//将字符串加入列表
		}
	    return list;									//返回列表
	}
	//将字符串转换成数组
	public static String[] StrToArray(String info)
	{
		int num = 0;							//定义大小常量
		String[] first = info.split("\\|");		//将字符串以“|”为界分隔开
		for(int i=0;i<first.length;i++)			//遍历字符串数组
		{
			String[] temp1 = first[i].split("<#>");	//将字符串以"<#>"分隔开
			num+=temp1.length;						
		}
		String[] temp2=new String[num];				//创建临时数组
		num=0;										//计数器清零
		for(String second:first)					//遍历数组
		{
			String[] temp3=second.split("<#>");		//将字符串以"<#>"分隔开
			for(String third:temp3)					//遍历数组
			{
			    temp2[num]=third;					//给临时数组赋值
			    num++;								//计时器递增
			}
		}
		return temp2;								//返回临时数组
	}
	//将List转换成字符串
	public static String ListToStr(List<String[]> list)
	{
		String mess="";									//定义字符串常量
		List<String[]> ls=new ArrayList<String[]>();	//创建一个新的列表
		ls=list;										//给列表赋值
		for(int i=0;i<ls.size();i++)					//遍历列表
		{	
			String[] ss=ls.get(i);						//将列表的值赋给字符串
			for(String s:ss)							//遍历字符串
			{
			    mess+=s+"<#>";							//更新字符串
			}
			mess+="|";									//字符串末尾加"|"
		}
		return mess;									//返回字符串
	}	
}
