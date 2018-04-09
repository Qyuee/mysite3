package com.cafe24.security;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.dto.FileListDto;

public class FileListArgumentResolver implements org.springframework.web.method.support.HandlerMethodArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		/*
		  webRequest.getParameter("width");
		  webRequest.getParameter("height");
		*/
		
		if(supportsParameter(parameter)==false) {
			// 파라미터가 검증이 실패하면.
			System.out.println("검증 실패!");
			return WebArgumentResolver.UNRESOLVED;
		}
		
		String contextPath="/assets/gallery-examples/";
		
		// 특정 경로의 파일목록을 가져와야 함.
		String realPath="C:\\cafe24\\workspace\\mysite3\\webapp\\assets\\gallery-examples";
		
		File targetFile=new File(realPath);
		
		System.out.println("is directory? "+targetFile.isDirectory());
		
		// 폴더 내의 모든 파일 이름을 반환.
		String[] files=targetFile.list();
		
		List<String> filelist=new LinkedList<>();
		
		for(int i=0; i<files.length; i++) {
			// 해당 폴더내의 서브 폴더를 제외하기 위한 확장자 추출
			String extName=files[i].substring(files[i].lastIndexOf(".")+1, files[i].length());
			
			// 추출한 확장자를 검사하여 분류
			if(extName.equals("jpg") || extName.equals("png")) {
				filelist.add(contextPath+files[i]); 
			}
		}
		
		return new FileListDto(filelist);
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) { 
		
		FileList fileList= parameter.getParameterAnnotation(FileList.class);
		
		if(fileList==null) {
			return false;
		}
		
		if(!parameter.getParameterType().equals(FileListDto.class)) {
			return false;
		}
		
		return true;
	}
	
}
