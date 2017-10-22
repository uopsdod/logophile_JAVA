package com.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Sql2oDao;
import com.model.Sql2oDaoUtil;
import com.util.Util;

@RestController
public class DaoCrudController {
	
    @Autowired
    Sql2oDao sql2oDao;
	
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
	
	
//	/**
//	 * 
//	 * @param beanName
//	 * @param formParams
//	 * @return
//	 */
//	@POST
//	@Consumes("application/x-www-form-urlencoded")
//	@Path("/batchInsertResource/{beanName}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response batchInsertResource(@PathParam("beanName") String beanName, @FormParam("beanList") String beanList) {
//		Util.getFileLogger().info("batchInsertResource input starts");
//		Util.getFileLogger().info("batchInsertResource input beanName: " + beanName);
//		Util.getFileLogger().info("batchInsertResource input beanList: " + beanList);
//		Util.getConsoleLogger().info("batchInsertResource input starts");
//		Util.getConsoleLogger().info("batchInsertResource input beanName: " + beanName);
//		Util.getConsoleLogger().info("batchInsertResource input beanList: " + beanList);
//		
//		JSONObject jsonObj = new JSONObject();
//		List<String> primaryKeyList = new ArrayList<>();
//		
//		JsonArray gJsonArray = Util.getGJsonArray(beanList);
//		
//		try(Connection con = Info360Dao.sql2oForAll.beginTransaction()){
//			for (JsonElement jsonElmt : gJsonArray) {
//			    JsonObject paymentObj = jsonElmt.getAsJsonObject();
//			    Type type = new TypeToken<Map<String, String>>(){}.getType();
//			    Map<String, String> myMap = Util.getGson().fromJson(paymentObj, type);
//			    
//			    /** 拿取bean **/
//				Object formParamsObj = convertObjToBean(beanName, myMap);
//				
//				/** 進行sql insert搜尋 **/
//				String primaryKey = Sql2oDao.insert(formParamsObj, con);
//				primaryKeyList.add(primaryKey);
//				
//				/** 放入回傳值 **/
//				jsonObj.put("primaryKeyList", primaryKeyList);
//			}			
//			con.commit();
//		}catch(Exception e){
//			Util.getConsoleLogger().info("batchInsertResource Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//			Util.getFileLogger().info("batchInsertResource Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//		}
//		
//		Util.getFileLogger().info("batchInsertResource input ends");
//		return Response
//				.status(200)
//				.entity(jsonObj.toString())
//				.header("Access-Control-Allow-Origin", "*")
//				.header("Access-Control-Allow-Methods",
//						"POST, GET, PUT, UPDATE, OPTIONS")
//						.header("Access-Control-Allow-Headers",
//								"Content-Type, Accept, X-Requested-With").build();
//	}
//	/** data輸入範例:  
//	 * beanList:[{callID: "#1209oskvajoasi", senderID:"100", recevierID:"wad12saocijawd", action:"login", tenantID:"9"},{callID: "9doiwj12ds", senderID:"200", recevierID:"12oascij209", action:"findagent", tenantID:"10"}]
//	 */
//	
//	
//	/**
//	 * 
//	 * @param beanName
//	 * @param formParams
//	 * @return
//	 */
//	@POST
//	@Consumes("application/x-www-form-urlencoded")
//	@Path("/updateResource/{beanName}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateResourceByBean(@PathParam("beanName") String beanName, MultivaluedMap<String, String> formParams) {
//		Util.getFileLogger().info("updateResource input starts");
//		Util.getFileLogger().info("updateResource input beanName: " + beanName);
//		Util.getFileLogger().info("updateResource input formParams: " + formParams);
//		
//		JSONObject jsonObj = new JSONObject();
//		
//		/** 拿取bean **/
//		Object formParamsObj = convertObjToBean(beanName, formParams);
//		
//		/** 進行sql update **/
//		Sql2oDao.update(formParamsObj);
//		
//		Util.getFileLogger().info("updateResource input ends");
//		return Response
//				.status(200)
//				.entity(jsonObj.toString())
//				.header("Access-Control-Allow-Origin", "*")
//				.header("Access-Control-Allow-Methods",
//						"POST, GET, PUT, UPDATE, OPTIONS")
//						.header("Access-Control-Allow-Headers",
//								"Content-Type, Accept, X-Requested-With").build();
//	}
//	
//	/**
//	 * 
//	 * @param beanName
//	 * @param formParams
//	 * @return
//	 */
//	@POST
//	@Consumes("application/x-www-form-urlencoded")
//	@Path("/deleteResource/{beanName}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteResourceByBean(@PathParam("beanName") String beanName, MultivaluedMap<String, String> formParams) {
//		Util.getFileLogger().info("deleteResource input starts");
//		Util.getFileLogger().info("deleteResource input beanName: " + beanName);
//		Util.getFileLogger().info("deleteResource input formParams: " + formParams);
//		
//		JSONObject jsonObj = new JSONObject();
//		
//		/** 拿取bean **/
//		Object formParamsObj = convertObjToBean(beanName, formParams);
//		
//		/** 進行sql delete **/
//		Sql2oDao.delete(formParamsObj);
//		
//		Util.getFileLogger().info("deleteResource input ends");
//		return Response
//				.status(200)
//				.entity(jsonObj.toString())
//				.header("Access-Control-Allow-Origin", "*")
//				.header("Access-Control-Allow-Methods",
//						"POST, GET, PUT, UPDATE, OPTIONS")
//						.header("Access-Control-Allow-Headers",
//								"Content-Type, Accept, X-Requested-With").build();
//		
//	}
//	
//	private Object convertObjToBean(String beanName, MultivaluedMap<String, String> formParams){
//		Map<String,String> formParamsMap = Util.convertMultiToRegularMap(formParams);
//		return convertObjToBean(beanName, formParamsMap);
//	}
	
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
