package com.jucaipen.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 *  �����û����ߡ����ߡ�������Ϣ
 *
 */       
public class WebSocketMessageInbound extends MessageInbound {
	//��ǰ���ӵ��û�����  
    private final String user; 
    public WebSocketMessageInbound(String user) {
		this.user=user;
	}
    
    public String getUser() {
		return user;
	}
    
    @Override
    protected void onOpen(WsOutbound outbound) {
    	//�������ӵĴ������¼�  
    	JSONObject object=new JSONObject();
    	object.element("type", "user_join");
    	object.element("user", this.user);
    	//�����������û����͵�ǰ�û����ߵ���Ϣ  
    	WebSocketMessageInboundPool.sendMessage(object.toString());
    	object=new JSONObject();
    	object.element("type","get_online_user");
    	object.element("list", WebSocketMessageInboundPool.getOnlineUser());
    	//�����ӳ���ӵ�ǰ�����Ӷ���
    	WebSocketMessageInboundPool.addMessageInbound(this);
    	
    	//��ǰ���ӷ��͵�ǰ�����û����б�
    	WebSocketMessageInboundPool.sendMessageToUser(this.user,object.toString());
    }
    
    @Override
    protected void onClose(int status) {
    	 // �����ر��¼��������ӳ����Ƴ�����  
    	WebSocketMessageInboundPool.removeMessageInbound(this);
    	JSONObject result = new JSONObject();  
        result.element("type", "user_leave");  
        result.element("user", this.user);  
        //�������û����͵�ǰ�û��˳�����Ϣ  
        WebSocketMessageInboundPool.sendMessage(result.toString());
    }

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		//�����������û�������Ϣ 
		WebSocketMessageInboundPool.sendMessage(message.toString());

	}

}
