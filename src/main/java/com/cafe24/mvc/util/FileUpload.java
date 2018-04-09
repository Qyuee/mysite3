package com.cafe24.mvc.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	// SAVE_PATH : 실제 저장 경로
	// PREFIX_URL : 클라이언트의 이미지 호출 주소 경로
	// 이 둘의 url 경로 매핑이 servlet-context에서 이루어져야 한다.
	public static final String SAVE_PATH = "C:\\cafe24\\workspace\\mysite3\\webapp\\assets\\gallery-examples";
	public static final String PREFIX_URL = "/gallery/images";

	/**
	 * 사용자 업로드 파일명 -> 서버 파일 저장명 변환.
	 * @param multipartFile
	 * @return 서버 저장명
	 */ 
	public static Map<String, Object> restore(MultipartFile multipartFile) {
		String url = "";
		String originFileName = multipartFile.getOriginalFilename();
		String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());

		/*String saveFileName = conversionFileName(extName).toLowerCase();*/
		
		String saveFileName=conversionFileName();
		
		long filesize=multipartFile.getSize();
		try {
			writeFile(multipartFile, saveFileName, extName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		url = PREFIX_URL + saveFileName;
		
		Map<String, Object> uploadInfo=new HashMap<>();
		uploadInfo.put("url", url);
		uploadInfo.put("filesize", filesize);
		uploadInfo.put("saveFileName", saveFileName);

		return uploadInfo;
	}

	// 파일 저장 메소드
	// 예외는 호출 메소드로 회피.
	private static void writeFile(MultipartFile multipartFile, String saveFileName, String extName) throws IOException {
		byte[] fileData = multipartFile.getBytes();
		
		// 원본 파일은 변경된 이름으로 저장.
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName+extName);
		fos.write(fileData);
		fos.close();
		
		// thumbs 파일 저장 로직 필요  
		ImageResize.imageResize(SAVE_PATH+"/", saveFileName, extName); 
	}

	// 확장자를 받고 식별될 수 있는 조합의 이름으로 변환. 
	private static String conversionFileName() {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		int milsec = calendar.get(Calendar.MILLISECOND);

		filename += year;
		filename += month;
		filename += day;
		filename += hour;
		filename += min;
		filename += sec;
		filename += milsec; 

		return filename;
	}  
	 
	public static String getSaveName(String savedFileName) {
		return PREFIX_URL +"/"+ savedFileName.toLowerCase();
	}
	
}
