package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.UserVo;

public class HandlerMethodArgumentResolver implements org.springframework.web.method.support.HandlerMethodArgumentResolver{

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer arg1, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		if(supportsParameter(parameter)==false) {
			return WebArgumentResolver.UNRESOLVED; 
		}
		
		// @AuthUser가 붙어 있고 파라미터 타입이 UserVo가 확실한 상태. 
		HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session=request.getSession();
		
		if(session==null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		// 1. @AuthUser 가 붙어 있는지? 확인.
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		parameter.hasParameterAnnotation(AuthUser.class);
		
		// 2. @AuthUser가 안 붙어 있음
		if(authUser==null) {
			return false;
		}
		
		// 3. Type이  UserVo인지 검사.
		if(parameter.getParameterType().equals(UserVo.class)==false) {
			return false;
		}
		
		return true;
	}
	
}
