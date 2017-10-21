package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.JsonObject;
import com.model.pic.Pic;
import com.model.pic.PicRepository;
import com.util.Util;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;

@Controller
public class MessageBrokerController {
	
    @Autowired
    PicRepository picRepository;
	
	private MessageBrokerUtil utilWebOSocketMsgBroker;
	
    @Autowired
    public MessageBrokerController(MessageBrokerUtil utilWebOSocketMsgBroker) {
    	System.out.println("MessageBrokerController() called");
        this.utilWebOSocketMsgBroker = utilWebOSocketMsgBroker;
    }
    
    @MessageMapping("/triggerInit")
    @SendTo(MessageBrokerUtil.TOPIC + MessageBrokerUtil.CHANNEL_init)
    public String init(String aMsg) throws Exception {
    	Util.getConsoleLogger().info("triggerInit starts");
    	Util.getConsoleLogger().info("triggerInit input aMsg: " + aMsg);
    	
        // 通知圖片紀錄
        this.utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_fileUploaded, FileUploadUtil.lastPic);
        
        // 通知評論紀錄
        this.utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_ratingHistory, FileUploadUtil.lastPic);
        
        Util.getConsoleLogger().info("triggerInit ends");
        return "";
    }
}
