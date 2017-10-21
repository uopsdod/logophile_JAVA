
package com;

import static java.lang.System.exit;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.Transactional;

import com.util.Util;

@SpringBootApplication
@ComponentScan({"com"})
@EnableMBeanExport(defaultDomain="${projectName}")
@ImportResource("classpath:spring-config.xml")
public class StartApplication extends SpringBootServletInitializer{
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	System.out.println("StartApplication configure() called ***************************** ");
        return application.sources(StartApplication.class);
        
    }	
	
	public static void main(String[] args){
		SpringApplication.run(StartApplication.class, args);
		System.out.println("StartApplication main() called ***************************** ");
//		Util.getFileLogger().info("Util.getVersion(): ");
	}
	

	
}
