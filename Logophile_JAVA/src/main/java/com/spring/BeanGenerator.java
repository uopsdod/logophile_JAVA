package com.spring;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.sql2o.Sql2o;

import com.google.gson.Gson;
import com.model.Sql2oDao;
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
	
	@Value("${spring.datasource.url}")
	public String db_url;

	@Value("${spring.datasource.username}")
	public String db_username;

	@Value("${spring.datasource.password}")
	public String db_pwd;	
	
	@Value("${spring.datasource.driver-class-name}")
	public String db_className;	
	
	
	
	
	
	
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
	public Util util(Gson aGson){
		Util util = new Util(aGson);
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
	
	@Bean
	Sql2o sql2o() {
		try {
			Class.forName(this.db_className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
//			Util.getFileLogger().error("Rpt_ServiceHistory - ClassNotFoundException - Util.getExceptionMsg(e): " + Util.getExceptionMsg(e));
		}
		System.out.println("this.db_url: " + this.db_url);
		Sql2o sql2o = new Sql2o(this.db_url, this.db_username, this.db_pwd);
		return sql2o;
	}
	
	@Bean
	@DependsOn("sql2o")
	Sql2oDao sql2oDao() {
		Sql2oDao sql2oDao = new Sql2oDao(sql2o());
		return sql2oDao;
	}
	
	
	
}
