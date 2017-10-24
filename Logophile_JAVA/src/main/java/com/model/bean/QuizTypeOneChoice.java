package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.annotation.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class QuizTypeOneChoice extends Common<QuizTypeOneChoice>{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
	@PrimaryKey
	@Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="quizTypeOneChoiceId_native"
    )
    @GenericGenerator(
        name = "quizTypeOneChoiceId_native", 
        strategy = "native"
    )
    Long quizTypeOneChoiceId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    private Date createDate;
    
    private String description;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Quiz quizId; // FK
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quizTypeOneChoiceId")
    private List<QuizTypeOneChoiceItem> oneChoiceList;

    public Long getquizTypeOneChoiceId() {
		return quizTypeOneChoiceId;
	}

	public void setquizTypeOneChoiceId(Long quizTypeOneChoiceId) {
		this.quizTypeOneChoiceId = quizTypeOneChoiceId;
	}


	public Long getQuizTypeOneChoiceId() {
		return quizTypeOneChoiceId;
	}

	public void setQuizTypeOneChoiceId(Long quizTypeOneChoiceId) {
		this.quizTypeOneChoiceId = quizTypeOneChoiceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuizTypeOneChoiceItem> getOneChoiceList() {
		return oneChoiceList;
	}

	public void setOneChoiceList(List<QuizTypeOneChoiceItem> oneChoiceList) {
		this.oneChoiceList = oneChoiceList;
	}

	public Quiz getQuizId() {
		return quizId;
	}

	public void setQuizId(Quiz quizId) {
		this.quizId = quizId;
	}

	public QuizTypeOneChoice(Date createDate) {
        this.createDate = createDate;
    }

    public QuizTypeOneChoice() {
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
