package com.spring;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.util.RESTfulUtil;
import com.util.Util;
import com.util.MessageBrokerUtil;
import com.util.Util;

@Component
public class SpringTestRunner implements CommandLineRunner{
	@Autowired
	private MessageBrokerUtil utilWebOSocketMsgBroker;
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource dataSource;

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {
		Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 開始測試");
		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 開始測試");
		
//        System.out.println("DATASOURCE = " + dataSource);
//        
//        System.out.println("\n1.findAll()...");
//        for (Customer customer : customerRepository.findAll()) {
//            System.out.println(customer);
//        }
//
//        System.out.println("\n2.findByEmail(String email)...");
//        for (Customer customer : customerRepository.findByEmail("222@yahoo.com")) {
//            System.out.println(customer);
//        }
//
//        System.out.println("\n3.findByDate(Date date)...");
//        for (Customer customer : customerRepository.findByDate(sdf.parse("2017-02-12"))) {
//            System.out.println(customer);
//        }
//
//        // For Stream, need @Transactional
//        System.out.println("\n4.findByEmailReturnStream(@Param(\"email\") String email)...");
//        try (Stream<Customer> stream = customerRepository.findByEmailReturnStream("333@yahoo.com")) {
//            stream.forEach(x -> System.out.println(x));
//        }

        //System.out.println("....................");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date from = sdf.parse("2017-02-15");
        //Date to = sdf.parse("2017-02-17");

        //for (Customer customer : customerRepository.findByDateBetween(from, to)) {
        //    System.out.println(customer);
        //}

        
//        System.out.println("Done!");
		
		// JWT - generate
//		String jwt = RESTfulUtil.createJWT();
//		Util.getConsoleLogger().info("TestSender - jwt: " + jwt);
//		
//		RESTfulUtil.parseJWT(jwt);
//		
//		jwt = RESTfulUtil.createJWT();;
//		Util.getConsoleLogger().info("TestSender - jwt: " + jwt);
//		
//		RESTfulUtil.parseJWT(jwt);
		
		
        Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 結束測試");
		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 結束測試");
//        exit(0);
    }

}