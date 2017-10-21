package com.model.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;

public class MyImplicitStrategy extends ImplicitNamingStrategyLegacyJpaImpl {

	// ref: https://github.com/hibernate/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/boot/model/naming/ImplicitNamingStrategyLegacyJpaImpl.java
	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		String name = source.getReferencedColumnName().getText();
		return toIdentifier( name, source.getBuildingContext() );
		// TODO Auto-generated method stub
//		return super.determineJoinColumnName(arg0);
	}
	
}
