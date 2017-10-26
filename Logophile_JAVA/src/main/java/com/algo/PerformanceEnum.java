package com.algo;

public enum PerformanceEnum {
	PERFECT_RESPONSE_05(5)
	,CORRECT_RESPONSE_04(4)
	,CORRECT_RESPONSE_03(3) // Set a cutoff for an answer being ��correct�� (defaults to 3)
	,INCORRECT_RESPONSE_02(2)
	,INCORRECT_RESPONSE_01(1)
	,COMPLETE_BLACKOUT_00(0)
	;
	private int level;
	PerformanceEnum(int level){
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
//	public int getValue() { 
//		return this.level;
//	}
	
//	 * Set quality response:
//	 * 5 - perfect response
//	 * 4 - correct response after a hesitation
//	 * 3 - correct response recalled with serious difficulty
//	 * 2 - incorrect response; where the correct one seemed easy to recall
//	 * 1 - incorrect response; the correct one remembered
//	 * 0 - complete blackout.
	
//	Newbie
//	Novice
//	Rookie
//	Beginner
//	Talented
//	Skilled
//	Intermediate
//	Skillful
//	Seasoned
//	Proficient
//	Experienced
//	Advanced
//	Senior
//	Expert	
	
	
}
