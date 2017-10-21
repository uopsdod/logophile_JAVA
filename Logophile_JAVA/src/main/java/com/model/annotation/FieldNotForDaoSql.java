package com.model.annotation;

import java.lang.annotation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

//@NameBinding
//@Target({ElementType.METHOD , ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface NotForDaoSql { }

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldNotForDaoSql {
}