package com.model.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 給各個Entity繼承使用
 * @author sam
 *
 */
public class Common <T> {
    transient Class<T> clazz = (Class<T>) this.getClass();
//  protected ClassGenericReserver(Class<T> aClazz) {
//      this.clazz = aClazz;
//  }
//	private List<String> errorMsgs;
//	private String jwtStr;
//
//	public List<String> getErrorMsgs() {
//		return errorMsgs;
//	}
//	protected Common(){
//		this.errorMsgs = new ArrayList<>();
//	}
//	
//	public void addError(String error){
//		this.errorMsgs.add(error);
//	}
//	public String getJwtStr() {
//		return jwtStr;
//	}
//	public void setJwtStr(String jwtStr) {
//		this.jwtStr = jwtStr;
//	}
//	public void setErrorMsgs(List<String> errorMsgs) {
//		this.errorMsgs = errorMsgs;
//	}
//	

}
