package com.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.util.Strings;
import org.sql2o.Connection;

import com.model.annotation.FieldNotForDaoSql;
import com.model.annotation.PrimaryKey;
import com.util.Util;

public class Sql2oDaoUtil {
	public final static String BEAN_PKG = "com.model.bean";
	
	public static <T> String getSelectWhereSqlNew(T aObj){
		StringBuilder sql_where = new StringBuilder();
		Field[] fields = aObj.getClass().getDeclaredFields();
		Util.getConsoleLogger().info("fields.length: " + fields.length);
		
		/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			
			// 若此屬性被標記為不要放入sql指令中，則跳過
			if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
				System.out.println("getSelectWhereSqlNew NotForDaoSql annotation is present");
				continue;
			}
			
			Util.getConsoleLogger().info("getSelectWhereSqlNew f.getName(): " + f.getName());
			Object fObj;
			try {
				fObj = f.get(aObj);
//				String fVal = "";
				
				/** 此段有可能可以去掉,若之後有非字串欄位的需求,可試試看 **/
//				if (fObj instanceof String){
//					fVal = String.class.cast(fObj);
//				}
				
				if (fObj != null){
					if (Strings.isEmpty(sql_where)){
						sql_where.append(" WHERE ");
					}else{
						sql_where.append(" AND ");
					}
					sql_where.append( f.getName() + " = :" + f.getName() );
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Util.getConsoleLogger().info("getSelectWhereSqlNew Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
				Util.getFileLogger().info("getSelectWhereSqlNew Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			}
		}// end of for (Field f : fields)
		Util.getFileLogger().info("getSelectWhereSqlNew sql_where: " + sql_where);		
		return sql_where.toString();
	}
	
	
	
	public static <T> String getSelectWhereSqlByMap(Map<String, String> aObj){
		StringBuilder sql_where = new StringBuilder();
		Field[] fields = aObj.getClass().getDeclaredFields();
		Util.getConsoleLogger().info("fields.length: " + fields.length);
		
		/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
		for (Entry<String, String> entry : aObj.entrySet()){
			if (Strings.isNotEmpty(entry.getValue())){
				if (Strings.isEmpty(sql_where)){
					sql_where.append(" WHERE ");
				}else{
					sql_where.append(" AND ");
				}
				sql_where.append( entry.getKey() + " = :" + entry.getKey() );
			}			
		}
		Util.getFileLogger().info("getContactDataSettingByTenantIDAndTypeID sql_where: " + sql_where);		
		return sql_where.toString();
	}
	
	
	public static List<Map<String, String>> convertMapObjToMapStr(List<Map<String,Object>> aFromMapList){
		List<Map<String, String>> resultMapList = new ArrayList<>();
		/** 將map轉換為Map<String,String> **/
		for (Map<String, Object> map : aFromMapList){
			Util.getConsoleLogger().info("map: " + map);
			Map<String, String> resultMap = new HashMap<>();
//			Map<String, Object> tmpMap = map;
			for (String key : map.keySet()){
//				Util.getConsoleLogger().info("key: "  + key);
				Object value = map.get(key);
//				Util.getConsoleLogger().info("value: "  + value);
				if (value == null) {
					resultMap.put(key, "");
				}else{
					resultMap.put(key, value.toString());
				}
			}// end of for
			resultMapList.add(resultMap);
		}// end of for 
		return resultMapList;
	}

	public static <T> String getInsertSqlNew(String aTableName, T aObj){
		StringBuilder sql_insert = new StringBuilder();
		//		"INSERT INTO " + aTableName + " ";
		StringBuilder sql_insert_col = new StringBuilder();
		//" ([Email] ,[Phone] ,[Name],[Message],[TenantID]) ";
		StringBuilder sql_insert_val = new StringBuilder();
		//" VALUES (:aEmail,:aPhone,:aName,:aMessage,:aTenantID)"; 
		
		Field[] fields = aObj.getClass().getDeclaredFields();
//		Util.getConsoleLogger().info("fields.length: " + fields.length);
		
		/** 動態生成WHERE語句(注意: 僅對String型態有效) **/
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			
			// 若此屬性被標記為不要放入sql指令中，則跳過
			if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
				System.out.println("findAll NotForDaoSql annotation is present");
				continue;
			}else if (f.isAnnotationPresent(PrimaryKey.class)) {
				System.out.println("insert PrimaryKey is skipped in insert method");
				continue;
			}			
			
//			Util.getConsoleLogger().info("f.getName(): " + f.getName());
			Object fObj;
			try {
				fObj = f.get(aObj);
				

				
				if (fObj != null){
//					Util.getConsoleLogger().info("fObj: " + fObj);
					if (sql_insert_col.length() > 0){
						sql_insert_col.append(",");
					}
					sql_insert_col.append(f.getName());
					
					if (sql_insert_val.length() > 0){
						sql_insert_val.append(",");
					}
					sql_insert_val.append(":" + f.getName());
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
				Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			}
		}// end of for (Field f : fields)
		
		sql_insert.append("INSERT INTO ").append(aTableName)
					.append(" (").append(sql_insert_col.toString()).append(") ")
					.append(" VALUES (").append(sql_insert_val.toString()).append(")");
		
		Util.getConsoleLogger().info("getInsertSql sql_insert: " + sql_insert);
		Util.getFileLogger().info("getInsertSql sql_insert: " + sql_insert);
		
		return sql_insert.toString();
	}

	public static <T> String getUpdateSetSqlNew(Connection aCon, String aTableName, T aObj, String... aPkAry){
		/** insert動態指令建立 **/
//		StringBuilder sql_update = new StringBuilder();
//				"INSERT INTO " + aTableName + " ";
		StringBuilder sql_update = new StringBuilder();
		StringBuilder sql_update_set = new StringBuilder();
		StringBuilder sql_update_where = new StringBuilder();
//		" ([Email] ,[Phone] ,[Name],[Message],[TenantID]) ";
//		StringBuilder sql_insert_val = new StringBuilder();
//		" VALUES (:aEmail,:aPhone,:aName,:aMessage,:aTenantID)";
		
		List<String> pkList = Arrays.asList(aPkAry);
		Util.getConsoleLogger().info("pkList: " + pkList);
		
		/*** 比對欄位名稱與userdata,若對到則進行此欄位的新增 ***/
		Field[] fields = aObj.getClass().getDeclaredFields();
		Util.getConsoleLogger().info("fields.length: " + fields.length);
		
		/** 動態生成SQL語句(注意: 僅對String型態有效) **/
		try {
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				Util.getConsoleLogger().info("f.getName(): " + f.getName());
				
//				// 若此屬性被標記為不要放入sql指令中，則跳過
//				if (f.isAnnotationPresent(PrimaryKey.class)) {
//					System.out.println("getUpdateSetSqlNew PrimaryKey present");
//					continue;
//				}
				
				Object fObj;
				fObj = f.get(aObj);
				
				Util.getConsoleLogger().info("fObj: " + fObj);
				
				/** 此段有可能可以去掉,若之後有非字串欄位的需求,可試試看 **/
				// 若非pk,則放入set語句中
				if (!Util.containsCaseInsensitive(f.getName(), pkList) && fObj != null){
					if (sql_update_set.length() > 0){
						sql_update_set.append(",");
					}
					sql_update_set.append(f.getName() + " = :" + f.getName());
				// 若為pk,則放入where語句中
				}else if (Util.containsCaseInsensitive(f.getName(), pkList) && fObj != null){
					Util.getConsoleLogger().info("f.getName(): " + f.getName() + " matched");
					if (sql_update_where.length() > 0){
						sql_update_where.append(" AND ");
					}
					sql_update_where.append(f.getName() + " = :" + f.getName());
				}
					
			}
			
			if (Strings.isBlank(sql_update_where.toString())){
				throw new RuntimeException("WHERE語句為空  請提供搜尋條件");
			}
			
			sql_update.append("UPDATE ").append(aTableName)
			.append(" SET ").append(sql_update_set)
			.append(" WHERE ").append(sql_update_where.toString());
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		
//		Util.getConsoleLogger().info("getInsertSql sql_update_where: " + sql_update_where);
		

//					.append(" WHERE ").append(aContactData_Setting.getUniqueKey() + " = " + "'" + pVal + "'");
		
		return sql_update.toString();
	}
	
	public static <T> String getDeleteSqlNew(Connection aCon, String aTableName, T aObj){
		/** insert動態指令建立 **/
		StringBuilder sql_delete = new StringBuilder();
		StringBuilder sql_delete_where = new StringBuilder();
		
		/** 看有那些欄位需要當作搜尋值 **/
		Field[] fields = aObj.getClass().getDeclaredFields();
		Util.getConsoleLogger().info("delete fields.length: " + fields.length);
		
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			if (f.isAnnotationPresent(PrimaryKey.class)) {
				System.out.println("delete PrimaryKey annotation is present");
				sql_delete_where.append(f.getName() + " = :" + f.getName());
			}
		}// end of for (Field f : fields)
		
		if (Strings.isBlank(sql_delete_where.toString())){
			throw new RuntimeException("WHERE語句為空  請提供搜尋條件");
		}
		
		sql_delete.append("DELETE FROM ")
				  .append(aTableName)
				  .append(" WHERE ").append(sql_delete_where.toString());
//		
//		/*** 比對欄位名稱與userdata,若對到則進行此欄位的新增 ***/
//		Field[] fields = aObj.getClass().getDeclaredFields();
//		Util.getConsoleLogger().info("fields.length: " + fields.length);
//		 
//		/** 動態生成SQL語句(注意: 僅對String型態有效) **/
//		try {
//			for (Field f : fields){
//				f.setAccessible(true); // prevent from error of accessing "private" fields
//				Util.getConsoleLogger().info("f.getName(): " + f.getName());
//				Object fObj;
//					fObj = f.get(aObj);
//					
//					Util.getConsoleLogger().info("fObj: " + fObj);
//					
//					/** 此段有可能可以去掉,若之後有非字串欄位的需求,可試試看 **/
//					// 若非pk,則放入set語句中
//					if ( fObj != null){
//						Util.getConsoleLogger().info("f.getName(): " + f.getName() + " matched");
//						if (sql_delete_where.length() > 0){
//							sql_delete_where.append(" AND ");
//						}
//						sql_delete_where.append(f.getName() + " = :" + f.getName());
//					}
//					
//			}
//			
//			if (Strings.isBlank(sql_delete_where.toString())){
//				throw new RuntimeException("WHERE語句為空  請提供搜尋條件");
//			}
//			
//			sql_delete.append("DELETE FROM ").append(aTableName)
//			.append(" WHERE ").append(sql_delete_where.toString());
//			
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
//		}
		
//		Util.getConsoleLogger().info("getInsertSql sql_update_where: " + sql_update_where);
		

//					.append(" WHERE ").append(aContactData_Setting.getUniqueKey() + " = " + "'" + pVal + "'");
		
		return sql_delete.toString();
	}

	


}
