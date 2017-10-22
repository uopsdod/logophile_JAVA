package com.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.util.ResourceUtils;

// ref: https://github.com/FutureProcessing/spring-boot-security-example/blob/master/src/main/java/com/futureprocessing/spring/ContainerConfiguration.java
public class MyTomcatConnectorCustomizer implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer factory) {
        if(factory instanceof TomcatEmbeddedServletContainerFactory) {
            customizeTomcat((TomcatEmbeddedServletContainerFactory) factory);
        }
    }

    public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
    	TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) factory;
        tomcat.addConnectorCustomizers(connector -> {
        	connector.setAttribute("parseBodyMethods", "POST,PUT,DELETE");
        });
    }

}