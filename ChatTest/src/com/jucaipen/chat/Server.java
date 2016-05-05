package com.jucaipen.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server{
	private static Map<Integer, Socket> soMap=new HashMap<Integer, Socket>();
	
	private static ServerSocket s;
	public static void main(String[] args) {
		try {
			s = new ServerSocket(8989);
			while (true) {
				Socket socket=s.accept();
				DataInputStream dis=new DataInputStream(socket.getInputStream());
				DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
				soMap.put(socket.getPort(), socket);
				dos.writeUTF(socket.getPort()+":"+"连接成功了~~");
				dos.flush();
				String result=dis.readUTF();
				System.out.println("s:"+result);
			}
		} catch (IOException e) {
			System.out.println("errorIo:"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	class SendMsg extends Thread{
		
		@Override
		public void run() {
			
		}
		
	}
	
}
