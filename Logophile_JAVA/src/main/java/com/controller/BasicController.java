package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.gson.JsonObject;
import com.util.Util;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;

@Controller
public class BasicController {
    
    @Autowired
    public BasicController() {
    	System.out.println("BasicController() called");
    }
    
    @RequestMapping("/")
    public String mainPage(Map<String, String> model) {
    	model.put("message", "hello");
    	return "main_page";
    }
    
	@RequestMapping("/main_page")
	public String main_page(Map<String, String> model) {
		model.put("message", "hello");
		return "main_page";
	}
    
}