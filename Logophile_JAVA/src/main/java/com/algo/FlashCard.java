package com.algo;

public class FlashCard extends SM2_Impl{
	private String currNodeName;
	public FlashCard(String nodeName) {
		this.currNodeName = nodeName;
	}
	public String getCurrNodeName() {
		return currNodeName;
	}
	public void setCurrNodeName(String currNodeName) {
		this.currNodeName = currNodeName;
	}
	
}
