package com.vTigerCRM.genericLib;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpHostOs 
{
	public static void main(String[] args) throws Exception 
	{
		//current directory
		System.out.println(System.getProperty("user.dir"));
		
		//Host name
		InetAddress in = InetAddress.getLocalHost();
		
		System.out.println("IP -> "+in.getLocalHost().toString());
		System.out.println("HOST Name -> "+in.getHostName());
		
		//OS Name
		System.out.println("OS -> "+System.getProperty("os.name"));

	}
}
