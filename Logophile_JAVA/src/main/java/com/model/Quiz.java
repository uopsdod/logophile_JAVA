package com.model;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
public class Quiz extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="quizId_native"
    )
    @GenericGenerator(
        name = "quizId_native", 
        strategy = "native"
    )
    Long quizId;
    private String quizType; // QuizOneChoice
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    protected Date createDate;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Word wordId; // FK

    public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getQuizType() {
		return quizType;
	}

	public void setQuizType(String quizType) {
		this.quizType = quizType;
	}

	public Word getWordId() {
		return wordId;
	}

	public void setWordId(Word wordId) {
		this.wordId = wordId;
	}

	public Quiz(Date createDate) {
        this.createDate = createDate;
    }

    public Quiz() {
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
