package com.bn.util;

import java.util.ArrayList;
import java.util.List;

public class StrListChange 
{
	//���ַ���ת�����б�����
	public static List<String[]> StrToList(String info)
	{
		List<String[]> list = new ArrayList<String[]>();//����һ�����б�
		String[] s = info.split("\\|");					//���ַ����ԡ�|��Ϊ��ָ���
		int num = 0;									//�����С����
		for(String ss:s)								//��������
		{
			num = 0;									//������
			String[] temp = ss.split("<#>");			//���ַ�����"<#>"Ϊ��ָ���
			String[] midd = new String[temp.length];	//������ʱ����
			for(String a:temp)							//��������
			{
				midd[num++] = a;
			}	
			list.add(midd);								//���ַ��������б�
		}
	    return list;									//�����б�
	}
	//���ַ���ת��������
	public static String[] StrToArray(String info)
	{
		int num = 0;							//�����С����
		String[] first = info.split("\\|");		//���ַ����ԡ�|��Ϊ��ָ���
		for(int i=0;i<first.length;i++)			//�����ַ�������
		{
			String[] temp1 = first[i].split("<#>");	//���ַ�����"<#>"�ָ���
			num+=temp1.length;						
		}
		String[] temp2=new String[num];				//������ʱ����
		num=0;										//����������
		for(String second:first)					//��������
		{
			String[] temp3=second.split("<#>");		//���ַ�����"<#>"�ָ���
			for(String third:temp3)					//��������
			{
			    temp2[num]=third;					//����ʱ���鸳ֵ
			    num++;								//��ʱ������
			}
		}
		return temp2;								//������ʱ����
	}
	//��Listת�����ַ���
	public static String ListToStr(List<String[]> list)
	{
		String mess="";									//�����ַ�������
		List<String[]> ls=new ArrayList<String[]>();	//����һ���µ��б�
		ls=list;										//���б�ֵ
		for(int i=0;i<ls.size();i++)					//�����б�
		{	
			String[] ss=ls.get(i);						//���б��ֵ�����ַ���
			for(String s:ss)							//�����ַ���
			{
			    mess+=s+"<#>";							//�����ַ���
			}
			mess+="|";									//�ַ���ĩβ��"|"
		}
		return mess;									//�����ַ���
	}	
}
