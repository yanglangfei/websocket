package com.jucaipen.chat;  
import java.io.IOException;  
import java.nio.CharBuffer;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.Set;  
/**
 * @author Administrator
 *
 *  ����Ŀǰ���ߵ��û�������
 *  
 */
public class WebSocketMessageInboundPool {  
    //�������ӵ�MAP����  
    private static final Map<String,WebSocketMessageInbound > connections = new HashMap<String,WebSocketMessageInbound>();  
    //�����ӳ����������  
    public static void addMessageInbound(WebSocketMessageInbound inbound){  
        //�������  
        System.out.println(inbound.getUser() + " ����..");  
        connections.put(inbound.getUser(), inbound);  
    }  
      
    //��ȡ���е������û�  
    public static Set<String> getOnlineUser(){  
        return connections.keySet();  
    }  
      
    public static void removeMessageInbound(WebSocketMessageInbound inbound){  
        //�Ƴ�����  
        System.out.println(inbound.getUser() + " ����..");  
        connections.remove(inbound.getUser());  
    }  
      
    public static void sendMessageToUser(String user,String message){  
        try {  
            //���ض����û���������  
            System.out.println("������Ϣ��" + user + ": " + message);  
            WebSocketMessageInbound inbound = connections.get(user);  
            if(inbound != null){  
                inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    //�����е��û�������Ϣ  
    public static void sendMessage(String message){  
        try {  
            Set<String> keySet = connections.keySet();  
            for (String key : keySet) {  
                WebSocketMessageInbound inbound = connections.get(key);  
                if(inbound != null){  
                    System.out.println("������Ϣ�� " + key + ": " + message);  
                    inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("error:"+e.getMessage());
        }  
    }  
}  