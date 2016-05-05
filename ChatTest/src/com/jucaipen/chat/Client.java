package com.jucaipen.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("192.168.1.134", 8989);
			System.out.println(socket.getPort()+"---------connected");
			DataInputStream dis=new DataInputStream(socket.getInputStream());
			DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
			String result=dis.readUTF();
			System.out.println("c:"+result);
			dos.writeUTF("server:"+result);
			dos.flush();
		} catch (UnknownHostException e) {
			System.out.println("err1:"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error2:"+e.getMessage());
			e.printStackTrace();
		}
	}

}
