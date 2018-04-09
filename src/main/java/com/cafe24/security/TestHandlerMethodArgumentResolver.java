package com.cafe24.security;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.TestVo;

public class TestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		// 파라미터에 어떤 애노테이션이 붙어 있는가를 검사한다.
		TestParam testParam = parameter.getParameterAnnotation(TestParam.class);  //파라미터에 붙어있는 어노테이션을 뽑는다.
		
		if(testParam==null) {
			return false;
		}
		
		// 강사님이 알려준 파라미터에 붙어 있는 어노테이션 타입 검사.
		if(parameter.getParameterType().equals(TestVo.class)==false) {
			return false;
		} 
		
		// 파라미터에 해당 어노테이션이 붙어 있는지 아닌지를 검사. boolean형
		boolean result=parameter.hasParameterAnnotation(AuthUser.class);
		System.out.println(result);
		
		// 파라미터에 어떤 어노테이션이 붙어 있는지 검사한다. 여러개의 어노테이션이 있으면 이를 배열로 하여 관리.
		Annotation[] annotations = parameter.getParameterAnnotations();
		
		for(int i=0; i<annotations.length; i++) { 
			System.out.println(annotations[i].annotationType().getSimpleName()); 
		}
		
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		if(supportsParameter(parameter)==false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		TestVo testVo=new TestVo();
		
		testVo.setFirst(webRequest.getParameter("first"));
		testVo.setSecond(webRequest.getParameter("second"));
		testVo.setThird(webRequest.getParameter("third"));
		
//		Iterator<String> param=webRequest.getParameterNames();
//		
//		while(param.hasNext()) {
//			testVo.
//		}
		
		return testVo;
	}

}
