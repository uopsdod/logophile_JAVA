package com.model;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
public class Quiz_QuizGp extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="quiz_QuizGpId_native"
    )
    @GenericGenerator(
        name = "quiz_QuizGpId_native", 
        strategy = "native"
    )
    Long quiz_QuizGpId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    protected Date createDate;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Word quizId; // FK
	
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
	private WordGp quizGpId; // FK

	public Long getQuiz_QuizGpId() {
		return quiz_QuizGpId;
	}

	public void setQuiz_QuizGpId(Long quiz_QuizGpId) {
		this.quiz_QuizGpId = quiz_QuizGpId;
	}

	public Word getQuizId() {
		return quizId;
	}

	public void setQuizId(Word quizId) {
		this.quizId = quizId;
	}

	public WordGp getQuizGpId() {
		return quizGpId;
	}

	public void setQuizGpId(WordGp quizGpId) {
		this.quizGpId = quizGpId;
	}

	public Quiz_QuizGp(Date createDate) {
        this.createDate = createDate;
    }

    public Quiz_QuizGp() {
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
