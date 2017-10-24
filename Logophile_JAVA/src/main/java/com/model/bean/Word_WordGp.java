package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.annotation.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class Word_WordGp extends Common<Word_WordGp>{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
	@PrimaryKey
	@Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="word_WordGpId_native"
    )
    @GenericGenerator(
        name = "word_WordGpId_native", 
        strategy = "native"
    )
    Long word_WordGpId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    protected Date createDate;
    
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Word wordId; // FK
	
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
	private WordGp wordGpId; // FK
	
    public Word_WordGp(Date createDate) {
        this.createDate = createDate;
    }

    public Word_WordGp() {
    }
    
	public Long getWord_WordGpId() {
		return word_WordGpId;
	}

	public void setWord_WordGpId(Long word_WordGpId) {
		this.word_WordGpId = word_WordGpId;
	}

	public Word getWordId() {
		return wordId;
	}

	public void setWordId(Word wordId) {
		this.wordId = wordId;
	}

	public WordGp getWordGpId() {
		return wordGpId;
	}

	public void setWordGpId(WordGp wordGpId) {
		this.wordGpId = wordGpId;
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
