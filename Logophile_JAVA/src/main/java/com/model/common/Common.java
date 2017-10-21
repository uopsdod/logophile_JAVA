package com.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 給各個Entity繼承使用
 * @author sam
 *
 */
public class Common {
	private List<String> errorMsgs;
	private String jwtStr;
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    protected Date createDate;
    
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<String> getErrorMsgs() {
		return errorMsgs;
	}
	protected Common(){
		this.errorMsgs = new ArrayList<>();
	}
	
	public void addError(String error){
		this.errorMsgs.add(error);
	}
	public String getJwtStr() {
		return jwtStr;
	}
	public void setJwtStr(String jwtStr) {
		this.jwtStr = jwtStr;
	}
	public void setErrorMsgs(List<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	

}
