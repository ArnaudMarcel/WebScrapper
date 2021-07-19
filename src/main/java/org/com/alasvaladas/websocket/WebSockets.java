/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.alasvaladas.websocket;

import java.io.IOException;

/**
 *
 * @author Arnaud
 */
public class WebSockets {
    
    @javax.websocket.server.ServerEndpoint(value = "/WebSockets")
    
    public static class My_ServerEndpoint {

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) throws IOException {
            System.out.println("onError: " + throwable.getMessage()); 
            if(!throwable.getLocalizedMessage().contains("JBrowserDriver")){
                System.out.println("ok");
                sendItems(session, null);
            }
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) {
            System.out.println("Message from JavaScript: " + message);
            if(message.contains("search_type")){
                itemRequest(session, message);
            }
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
        }
        
       private void itemRequest(javax.websocket.Session session, String request){
           System.out.println("Seeking this item..." + request);
           org.com.alasvaladas.java.Leclerc_items.extractRequestData(session, request);
       }
       
       public static void sendItems(javax.websocket.Session session, java.util.Set<org.com.alasvaladas.java.Item> items) throws java.io.IOException {
           com.google.gson.Gson json_response = new com.google.gson.Gson();
           session.getBasicRemote().sendText(json_response.toJson(items));
       }
    }
}
