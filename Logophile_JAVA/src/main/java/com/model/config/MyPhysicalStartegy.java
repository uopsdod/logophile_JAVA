package com.model.config;

import java.io.Serializable;
import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class MyPhysicalStartegy extends PhysicalNamingStrategyStandardImpl implements Serializable {
	// ref: https://github.com/hibernate/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/boot/model/naming/PhysicalNamingStrategyStandardImpl.java
}