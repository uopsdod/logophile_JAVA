package com.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.model.bean.Common;
//import com.model.mem.Mem;
//import com.model.mem.MemRepository;
//import com.model.pic.Pic;
//import com.model.pic.PicRepository;
//import com.model.rate.Rate;
//import com.model.rate.RateRepository;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;
import com.util.RESTfulUtil;
import com.util.Util;

@RestController
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
    @Autowired
    DataSource dataSource;

//    @Autowired
//    MemRepository memRepository;
//
//    @Autowired
//    PicRepository picRepository;
//    
//    @Autowired
//    RateRepository rateRepository;

    @Autowired
    MessageBrokerUtil utilWebOSocketMsgBroker;
    
    @RequestMapping(value = "/getBeanFieldsMap", method = RequestMethod.GET)
    public ResponseEntity<String> getBeanFieldsMap() {
    	Util.getConsoleLogger().info("getBeanFieldsMap starts");
    	
    	Map<String, List<String>> beanFieldsMap = Util.getBeanFieldsMap();
    	String jsonStrOut = Util.getGson().toJson(beanFieldsMap);
    	
    	Util.getConsoleLogger().info("getBeanFieldsMap output ");
    	Util.getConsoleLogger().info("getBeanFieldsMap ends");
//        return jsonStrOut;
    	
    	// ref: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location)
							.contentType(MediaType.APPLICATION_JSON_UTF8) // specify we intend to return json format
							.body(jsonStrOut)
							;
    	
//    	ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonStrOut, HttpStatus.OK);
//    	responseEntity.getHeaders().add("Content-Type","application/json");
//    	return responseEntity;
    }
    
//    @PostMapping("/updatePic")
//    public Pic giveRating(@RequestParam(value="picId", required=true) long picId
//    					,@RequestParam(value="score", required=true) long score
//    					,@RequestParam(value="wordsToShow", required=true) String wordsToShow) {
//    	Util.getConsoleLogger().info(TAG + "/updatePic starts");
//    	Util.getConsoleLogger().info(TAG + "/updatePic input picId: " + picId);
//    	Util.getConsoleLogger().info(TAG + "/updatePic input score: " + score);
//    	Util.getConsoleLogger().info(TAG + "/updatePic input wordsToShow: " + wordsToShow);
//    	
//    	/** 先抓取舊資料 **/
//    	Pic pic = picRepository.findOne(picId);
//    	if (pic == null){
//    		Util.getFileLogger().info("/updatePic - picId: " + picId + " not found in Pic table");
//    	}
//    	
//    	/** 更新bean **/
//    	long rateNum = pic.getRateNum() + 1;
//    	long rateResult = (pic.getRateResult()*pic.getRateNum() + score)/(pic.getRateNum() + 1);
//    	pic.setRateNum(rateNum);
//    	pic.setRateResult(rateResult);
//    	
//    	/** 進行Pic Table更新 **/
//    	Pic newPic = picRepository.save(pic); // update
//    	
//    	/** 進行Rate Table更新 **/
//    	Rate rate = new Rate();
//    	rate.setRatePic(newPic);
//    	rate.setRateResult(wordsToShow);
//    	
//    	Rate newRate = rateRepository.save(rate);
//    	
//    	/** 加上原本最新的Rate **/
//    	newPic.getRateList().add(newRate);
//    	
//    	Util.getConsoleLogger().info(TAG + "/updatePic output ");
//    	Util.getConsoleLogger().info(TAG + "/updatePic ends");
//    	
//    	/** 告知訂閱client們 **/
//    	newPic.setPicFile(null); // 更新不需要再將圖檔傳給前端
//    	utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_ratingHistory, newPic);
//    	
//    	/** 更新當下Pic物件 **/
//    	FileUploadUtil.lastPic = newPic;
//    	
//    	return newPic;
//    }
}
