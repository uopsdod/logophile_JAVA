package com.model.pic;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.model.common.Common;
import com.model.rate.Rate;

@Entity
public class Pic extends Common{
	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="picId_native"
    )
    @GenericGenerator(
        name = "picId_native", 
        strategy = "native"
    )
    Long picId;
    
	private long rateNum;
	private long rateResult;
	
	@JsonIgnore // 不要被轉換成json
    @Lob
    private byte[] picFile;
//	private byte[] picFile;
    //@Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    Date date;
    
    @JsonInclude() // 但仍要可以被轉換成json
    public String picUrl;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratePic",fetch = FetchType.EAGER)
//    public Set<Rate> rateList;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratePic")
    private List<Rate> rateList;

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public long getRateNum() {
		return rateNum;
	}

	public void setRateNum(long rateNum) {
		this.rateNum = rateNum;
	}

	public long getRateResult() {
		return rateResult;
	}

	public void setRateResult(long rateResult) {
		this.rateResult = rateResult;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte[] getPicFile() {
		return picFile;
	}

	public void setPicFile(byte[] picFile) {
		this.picFile = picFile;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}   
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}
	
}
