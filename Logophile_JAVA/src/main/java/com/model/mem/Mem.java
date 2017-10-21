package com.model.mem;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.model.common.Common;

import java.util.Date;

@Entity
public class Mem extends Common{

	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="memId_native"
    )
    @GenericGenerator(
        name = "memId_native", 
        strategy = "native"
    )
    Long memId;

	private String memName;
	private String memAccount;
	private String memPwd;

    //@Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    Date date;

    public Mem(String memName, String memAccount, String memPwd, Date date) {
        this.memName = memName;
        this.memAccount = memAccount;
        this.memPwd = memPwd;
        this.date = date;
    }

    public Mem() {
    }

	public Long getMemId() {
		return memId;
	}

	public void setMemId(Long memId) {
		this.memId = memId;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemAccount() {
		return memAccount;
	}

	public void setMemAccount(String memAccount) {
		this.memAccount = memAccount;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", date=" + date +
//                '}';
//    }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}
