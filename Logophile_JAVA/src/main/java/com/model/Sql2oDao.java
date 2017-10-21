package com.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.util.Strings;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import com.model.annotation.FieldNotForDaoSql;
import com.model.annotation.PrimaryKey;
import com.util.Util;

public class Sql2oDao {
	private Sql2o sql2o;
	
	public Sql2oDao(Sql2o aSql2o) {
		this.sql2o = aSql2o;
	}

	
	public <T> T findOne(T aObj){
		return findOne(aObj.getClass().getSimpleName(), aObj);
	}
	
	public <T> T findOne(String aTableName, T aObj){
		try(Connection con = sql2o.open()){
			return findOne(con, aTableName, aObj);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
		return null;
	}
	
	public <T> T findOne(Connection aCon, String aTableName, T aObj){
		T result = null;
		
		try{
			List<T> resultList = this.findAll(aCon, aTableName, aObj);
			if (resultList.size() == 0){
				throw new RuntimeException(aTableName + " findOne - 無資料");
			}else if (resultList.size() == 1){
				result = resultList.get(0);
			}else if (resultList.size() > 1){
				throw new RuntimeException(aTableName + " findOne - 資料數大於一，請以Primary key作為搜尋條件");
			}
		}catch(Exception e){
		    Util.getConsoleLogger().info("findOne Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("findOne Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		return result;
	}
	
	public <T> List<T> findAll(T aObj){
		return findAll(aObj.getClass().getSimpleName(), aObj);
	}

	
	public <T> List<T> findAll(String aTableName, T aObj){
		try(Connection con = sql2o.open()){
			return findAll(con, aTableName, aObj);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
		return null;
	}
	
	public <T> List<T> findAll(Connection aCon, String aTableName, T aObj){
		List<T> resultList = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM " + aTableName);
							
		String sql_where = Sql2oDaoUtil.getSelectWhereSqlNew(aObj);
		if (Strings.isNotBlank(sql_where)){
			sql.append(sql_where);
		}
		Util.getConsoleLogger().info("findAll sql: " + sql);
		Util.getFileLogger().info("findAll sql: " + sql);
		
		try{
			Query query = aCon.createQuery(sql.toString());
			
			/** 看有那些欄位需要塞值進去 **/
			if (Strings.isNotBlank(sql_where)){
				Field[] fields = aObj.getClass().getDeclaredFields();
				for (Field f : fields){
					f.setAccessible(true); // prevent from error of accessing "private" fields
					
					// 若此屬性被標記為不要放入sql指令中，則跳過
					if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
						System.out.println("findAll NotForDaoSql annotation is present");
						continue;
					}
					
					Util.getConsoleLogger().info("findAll f.getName(): " + f.getName());
					Object fObj;
					fObj = f.get(aObj);
					if (fObj != null){
						query.addParameter(f.getName(), fObj);
					}
				}// end of for (Field f : fields)
			}
			
			resultList = (List<T>) query.executeAndFetch(aObj.getClass());
		}catch (IllegalArgumentException | IllegalAccessException e) {
		    Util.getConsoleLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		return resultList;
	}
	
	public List<Map<String, String>> findAllByMap(String aTableName, Map<String, String> aMap){
		try(Connection con = sql2o.open()){
			return findAllByMap(con, aTableName, aMap);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
		return null;
	}
	
	public List<Map<String, String>> findAllByMap(Connection aCon, String aTableName, Map<String, String> aMap){
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<Map<String, String>> resultStrList = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append(" FROM " + aTableName);
							
		String sql_where = Sql2oDaoUtil.getSelectWhereSqlByMap(aMap);
		if (Strings.isNotBlank(sql_where)){
			sql.append(sql_where);
		}
		Util.getConsoleLogger().info("findAllByMap sql: " + sql);
		Util.getFileLogger().info("findAllByMap sql: " + sql);
		
		try{
			Query query = aCon.createQuery(sql.toString());
			
			/** 看有那些欄位需要塞值進去 **/
			for (Entry<String, String> entry : aMap.entrySet()){
				if (Strings.isNotEmpty(entry.getValue())){
					Util.getConsoleLogger().info("findAllByMap entry.getKey(): " + entry.getKey());
					Util.getConsoleLogger().info("findAllByMap entry.getValue(): " + entry.getValue());
					query.addParameter(entry.getKey(), entry.getValue());
				}			
			}
			resultList = query.executeAndFetchTable().asList();
//			
//			Table executeAndFetchTable = con.createQuery(sql)
//					.executeAndFetchTable();
//			List<Map<String, Object>> resultList = executeAndFetchTable.asList();
			Util.getConsoleLogger().info("findAllByMap resultList: " + resultList);
			
			/** 將map轉換為Map<String,String> **/
			resultStrList = Sql2oDaoUtil.convertMapObjToMapStr(resultList);
			Util.getConsoleLogger().info("findAllByMap resultStrList: " + resultStrList);
			
			
		}catch (Exception e) {
		    Util.getConsoleLogger().info("findAllByMap Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("findAllByMap Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		return resultStrList;		
	}
	
	public <T> String insert(T aObj){
		return insert(aObj.getClass().getSimpleName(), aObj);
	}
	
	public <T> String insert(T aObj, Connection aCon){
		return insert(aCon, aObj.getClass().getSimpleName(), aObj);
	}
	
	public <T> String insert(String aTableName, T aObj){
		try(Connection con = sql2o.open()){
			return insert(con, aTableName, aObj);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
		return null;
	}
	
	public <T> String insert(Connection aCon, String aTableName, T aObj){
		Object primaryKey = null;
		String sql = Sql2oDaoUtil.getInsertSqlNew(aTableName, aObj);
//	    Util.getConsoleLogger().info("insert sql: " + sql);
//		Util.getFileLogger().info("insert sql: " + sql);
		
		try {
			boolean isReturnGeneratedKeys = true;
			Query query = aCon.createQuery(sql.toString(), isReturnGeneratedKeys);
			
			Field[] fields = aObj.getClass().getDeclaredFields();
//			Util.getConsoleLogger().info("fields.length: " + fields.length);
		
			/** 看有那些欄位需要塞值進去  **/
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				
				// 若此屬性被標記為不要放入sql指令中，則跳過
				if (f.isAnnotationPresent(FieldNotForDaoSql.class)) {
					System.out.println("insert NotForDaoSql annotation is present");
					continue;
				}else if (f.isAnnotationPresent(PrimaryKey.class)) {
					System.out.println("insert PrimaryKey is skipped in insert method");
					continue;
				}
				
//				Util.getConsoleLogger().info("insert f.getName(): " + f.getName());
				Object fObj = f.get(aObj);
//				Util.getConsoleLogger().info("insert fObj: " + fObj);
				
				if (fObj != null){
					query.addParameter(f.getName(), fObj);
				}
			}// end of for (Field f : fields)
			
			primaryKey = query.executeUpdate().getKey(); // 若此table無primary key,可能為空值
		}catch (IllegalArgumentException | IllegalAccessException e) {
		    Util.getConsoleLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		return (primaryKey != null)?primaryKey.toString():null;
	}
	
	/**
	 * 此方法會去抓取@PrimaryKey，判斷其PK為那些欄位，以作為搜尋欄位
	 * @param aObj
	 */
	public <T> void update(T aObj){
		try(Connection con = sql2o.open()){
			update(con, aObj);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
	}
	public <T> void update(Connection aCon, T aObj){
		List<String> searchList = new ArrayList<>();
		/** 看有那些欄位需要當作搜尋值 **/
		Field[] fields = aObj.getClass().getDeclaredFields();
		Util.getConsoleLogger().info("update fields.length: " + fields.length);
		
		for (Field f : fields){
			f.setAccessible(true); // prevent from error of accessing "private" fields
			// 若此屬性被標記為不要放入sql指令中，則跳過
			if (f.isAnnotationPresent(PrimaryKey.class)) {
				System.out.println("update PrimaryKey annotation is present");
				searchList.add(f.getName());
			}
		}// end of for (Field f : fields)
		String[] pkAry = searchList.toArray(new String[0]);
		update(aCon, aObj.getClass().getSimpleName(), aObj, pkAry);		
	}
	
	/** 過度方法 **/
	public <T> void update(String aTableName, T aObj, String... aPkAry){
		try(Connection con = sql2o.open()){
			update(con, aTableName, aObj, aPkAry);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
	}
	
	public <T> void update(Connection aCon, String aTableName, T aObj, String... aPkAry){
		Object primaryKey = null;
		String sql_update = Sql2oDaoUtil.getUpdateSetSqlNew(aCon, aTableName, aObj, aPkAry);
	    Util.getConsoleLogger().info("update sql_update: " + sql_update);
		Util.getFileLogger().info("update sql_update: " + sql_update);
		Util.getConsoleLogger().info("update aPkAry.length: " + aPkAry.length);
		
		List<String> pkList = Arrays.asList(aPkAry);
		
		try {
			boolean isReturnGeneratedKeys = true;
			Query query = aCon.createQuery(sql_update.toString(), isReturnGeneratedKeys);
		
			/** 看有那些欄位需要塞值進去 **/
			Field[] fields = aObj.getClass().getDeclaredFields();
			Util.getConsoleLogger().info("update fields.length: " + fields.length);
			
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				Util.getConsoleLogger().info("f.getName(): " + f.getName());
				
				Object fObj;
				fObj = f.get(aObj);
				
				Util.getConsoleLogger().info("fObj: " + fObj);
				
				// 若非pk,則放入set語句中
				if (!Util.containsCaseInsensitive(f.getName(), pkList) && fObj != null){
					query.addParameter(f.getName(), fObj);
				// 若為pk,則放入where語句中
				}else if (Util.containsCaseInsensitive(f.getName(), pkList) && fObj != null){
					query.addParameter(f.getName(), fObj);
				}
			}
			
			query.executeUpdate();
		}catch (IllegalArgumentException | IllegalAccessException e) {
		    Util.getConsoleLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("findAll Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
	}
	
	
	public <T> void delete(T aObj){
		delete(aObj.getClass().getSimpleName(), aObj);
	}
	
	public <T> void delete(String aTableName, T aObj){
		try(Connection con = sql2o.open()){
			delete(con, aTableName, aObj);
		}catch(Exception e){
			Util.getConsoleLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));			
		}
	}
	
	public <T> void delete(Connection aCon, String aTableName, T aObj){
		/** 拿到delete sql語句 **/
		String sql_delete = Sql2oDaoUtil.getDeleteSqlNew(aCon, aTableName, aObj);
	    Util.getConsoleLogger().info("delete sql_delete: " + sql_delete);
		Util.getFileLogger().info("delete sql_delete: " + sql_delete);
		try {
			Query query = aCon.createQuery(sql_delete.toString());
		
			/** 看有那些欄位需要塞值進去 **/
			Field[] fields = aObj.getClass().getDeclaredFields();
			Util.getConsoleLogger().info("delete fields.length: " + fields.length);
			
			for (Field f : fields){
				f.setAccessible(true); // prevent from error of accessing "private" fields
				Util.getConsoleLogger().info("delete f.getName(): " + f.getName());
				if (f.isAnnotationPresent(PrimaryKey.class)) {
					Object fObj = f.get(aObj);
					Util.getConsoleLogger().info("delete fObj: " + fObj);
					if ( fObj != null){
						query.addParameter(f.getName(), fObj);
					}					
				}
			}// end of for (Field f : fields)
			
			query.executeUpdate();
		}catch (IllegalArgumentException | IllegalAccessException e) {
		    Util.getConsoleLogger().info("delete Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("delete Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}catch (Exception e) {
		    Util.getConsoleLogger().info("delete Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
			Util.getFileLogger().info("delete Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
	}
	
	
	public Sql2o getSql2o() {
		return sql2o;
	}

	public void setSql2o(Sql2o sql2o) {
		this.sql2o = sql2o;
	}
	
	
	
}
