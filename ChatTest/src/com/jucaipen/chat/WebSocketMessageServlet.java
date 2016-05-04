package com.jucaipen.chat;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class WebSocketMessageServlet extends WebSocketServlet {
	
	private static final long serialVersionUID = 1L;  
    public static int ONLINE_USER_COUNT = 1;
    public int index;
    
    public String getUser(HttpServletRequest request){
    	try {
			request.setCharacterEncoding("UTF-8");
			 return (String) request.getParameter("user");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
       
    }  

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest request) {
		//初始化自定义的WebSocket连接对象  
		return new WebSocketMessageInbound(getUser(request));
	}
	
	

}
