package com.cafe24.dto;

import java.util.List;

public class FileListDto {
	private List<String> filelist;
	
	public FileListDto() {
		// TODO Auto-generated constructor stub
	}
	
	public FileListDto(List<String> filelist) {
		this.filelist=filelist;
	}
	
	public List<String> getFilelist(){
		return filelist;
	}
	
}
