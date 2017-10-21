package com.util;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.model.mem.Mem;

public class MessageBrokerUtil {
	
	
	
    private SimpMessagingTemplate template;

    @Autowired
    public MessageBrokerUtil(SimpMessagingTemplate aTemplate) {
        this.template = aTemplate;
    }
    
    public void sendMsgToTopicSubcriber(String aTopicName, String aText){
    	Util.getConsoleLogger().info("sendMsgToTopicSubcriber input aText: " + aText);
    	String dst = MessageBrokerUtil.TOPIC + "/" + aTopicName;
    	Util.getConsoleLogger().info("sendMsgToTopicSubcriber dst: " + dst);
    	this.template.convertAndSend(dst, aText);
    }
    
    public void sendJsonToTopicSubcriber(String aTopicName, Object aObj){
    	Util.getConsoleLogger().info("sendMsgToTopicSubcriber input aObj: " + aObj);
    	String dst = MessageBrokerUtil.TOPIC + "/" + aTopicName;
    	Util.getConsoleLogger().info("sendMsgToTopicSubcriber dst: " + dst);
//    	Mem mem = new Mem();
//    	mem.setMemAccount("accountTest");
    	this.template.convertAndSend(dst, aObj, Collections.singletonMap("content-type", "application/json;charset=UTF-8"));
    }    
	
	public static final String TOPIC = "/topic";
	public static final String DST_PREFIX = "/app";
	public static final String ENDPOINT = "/gs-guide-websocket";
	
	public static final String CHANNEL_ratingHistory = "/ratingHistory";
	public static final String CHANNEL_fileUploaded = "/fileUploaded";
	public static final String CHANNEL_init= "/init";
}
