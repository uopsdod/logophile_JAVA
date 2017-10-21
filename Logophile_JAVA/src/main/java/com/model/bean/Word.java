package com.model.bean;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;


import java.util.Date;
import java.util.List;

@Entity
public class Word extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="wordId_native"
    )
    @GenericGenerator(
        name = "wordId_native", 
        strategy = "native"
    )
    Long wordId;
    
    //@Temporal(TemporalType.DATE)
    //@Column(name = "CREATED_DATE")
    private Date createDate;
    
	private String spell; // 單字
	private String soundSpell; // 英標
//	private byte[] sound; // 發音
	
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratePic")
//    private List<Rate> wordDefIdList;	// 英文定義ID
	
//    private List<Rate> defIdList_zh;	// 中文定義ID
//    private List<Rate> exIdList; // 例句ID
//    private List<Rate> synIdList;	// 同義詞ID
//    private List<Rate> synIdList; // 反義詞ID
//    private List<Rate> helperPicIdList; // 圖片ID	
//    private List<Rate> helperSoundIdList;	// 聲音ID 
	
    public Word(Date createDate) {
        this.createDate = createDate;
    }

    public Long getWordId() {
		return wordId;
	}

	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSoundSpell() {
		return soundSpell;
	}

	public void setSoundSpell(String soundSpell) {
		this.soundSpell = soundSpell;
	}

	public Word() {
		this.createDate = new java.util.Date();
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
