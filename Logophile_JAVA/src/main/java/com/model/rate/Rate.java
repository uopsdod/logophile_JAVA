package com.model.rate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.common.Common;
import com.model.pic.Pic;

@Entity
public class Rate extends Common{
	// Why should not use the AUTO JPA GenerationType with MySQL and Hibernate:
    // https://vladmihalcea.com/2017/01/24/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO, 
        generator="rateId_native"
    )
    @GenericGenerator(
        name = "rateId_native", 
        strategy = "native"
    )
    Long rateId;
    
    private String rateResult;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getRateResult() {
		return rateResult;
	}

	public void setRateResult(String rateResult) {
		this.rateResult = rateResult;
	}
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "picId")
	@JsonIgnore // to prevent infinite loop when parsing into json
    @ManyToOne(optional = false)
    private Pic ratePic; // FK


	public Pic getRatePic() {
		return ratePic;
	}

	public void setRatePic(Pic ratePic) {
		this.ratePic = ratePic;
	}

    
}
