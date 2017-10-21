package com.model;

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
    protected Date createDate;
    
	private String wordSpell; // 單字
	private String wordSoundSpell; // 英標
//	private byte[] wordSound; // 發音
	
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratePic")
//    private List<Rate> wordDefIdList;	// 英文定義ID
	
//    private List<Rate> wordDefIdList_zh;	// 中文定義ID
//    private List<Rate> wordExIdList; // 例句ID
//    private List<Rate> wordSynIdList;	// 同義詞ID
//    private List<Rate> wordAsynIdList; // 反義詞ID
//    private List<Rate> wordHelperPicIdList; // 圖片ID	
//    private List<Rate> wordHelperSoundIdList;	// 聲音ID 

    public Word(Date createDate) {
        this.createDate = createDate;
    }

    public Word() {
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
