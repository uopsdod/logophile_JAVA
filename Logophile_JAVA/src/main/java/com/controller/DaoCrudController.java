package com.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.model.Sql2oDao;
import com.model.Sql2oDaoUtil;
import com.util.Util;

@RestController
public class DaoCrudController {
	
    @Autowired
    Sql2oDao sql2oDao;
    
    @Autowired
    Sql2o sql2o;
	
	// great example to follow: GET_ALL, GET, POST, PUT, DELTE_ALL, DELTE
	// ref: http://websystique.com/spring-boot/spring-boot-rest-api-example/
	@RequestMapping(value = "/crud/{beanName}", method = RequestMethod.GET)
	public String select(@PathVariable("beanName") String beanName
						,@RequestParam Map<String, String> formParams) {
		Util.getConsoleLogger().info("select starts");
		Util.getConsoleLogger().info("select input beanName: " + beanName);
		Util.getConsoleLogger().info("select input formParams: " + formParams);

		JSONArray resultJSONArray = new JSONArray();

		/** get bean obj from formParams **/
		Object formParamsObj = convertObjToBean(beanName, formParams);

		/** execute sql findAll command **/
		List<Object> findAll = sql2oDao.findAll(formParamsObj);

		/** convert bean list to jsonArray **/
		resultJSONArray = new JSONArray(findAll);

		Util.getConsoleLogger().info("select input ends");
		return resultJSONArray.toString();
	}
	
	/** NOW **/
	

	/**
	 * 
	 * @param beanName
	 * @param formParams
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/crud/{beanName}", method = RequestMethod.POST)
	public String insert(@PathVariable("beanName") String beanName
						,@RequestParam Map<String, String> formParams){
		Util.getConsoleLogger().info("insert starts");
		Util.getConsoleLogger().info("insert input beanName: " + beanName);
		Util.getConsoleLogger().info("insert input formParams: " + formParams);

		JSONObject jsonObj = new JSONObject();

		/** get bean obj from formParams **/
		Object formParamsObj = convertObjToBean(beanName, formParams);

		/** execute sql findAll command **/
		String primaryKey = sql2oDao.insert(formParamsObj);

		/** 放入回傳值 **/
		try {
			jsonObj.put("primaryKey", primaryKey);
		} catch (JSONException e) {
			Util.getConsoleLogger().info(Util.getExceptionMsg(e));
			Util.getFileLogger().info(Util.getExceptionMsg(e));
		}

		Util.getConsoleLogger().info("insert ends");
		return jsonObj.toString();
	}
	
	
	/**
	 * 
	 * @param beanName
	 * @param formParams
	 * @return
	 */
	@RequestMapping(value = "/crud/{beanName}", method = RequestMethod.PUT)
	public String update(@PathVariable("beanName") String beanName
						,@RequestParam Map<String, String> formParams){
		Util.getConsoleLogger().info("update starts");
		Util.getConsoleLogger().info("update input beanName: " + beanName);
		Util.getConsoleLogger().info("update input formParams: " + formParams);

		JSONObject jsonObj = new JSONObject();

		/** get bean obj from formParams **/
		Object formParamsObj = convertObjToBean(beanName, formParams);

		/** execute sql findAll command **/
		int rows = sql2oDao.update(formParamsObj);
		
		/** 放入回傳值 **/
		try {
			jsonObj.put("updated_rows", rows);
		} catch (JSONException e) {
			Util.getConsoleLogger().info(Util.getExceptionMsg(e));
			Util.getFileLogger().info(Util.getExceptionMsg(e));
		}
		

		Util.getConsoleLogger().info("update ends");
		return jsonObj.toString();		
	}
	
	
	/**
	 * 
	 * @param beanName
	 * @param formParams
	 * @return
	 */
//	@RequestMapping(value = "/crud/{beanName}", method = RequestMethod.DELETE) // DELETE still cannot send body to server
	@RequestMapping(value = "/crud/delete/{beanName}", method = RequestMethod.POST) // 過度時期 
	public String delete(@PathVariable("beanName") String beanName
						,@RequestParam Map<String, String> formParams){
		Util.getConsoleLogger().info("delete starts");
		Util.getConsoleLogger().info("delete input beanName: " + beanName);
		Util.getConsoleLogger().info("delete input formParams: " + formParams);

		JSONObject jsonObj = new JSONObject();

		/** get bean obj from formParams **/
		Object formParamsObj = convertObjToBean(beanName, formParams);

		/** execute sql findAll command **/
		int rows = sql2oDao.delete(formParamsObj);
		
		/** 放入回傳值 **/
		try {
			jsonObj.put("deleted_rows", rows);
		} catch (JSONException e) {
			Util.getConsoleLogger().info(Util.getExceptionMsg(e));
			Util.getFileLogger().info(Util.getExceptionMsg(e));
		}
		

		Util.getConsoleLogger().info("delete ends");
		return jsonObj.toString();		
	}

	//	
//	private Object convertObjToBean(String beanName, MultivaluedMap<String, String> formParams){
//		Map<String,String> formParamsMap = Util.convertMultiToRegularMap(formParams);
//		return convertObjToBean(beanName, formParamsMap);
//	}
	
	
	@RequestMapping(value = "/crud/batch/{beanName}", method = RequestMethod.POST)
	public ResponseEntity<String> batchInsert(@PathVariable("beanName") String beanName
											,@RequestParam(value="beanList", required=true) String beanList
											){
		Util.getFileLogger().info("batchInsert input starts");
		Util.getFileLogger().info("batchInsert input beanName: " + beanName);
		Util.getFileLogger().info("batchInsert input beanList: " + beanList);
		Util.getConsoleLogger().info("batchInsert input starts");
		Util.getConsoleLogger().info("batchInsert input beanName: " + beanName);
		Util.getConsoleLogger().info("batchInsert input beanList: " + beanList);
		
		JSONObject jsonObj = new JSONObject();
		List<String> primaryKeyList = new ArrayList<>();
		
		JsonArray gJsonArray = Util.getGJsonArray(beanList);
		
		try(Connection con =  this.sql2o.beginTransaction()){
			for (JsonElement jsonElmt : gJsonArray) {
			    JsonObject paymentObj = jsonElmt.getAsJsonObject();
			    Type type = new TypeToken<Map<String, String>>(){}.getType();
			    Map<String, String> myMap = Util.getGson().fromJson(paymentObj, type);
			    
			    /** 拿取bean **/
				Object formParamsObj = convertObjToBean(beanName, myMap);
				
				/** 進行sql insert搜尋 **/
				String primaryKey = sql2oDao.insert(formParamsObj, con);
				primaryKeyList.add(primaryKey);
				
				/** 放入回傳值 **/
				jsonObj.put("primaryKeyList", primaryKeyList);
			}			
			con.commit();
		}catch(Exception e){
			Util.getConsoleLogger().info("batchInsert Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("batchInsert Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		
		Util.getFileLogger().info("batchInsert input ends");
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location)
							.contentType(MediaType.APPLICATION_JSON_UTF8) // specify we intend to return json format
							.body(jsonObj.toString())
							;
	}
	/** data輸入範例:  
	 * url : http://localhost:8089/crud/batch/Word
	 * Content-Type : application/x-www-form-urlencoded
	 * beanList:[{spell: "w1"},{spell: "w2"}]
	 */
		
	
	
	private Object convertObjToBean(String beanName, Map<String,String> formParamsMap){
		Object formParamsObj = null;
		try {
			/** 讀取Class檔案 **/
			Class<?> forName = Class.forName(Sql2oDaoUtil.BEAN_PKG + "." + beanName);
			/** 讀取Class檔案 (新版) 避免不必要的重複仔入calss檔**/
//			Class<?> forName = Util.getBeanClass(beanName);
			
			/** 抓取其generic type(類別本身型別) **/
			Type genericSuperclass = forName.getGenericSuperclass();
			Util.getFileLogger().info("convertObjByType genericSuperclass: " + genericSuperclass);
			ParameterizedType p = (ParameterizedType) genericSuperclass;
			Type type = p.getActualTypeArguments()[0];
			Util.getFileLogger().info("convertObjByType p.getActualTypeArguments()[0]: " + type);
			Util.getFileLogger().info("convertObjByType p.getClass(): " + p.getClass());
			Util.getFileLogger().info("convertObjByType beanName: " + beanName);
			
			/** 將參數map轉換成類別物件 **/
			JSONObject formParamsJsonObj = new JSONObject(formParamsMap);
			Util.getFileLogger().info("convertObjByType  formParamsJson.toString(): " + formParamsJsonObj.toString());
			formParamsObj = Util.getGson().fromJson(formParamsJsonObj.toString(), type);
			Util.getFileLogger().info("convertObjByType  formParamsObj: " + formParamsObj);
		}catch(Exception e) {
			Util.getConsoleLogger().info(Util.getExceptionMsg(e));
			Util.getFileLogger().info(Util.getExceptionMsg(e));
		}
		
		return formParamsObj;
	}
	
	

}
