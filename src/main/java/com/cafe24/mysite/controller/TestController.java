package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.vo.TestVo;
import com.cafe24.security.AuthUser;
import com.cafe24.security.TestParam;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping(value="/hmar1", method=RequestMethod.GET)
	public String hmar1() {
		return "test/paramTest"; 
	} 
	
	@RequestMapping(value="/hmar1", method=RequestMethod.POST)
	public String hmar1(@TestParam @AuthUser TestVo testvo, Model model) {
		System.out.println(testvo);
		model.addAttribute("vo", testvo); 
		return "test/paramTest_proc"; 
	}
}
