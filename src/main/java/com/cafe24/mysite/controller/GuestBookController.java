package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestBookService;
import com.cafe24.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model) {
		System.out.println(" >> called list GET");
		model.addAttribute("list", guestBookService.getListGuestBookVo());
		return "guestbook/list";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVo vo) {
		System.out.println(" >> called list POST");
		guestBookService.insertGuestBookVo(vo);
		return "redirect:/guestbook"; 
	} 
	
	@RequestMapping(value="/del", method=RequestMethod.GET)
	public String del(@RequestParam(value="no", required=true) int no, Model model) {
		System.out.println(" >> called del GET");
		model.addAttribute("no", no);
		return "guestbook/deleteform"; 
	}
	 
	@RequestMapping(value="/del", method=RequestMethod.POST)
	public String del(
			Model model,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="no", required=true) int no
			) {
		System.out.println(" >> called del POST");
		System.out.println(no); 
		System.out.println(password);
		Boolean result=guestBookService.deleteOneGuestBook(no, password);
		System.out.println(result); 
		if(result==false) {
			model.addAttribute("result", result);
			//return "forward:/WEB-INF/views/guestbook/list.jsp";
			return "forward:/guestbook/del";
		}
		
		return "redirect:/guestbook";
	}
}
