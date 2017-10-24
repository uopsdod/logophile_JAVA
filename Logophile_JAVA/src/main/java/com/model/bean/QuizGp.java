package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.model.annotation.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class QuizGp extends Common<QuizGp>{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
	@PrimaryKey
	@Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="quizGpId_native"
    )
    @GenericGenerator(
        name = "quizGpId_native", 
        strategy = "native"
    )
    Long quizGpId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    private Date createDate;

	private String name;

    public QuizGp(Date createDate) {
        this.createDate = createDate;
    }

    public QuizGp() {
    }
    
	public Long getQuizGpId() {
		return quizGpId;
	}

	public void setQuizGpId(Long quizGpId) {
		this.quizGpId = quizGpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}
