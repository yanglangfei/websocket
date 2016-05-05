package com.jucaipen.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 *  处理用户上线、下线、发送消息
 *
 */       
public class WebSocketMessageInbound extends MessageInbound {
	//当前连接的用户名称  
    private final String user; 
    public WebSocketMessageInbound(String user) {
		this.user=user;
	}
    
    public String getUser() {
		return user;
	}
    
    @Override
    protected void onOpen(WsOutbound outbound) {
    	//建立连接的触发的事件  
    	JSONObject object=new JSONObject();
    	object.element("type", "user_join");
    	object.element("user", this.user);
    	//向所有在线用户推送当前用户上线的消息  
    	WebSocketMessageInboundPool.sendMessage(object.toString());
    	object=new JSONObject();
    	object.element("type","get_online_user");
    	object.element("list", WebSocketMessageInboundPool.getOnlineUser());
    	//向连接池添加当前的连接对象
    	WebSocketMessageInboundPool.addMessageInbound(this);
    	
    	//向当前连接发送当前在线用户的列表
    	WebSocketMessageInboundPool.sendMessageToUser(this.user,object.toString());
    }
    
    @Override
    protected void onClose(int status) {
    	 // 触发关闭事件，在连接池中移除连接  
    	WebSocketMessageInboundPool.removeMessageInbound(this);
    	JSONObject result = new JSONObject();  
        result.element("type", "user_leave");  
        result.element("user", this.user);  
        //向在线用户发送当前用户退出的消息  
        WebSocketMessageInboundPool.sendMessage(result.toString());
    }

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		//向所有在线用户发送消息 
		WebSocketMessageInboundPool.sendMessage(message.toString());

	}

}
