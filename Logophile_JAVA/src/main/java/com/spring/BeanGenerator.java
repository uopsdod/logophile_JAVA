package com.spring;


import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.google.gson.Gson;
import com.model.pic.PicRepository;
import com.util.Util;
import com.util.MessageBrokerUtil;
import com.util.storage.StorageProperties;
import com.util.storage.StorageService;

@Configuration
@EnableMBeanExport(defaultDomain="${projectName}")
@PropertySource("classpath:application.properties")
@ConfigurationProperties
@EnableConfigurationProperties(StorageProperties.class)
public class BeanGenerator {
	
	/**
	 * gson instance
	 * @return
	 */
	@Bean
	public Gson gson(){
		return new Gson();
	}
	
	/**
	 * to retrieve beans created in Spring and cope with bean initiation order problem
	 * @return
	 */
	@Bean(name="SpringContextHolder")
	public SpringContextHolder springContextHolder() {
	   return new SpringContextHolder();
	}
	
	
	/**
	 * myApplicationListener starts 
	 * @return
	 */
	@Bean 
	public com.spring.MyApplicationListener myApplicationListener(){
		return new com.spring.MyApplicationListener();
	}

	
	/**
	 * Util
	 * @return
	 */
	@Bean(name="Util")
	public Util util(Gson aGson, PicRepository picRepository){
		Util util = new Util(aGson, picRepository);
		return util;
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
	
	@Bean
	MessageBrokerUtil UtilWebOSocketMsgBroker(SimpMessagingTemplate aTemplate) {
		MessageBrokerUtil utilWebOSocketMsgBroker = new MessageBrokerUtil(aTemplate);
		return utilWebOSocketMsgBroker;
	}
	
}
