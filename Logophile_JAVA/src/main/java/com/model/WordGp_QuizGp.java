package com.model;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
public class WordGp_QuizGp extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="wordGp_QuizGpId_native"
    )
    @GenericGenerator(
        name = "wordGp_QuizGpId_native", 
        strategy = "native"
    )
    Long wordGp_QuizGpId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    protected Date createDate;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Word wordGpId; // FK
	
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
	private WordGp quizGpId; // FK
	
    public Long getWordGp_QuizGpId() {
		return wordGp_QuizGpId;
	}

	public void setWordGp_QuizGpId(Long wordGp_QuizGpId) {
		this.wordGp_QuizGpId = wordGp_QuizGpId;
	}

	public Word getWordGpId() {
		return wordGpId;
	}

	public void setWordGpId(Word wordGpId) {
		this.wordGpId = wordGpId;
	}

	public WordGp getQuizGpId() {
		return quizGpId;
	}

	public void setQuizGpId(WordGp quizGpId) {
		this.quizGpId = quizGpId;
	}

	public WordGp_QuizGp(Date createDate) {
        this.createDate = createDate;
    }

    public WordGp_QuizGp() {
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
