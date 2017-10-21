package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.common.Common;
import com.model.mem.Mem;
import com.model.mem.MemRepository;
import com.model.pic.Pic;
import com.model.pic.PicRepository;
import com.model.rate.Rate;
import com.model.rate.RateRepository;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;
import com.util.RESTfulUtil;
import com.util.Util;

@RestController
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
    @Autowired
    DataSource dataSource;

    @Autowired
    MemRepository memRepository;

    @Autowired
    PicRepository picRepository;
    
    @Autowired
    RateRepository rateRepository;

    @Autowired
    MessageBrokerUtil utilWebOSocketMsgBroker;
    
    @PostMapping("/login")
    public Mem login(@RequestParam(value="account", required=true) String account
    						 ,@RequestParam(value="password", required=true) String password) {
    	Util.getConsoleLogger().info(TAG + "/login starts");
    	Util.getConsoleLogger().info(TAG + "/login input account: " + account);
    	Util.getConsoleLogger().info(TAG + "/login input password: " + password);
    	
    	/** 搜尋DB是否已有此帳號 **/
    	Mem mem = new Mem();
    	List<Mem> memList = memRepository.findByMemAccount(account);
    	
    	if (memList.size() > 1){
    		Util.getConsoleLogger().error(TAG + " find more than one rows by account: " + account);
    		mem.addError("multiple accounts exist");
    	}else if (memList.size() == 0){
    		Util.getConsoleLogger().info(TAG + " no row found by account: " + account);
    		mem.addError("account does not exist");
        /** 驗證帳號密碼是否符合 **/
    	}else if (memList.size() == 1){
    		Mem currMem = memList.get(0);
    		if (currMem != null){
    			if (!currMem.getMemPwd().equals(password)){
    				Util.getConsoleLogger().info(TAG + " wrong password ");
    				mem.addError("wrong password");
    			/** 帳號密碼皆正確,給予使用者完整資訊 **/
    			}else{
    				mem = currMem;
    				mem.setJwtStr(RESTfulUtil.createJWT());
    			}
    		}
    	}
    	
    	Util.getConsoleLogger().info(TAG + "/login output ");
    	Util.getConsoleLogger().info(TAG + "/login ends");
        return mem;
    }
    
    @PostMapping("/updatePic")
    public Pic giveRating(@RequestParam(value="picId", required=true) long picId
    					,@RequestParam(value="score", required=true) long score
    					,@RequestParam(value="wordsToShow", required=true) String wordsToShow) {
    	Util.getConsoleLogger().info(TAG + "/updatePic starts");
    	Util.getConsoleLogger().info(TAG + "/updatePic input picId: " + picId);
    	Util.getConsoleLogger().info(TAG + "/updatePic input score: " + score);
    	Util.getConsoleLogger().info(TAG + "/updatePic input wordsToShow: " + wordsToShow);
    	
    	/** 先抓取舊資料 **/
    	Pic pic = picRepository.findOne(picId);
    	if (pic == null){
    		Util.getFileLogger().info("/updatePic - picId: " + picId + " not found in Pic table");
    	}
    	
    	/** 更新bean **/
    	long rateNum = pic.getRateNum() + 1;
    	long rateResult = (pic.getRateResult()*pic.getRateNum() + score)/(pic.getRateNum() + 1);
    	pic.setRateNum(rateNum);
    	pic.setRateResult(rateResult);
    	
    	/** 進行Pic Table更新 **/
    	Pic newPic = picRepository.save(pic); // update
    	
    	/** 進行Rate Table更新 **/
    	Rate rate = new Rate();
    	rate.setRatePic(newPic);
    	rate.setRateResult(wordsToShow);
    	
    	Rate newRate = rateRepository.save(rate);
    	
    	/** 加上原本最新的Rate **/
    	newPic.getRateList().add(newRate);
    	
    	Util.getConsoleLogger().info(TAG + "/updatePic output ");
    	Util.getConsoleLogger().info(TAG + "/updatePic ends");
    	
    	/** 告知訂閱client們 **/
    	newPic.setPicFile(null); // 更新不需要再將圖檔傳給前端
    	utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_ratingHistory, newPic);
    	
    	/** 更新當下Pic物件 **/
    	FileUploadUtil.lastPic = newPic;
    	
    	return newPic;
    }
}
