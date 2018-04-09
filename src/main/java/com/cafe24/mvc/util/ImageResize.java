package com.cafe24.mvc.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ImageResize {
	private static final String RESIZE_SAVE_FOLDER="\\thumbs";
	/**
	 * 이미지를 원하는 크기에 맞추어 리사이즈 한다.
	 * @param srcPath - 원본 이미지 경로 
	 * @param destPath - 변환 이미지 저장 경로
	 * @param filename - 파일 이름
	 * @param suffix - 확장자 명
	 * @throws IOException
	 */
	public static void imageResize(String srcPath, String filename, String suffix) throws IOException {
		// srcPath = 대상 파일 저장 경로
		// srcPath+RESIZE_SAVE_FOLDER = 썸네일 저장 경로
		File destFolder = new File(srcPath+RESIZE_SAVE_FOLDER);
		
		// 지정한 경로에 폴더가 없으면 폴더를 생성.
		if(!destFolder.exists()) {
			destFolder.mkdirs();
		}
		
		System.out.println(" >>> "+srcPath+filename+suffix);
		
		// 이미지에 대한 byte 데이터를 버퍼에 저장.
		BufferedImage image=ImageIO.read(new File(srcPath+filename+suffix));
		
		System.out.println(" >>>전 "+image);
		 
		// 해쉬명 생성. - 중복되지 않는 유니크한 파일명을 위해
		// String convertID=UUID.randomUUID().toString().replace("-", ""); 
		
		// 변경하려는 이미지의 타입이 0이면 정수형 RGB로 하고 아니면 그대로.
		int type=image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
		
		// 이미지를 변환하여 변환 된 BufferdImage를 구한다.
		BufferedImage resizeImage = resizeImageStart(image, type); 
		
		System.out.println(" >>>후 "+resizeImage);
		System.out.println("[경로] : "+srcPath+RESIZE_SAVE_FOLDER+"\\"+filename+suffix);
		System.out.println("[확장자] : "+suffix);
		
		// 변환 된 이미지를 지정된 경로에 쓴다.
		ImageIO.write(resizeImage, suffix.substring(1), new File(srcPath+RESIZE_SAVE_FOLDER+"\\"+filename+suffix));
	} 
	
	public static BufferedImage resizeImageStart(BufferedImage original, int type) {
		
		BufferedImage resizeImage=new BufferedImage(100, 100, type);
		Graphics2D g=resizeImage.createGraphics();
		
		g.drawImage(original, 0, 0, 100, 100, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		return resizeImage;
	}
}
