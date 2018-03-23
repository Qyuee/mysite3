package com.cafe24.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mvc.util.WebUtil;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardservice;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String board(
			Model model,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd, 
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage
			) {
		int totalCount=boardservice.totalCount(kwd);
		List<BoardVo> list = boardservice.ListGet(kwd, nowPage);
		Map<String, String> paging = WebUtil.Paging(nowPage, totalCount, kwd);
		 
		model.addAttribute("list", list); 
		model.addAttribute("paging", paging);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("nowPage", nowPage); 
		return "board/list";
	}
	
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view( 
			Model model,
			@RequestParam(value="bno", required=true, defaultValue="1") int bno,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage
			) {
		
		boardservice.increaseCnt(bno);
		model.addAttribute("vo", boardservice.getOneBoard(bno));
		return "board/view";
	}
	
	@RequestMapping(value= {"/write", "/reply"}, method=RequestMethod.GET)
	public String write(
			Model model,
			@RequestParam(value="bno", required=false, defaultValue="0") int bno
			) {
		if(bno!=0) { 
			model.addAttribute("bno", bno);
			model.addAttribute("pVo", boardservice.getOneBoard(bno));
		}
		return "board/write"; 
	} 
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			Model model,
			@ModelAttribute BoardVo vo,
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage 
			) {
		if(vo.getBno()!=0) { 
			return "forward:/board/reply";
		}
		boardservice.insert(vo);  
		model.addAttribute("nowPage", nowPage); 
		return "redirect:/board";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST) 
	public String reply(
			Model model,
			@RequestParam(value="bno", required=false) int pBno,
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage,
			@ModelAttribute BoardVo cVo
			) {
		System.out.println(cVo);
		boardservice.reply(pBno, cVo); 
		model.addAttribute("nowPage", nowPage);
		return "redirect:/board";
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value="bno", required=true) int bno, 
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage
			) {
		boardservice.delete(bno);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("kwd",kwd);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(
			Model model,
			@RequestParam(value="bno", required=true) int bno 
			) {  
		model.addAttribute("vo", boardservice.modifyFormContent(bno));
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(
			Model model,
			@ModelAttribute BoardVo vo,
			@RequestParam(value="bno", required=true) int bno,
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd,
			@RequestParam(value="nowPage", required=true, defaultValue="1") int nowPage
			) {  
		boardservice.modifyAction(vo);
		model.addAttribute("bno",bno);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("kwd",kwd); 
		return "redirect:/board/view"; 
	}
}
