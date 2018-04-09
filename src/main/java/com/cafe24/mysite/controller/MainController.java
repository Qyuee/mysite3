package com.cafe24.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.dto.JSONResult;
import com.cafe24.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main() { 
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() { 
		Map<String, Object> map=new HashMap<>();
		map.put("인사", "안녕하세요.");
		return map.toString();
	} 
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2() {
		return JSONResult.success(new UserVo());
	}
} 
