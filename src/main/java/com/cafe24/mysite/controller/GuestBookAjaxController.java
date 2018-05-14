package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.dto.JSONResult;
import com.cafe24.mysite.service.GuestBookAjaxService;
import com.cafe24.mysite.vo.GuestBookAjaxVo;

@Controller
@RequestMapping("/guestbookajax")
public class GuestBookAjaxController {
	
	@Autowired
	private GuestBookAjaxService service;
	
	@RequestMapping("")
	public String guestbookAjax() {
		return "index-ajax";
	}
	
	@ResponseBody 
	@RequestMapping(value="insert" , method=RequestMethod.POST)
	public JSONResult insert( 
			@RequestBody GuestBookAjaxVo guestBookAjaxVo
			) {
		System.out.println(" >>> vo : "+guestBookAjaxVo);
		long recent_pk=service.insert(guestBookAjaxVo);
		if(recent_pk!=0 && recent_pk>0) {
			System.out.println("recent_pk : "+recent_pk);
			GuestBookAjaxVo ajaxVo=service.get(guestBookAjaxVo.getNo());
			System.out.println(" >>> "+ajaxVo); 
			return JSONResult.success(ajaxVo);
		}
		return JSONResult.success("fail");
	}
	
	@ResponseBody
	@RequestMapping(value="getlistAjax", method=RequestMethod.GET)
	public JSONResult getListAjax(
			@RequestParam("startNo") long _parameter
			){
		List<GuestBookAjaxVo> list=service.getListAjax(_parameter);
		
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public JSONResult getListAjax(
			@ModelAttribute GuestBookAjaxVo vo
			){
		boolean result=service.deleteAjax(vo); 
		return JSONResult.success(result  ? vo.getNo() : -1);
	}
}
