package com.model;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.model.common.Common;
import com.model.rate.Rate;

import java.util.Date;
import java.util.List;

@Entity
public class WordGp extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="wordGpId_native"
    )
    @GenericGenerator(
        name = "wordGpId_native", 
        strategy = "native"
    )
    Long wordGpId;

	private String wordGpName;

    public WordGp(Date createDate) {
        this.createDate = createDate;
    }

    public WordGp() {
    }
    
	public Long getWordGpId() {
		return wordGpId;
	}

	public void setWordGpId(Long wordGpId) {
		this.wordGpId = wordGpId;
	}

	public String getWordGpName() {
		return wordGpName;
	}

	public void setWordGpName(String wordGpName) {
		this.wordGpName = wordGpName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}
