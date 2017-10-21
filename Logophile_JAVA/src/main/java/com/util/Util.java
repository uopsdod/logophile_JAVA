package com.util;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.pic.Pic;
import com.model.pic.PicRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Util {
	
	private static Gson gson;
	
	PicRepository picRepository;
	
	public Util(Gson aGson, PicRepository picRepository){
		Util.getFileLogger().info("Util() start");
		Util.gson = aGson;
		Util.getFileLogger().info("Util() end");
		this.picRepository = picRepository;
	}	
	
	public static String getSdfDateFormat(){
		return Attr.sdfDateFormat;
	}
	public static String getSdfTimeFormat(){
		return Attr.sdfTimeFormat;
	}
	public static String getSdfDateTimeFormat(){
		return Attr.sdfDateTimeFormat;
	}
	public static Map<String, String> getSystemParam() {
		return Attr.SystemParam;
	}
	public static void setSystemParam(Map<String, String> systemParam) {
		Attr.SystemParam = systemParam;
	}
	
	public static JsonObject getGJsonObject(String aMsg){
		JsonParser jsonParser = new JsonParser(); 
		JsonObject msgJson = jsonParser.parse(aMsg).getAsJsonObject();
		return msgJson;
	}
	public static String getGString(JsonObject aObj, String aKey){
		return (aObj.get(aKey) != null && !(aObj.get(aKey)instanceof JsonNull))?aObj.get(aKey).getAsString():null;
	}
	public static Logger getFileLogger(){
		return Attr.fileLogger;
	}
	public static Logger getConsoleLogger(){
		return Attr.consoleLogger;
	}
	public static Logger getStatusFileLogger(){
		return Attr.statusFileLogger;
	}
	public static Logger getPressureTestFileLogger(){
		return Attr.pressureTestFileLogger;
	}
	public static Gson getGson() {
		return gson;
	}
	public static void setGson(Gson gson) {
		Util.gson = gson;
	}

	private static class Attr {
		private static final String sdfDateFormat = "yyyy-MM-dd";
		private static final String sdfTimeFormat = "HH:mm:ss";
		private static final String sdfDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		private static Map<String,String> SystemParam = new HashMap<>();
		private static final Logger fileLogger = LogManager.getLogger("util.fileLogger");
		private static final Logger consoleLogger = LogManager.getLogger("util.consoleLogger");
		private static final Logger statusFileLogger = LogManager.getLogger("util.statusFileLogger");
		private static final Logger pressureTestFileLogger = LogManager.getLogger("util.pressureTestFileLogger");
		
	}
}
