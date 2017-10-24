package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.annotation.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class Quiz extends Common<Quiz>{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
	@PrimaryKey
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
    private String type; // QuizOneChoice
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    private Date createDate;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Word wordId; // FK
	
	@JsonIgnore // to prevent infinite loop when parsing into json
	@ManyToOne(optional = false)
	private QuizGp quizGpId; // FK

    public Long getQuizId() {
		return quizId;
	}
    
	public QuizGp getQuizGpId() {
		return quizGpId;
	}

	public void setQuizGpId(QuizGp quizGpId) {
		this.quizGpId = quizGpId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
