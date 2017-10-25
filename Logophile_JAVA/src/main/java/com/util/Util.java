package com.util;
 
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassMatchProcessor;

public class Util {
	
	private static Gson gson;
	private static Map<String, Class<?>> beanMap = new HashMap<>();
	private static Map<String, List<String>> beanFieldsMap = new HashMap<>();
	
	public Util(Gson aGson){
		Util.getFileLogger().info("Util() start");
		Util.gson = aGson;
		Util.getFileLogger().info("Util() end");
	}	
	
	public static String getNowDateTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat( Util.getSdfDateTimeFormat());
		String now = sdf.format(new java.util.Date());
		return now;
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
	public static JsonArray getGJsonArray(String aMsg) {
		JsonArray msgJson = null;
		JsonParser jsonParser = new JsonParser();
		JsonElement parse = jsonParser.parse(aMsg);
		if (parse.isJsonArray()){
			msgJson = parse.getAsJsonArray();
		}
		return msgJson;
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
    
	public static Map<String, Class<?>> getBeanMap() {
		return beanMap;
	}

	public static Map<String, List<String>> getBeanFieldsMap() {
		return beanFieldsMap;
	}

	public static String getExceptionMsg(Throwable e){
		String eMsg = null;
		try(StringWriter trace = new StringWriter();){
			e.printStackTrace(new PrintWriter(trace));
			eMsg = trace.toString();
		}catch(Exception exception){
			Util.getFileLogger().info("getExceptionMsg exception.getMessage(): " + exception.getMessage());
		}
	    return eMsg;
	}
	
	public static boolean containsCaseInsensitive(String s, List<String> l) {
		for (String string : l) {
			if (string.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
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

	public static void updateBeanMapByPkgName(String packageName){
		   Util.getConsoleLogger().info("scanPackageByName() start");
		    new FastClasspathScanner(packageName) 
		       .matchAllClasses(new ClassMatchProcessor() {

		        @Override
		        public void processMatch(Class<?> klass) {
		        	Util.getConsoleLogger().info("class.getSimpleName(): " + klass.getSimpleName());
		        	Util.getBeanMap().put(klass.getSimpleName(), klass);
		        }
		    }).scan();
		    Util.getConsoleLogger().info("scanPackageByName() end");
	}
	
	public static void updateBeanFieldsMapByPkgName(String packageName){
		Map<String, List<String>> beanFieldsMap = Util.getBeanFieldsMap();
		
		beanMap.entrySet().stream()
				.forEach(e -> {
					Util.getConsoleLogger().info("beanMap: ( " + e.getKey() + " , " + e.getValue() + ") ");
					Field[] fields = e.getValue().getDeclaredFields();
					Stream<Field> fieldStream = Arrays.stream(fields);
					List<String> fieldNameList = fieldStream.map(f->f.getName()).collect(Collectors.toList());
					beanFieldsMap.put(e.getKey(), fieldNameList);
					
					// debugging
//					for (Field f : fields){
//						f.setAccessible(true); // prevent from error of accessing "private" fields
//						
//						// 若此屬性被標記為不要放入sql指令中，則跳過
//						if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
//							System.out.println("findAll NotForDaoSql annotation is present");
//							continue;
//						}
//						
//						Util.getConsoleLogger().info("beanMap: f: " + f);
//						Util.getConsoleLogger().info("beanMap: f.getName(): " + f.getName());
//					}// end of for (Field f : fields)
					
				});
		
		/** check result **/
		Util.getConsoleLogger().info("beanFieldsMap: " + beanFieldsMap);
	}
	
	
}
