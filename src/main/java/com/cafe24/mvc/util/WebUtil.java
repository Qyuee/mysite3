package com.cafe24.mvc.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.mysite.vo.BoardVo;

public class WebUtil {
	
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
	}
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		// forwarding
		RequestDispatcher rd = request.getRequestDispatcher(path); // request를 dispatch한다.
		rd.forward(request, response);
	}
	
	/**
	 * 문자열이 null이거나 공백("")이면 defaultValue로 변환.
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String checkString(String str, String defaultValue) {
		if(str==null || str=="") {
			str=defaultValue;
		}
		return str;
	}
	
	

	/**
	 * <xmp> TextArea 개행문자 변환.(출력시 \n 값을 <br />
	 * 로 변경.) \r\n 이나 \r 이나 \n 이나 \n\r을 <br />
	 * 으로 치환 한다. </xmp>
	 * 
	 * @param str
	 * @return 변환된 문자열
	 */
	public static String transfer_textarea_enter(String str) {
		// \r\n 이나 \r 이나 \n 이나 \n\r을 <br />으로 치환 한다.
		String result = str.replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
		return result;
	}
	
	public static String transferBrToEnter(String str) {
		str=str.replaceAll("(<br/>|<br />|<br>)", "&#10;");
		return str;
	}
	
	/**
	 * 페이징 처리
	 * @param nowPage
	 * @param totalCount
	 * @return Map<String, String>
	 */
	public static Map<String, String> Paging(int nowPage, int totalCount, String kwd){
		Map<String, String> page=new LinkedHashMap<String, String>();
		int countList=BoardVo.countList; // 한 페이지 당 노출 게시글 수
		int countPage=BoardVo.countPage; // 한 블럭에 보여주느 페이지 수 
		int totalPage=totalCount/countList;  // 총 페이지 수
		int startPage=(((nowPage-1)/countPage)*countPage)+1;
		int endPage=startPage+countPage-1; 
		
		if(totalCount % countList > 0) {
			totalPage++;
		}
		
		if(totalPage < nowPage) {
			nowPage=totalPage;
		}
		
		if(startPage!=1) {
			page.put("◀", "/mysite/board?nowPage="+(startPage-1)+"&kwd="+kwd); 
		}
		
		for(int i=startPage; i<=endPage; i++) {
			if(i>totalPage) {
				page.put(String.valueOf(i), "none");
			}else {
				page.put(String.valueOf(i), "/mysite/board?nowPage="+i+"&kwd="+kwd);
			}
		}
		
		if(endPage < totalPage) {
			page.put("▶", "/mysite/board?nowPage="+(endPage+1)+"&kwd="+kwd);  
		}
		
		return page;
	}
}
