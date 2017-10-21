package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
public class QuizTypeOneChoiceItem extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="quizTypeOneChoiceItemId_native"
    )
    @GenericGenerator(
        name = "quizTypeOneChoiceItemId_native", 
        strategy = "native"
    )
    Long quizTypeOneChoiceItemId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    private Date createDate;
    
    private String description; // 注意: desc為sql關鍵字，不可以使用
    private Boolean isTheAns;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private QuizTypeOneChoice quizTypeOneChoiceId; // FK

	public Long getquizTypeOneChoiceItemId() {
		return quizTypeOneChoiceItemId;
	}

	public void setquizTypeOneChoiceItemId(Long quizTypeOneChoiceItemId) {
		this.quizTypeOneChoiceItemId = quizTypeOneChoiceItemId;
	}


	public Boolean getIsTheAns() {
		return isTheAns;
	}

	public void setIsTheAns(Boolean isTheAns) {
		this.isTheAns = isTheAns;
	}


	public Long getQuizTypeOneChoiceItemId() {
		return quizTypeOneChoiceItemId;
	}

	public void setQuizTypeOneChoiceItemId(Long quizTypeOneChoiceItemId) {
		this.quizTypeOneChoiceItemId = quizTypeOneChoiceItemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public QuizTypeOneChoice getQuizTypeOneChoiceId() {
		return quizTypeOneChoiceId;
	}

	public void setQuizTypeOneChoiceId(QuizTypeOneChoice quizTypeOneChoiceId) {
		this.quizTypeOneChoiceId = quizTypeOneChoiceId;
	}

	public QuizTypeOneChoiceItem(Date createDate) {
        this.createDate = createDate;
    }

    public QuizTypeOneChoiceItem() {
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
