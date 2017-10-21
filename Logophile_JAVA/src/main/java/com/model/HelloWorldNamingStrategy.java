//package com.model;
//
//import org.hibernate.cfg.DefaultNamingStrategy;
//
///**
// * Extending from the DefaultNamingStrategy to avoid implementing All methods of
// * NamingStrategy interface
// */
////public class HelloWorldNamingStrategy extends DefaultNamingStrategy {
////	// Extending from the DefaultNamingStrategy to
////	// avoid implementing All methods of NamingStrategy interface
////	public String classToTableName(String className) {
////		// need to get the className only
////		int dotPos = className.lastIndexOf(".");
////		String classNameWithoutPackageName = className.substring(dotPos + 1, className.length());
////		return classNameWithoutPackageName + "_Messages";
////	}
////
////	public String propertyToColumnName(String propertyName) {
////		return propertyName + "_test01";
////	}
////}