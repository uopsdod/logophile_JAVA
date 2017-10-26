package com.algo;

import org.apache.commons.lang3.builder.ToStringBuilder;

// ref: http://www.blueraja.com/blog/477/a-better-spaced-repetition-learning-algorithm-sm2
public class SM2_Impl {
	private Double easiness = new Double(2.5);
	private Double consecutiveCorrectAnswers = new Double(0);
	private Double nextDueDate = new Double(0);
	
	public Double getEasiness() {
		return easiness;
	}
	public void setEasiness(Double easiness) {
		this.easiness = easiness;
	}
	public Double getConsecutiveCorrectAnswers() {
		return consecutiveCorrectAnswers;
	}
	public void setConsecutiveCorrectAnswers(Double consecutiveCorrectAnswers) {
		this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
	}
	public Double getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(Double nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	
	public void ans(PerformanceEnum performanceRating) {
		int pLv = performanceRating.getLevel();
		this.easiness +=  -0.8 + 0.28*pLv + 0.02*Math.pow(pLv, 2);
		if (this.easiness < 1.3) {
			this.easiness = 1.3; // the min val
		}else if (this.easiness > 3) {
			this.easiness = 3.0; // the max val 
		}
		
		
		switch(performanceRating) {
		case CORRECT_RESPONSE_03:
		case CORRECT_RESPONSE_04:
		case PERFECT_RESPONSE_05:
			this.consecutiveCorrectAnswers++;
			this.nextDueDate = this.nextDueDate + (6 * Math.pow(this.easiness, this.consecutiveCorrectAnswers - 1));
			break;
		case COMPLETE_BLACKOUT_00:
		case INCORRECT_RESPONSE_01:
		case INCORRECT_RESPONSE_02:
			this.consecutiveCorrectAnswers = 0.0;
			this.nextDueDate = this.nextDueDate + 1;
			break;
		default:
			break;
		}
		
	}
	
//	public static void ans(SM2_Impl sm2Obj ,PerformanceEnum performanceRating) {
//		int pLv = performanceRating.getLevel();
//		sm2Obj.setEasiness(sm2Obj.getEasiness() + -0.8 + 0.28*pLv + 0.02*Math.pow(pLv, 2));
//		if (sm2Obj.getEasiness() < 1.3) {
//			sm2Obj.setEasiness(1.3); // the min val
//		}else if (sm2Obj.getEasiness() > 3) {
//			sm2Obj.setEasiness(3.0); // the max val 
//		}
//		
//		switch(performanceRating) {
//		case CORRECT_RESPONSE_03:
//		case CORRECT_RESPONSE_04:
//		case PERFECT_RESPONSE_05:
//			sm2Obj.setConsecutiveCorrectAnswers(sm2Obj.getConsecutiveCorrectAnswers() + 1);
//			sm2Obj.setNextDueDate(sm2Obj.getNextDueDate() + (6 * Math.pow(sm2Obj.getEasiness(), sm2Obj.getConsecutiveCorrectAnswers() - 1)));
//			break;
//		case COMPLETE_BLACKOUT_00:
//		case INCORRECT_RESPONSE_01:
//		case INCORRECT_RESPONSE_02:
//			sm2Obj.setConsecutiveCorrectAnswers(0.0);
//			sm2Obj.setNextDueDate(sm2Obj.getNextDueDate() + 1);
//			break;
//		default:
//			break;
//		}
//		
//	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
