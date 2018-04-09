package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.dto.FileListDto;
import com.cafe24.mvc.util.FileUpload;
import com.cafe24.security.FileList;

@Controller
public class GalleryController {
	 
	@RequestMapping("/gallery")
	public String gallery(
			Model model,
			@FileList FileListDto fileList
			) {
		List<String> files=fileList.getFilelist();
		model.addAttribute("files", files);
		return "gallery/index";
	}
	
	@RequestMapping(value="/gallery/upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("comments") String comments
			) {
		FileUpload.restore(file);
		
		
		return "redirect:/gallery";
	}
	
}
 