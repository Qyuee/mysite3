package com.cafe24.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface Auth {
public enum Role{ADMIN, USER}
	
	public Role role() default Role.USER;
	
	//public String value() default "user";
	//public int test() default 1;
}
