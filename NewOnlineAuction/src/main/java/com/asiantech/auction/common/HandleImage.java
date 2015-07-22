package com.asiantech.auction.common;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandleImage{
	
	public static final int DEFAULT_BUFFER_SIZE = 10240;
	
	@Value("${"+SystemConstant.IMAGE_DOCROOT+"}")
	private String rootbaseImg;
	
	public String getPathImg(String url,String urlImage){ 
		return rootbaseImg + File.separator + url + File.separator + urlImage;
	}
	
	public String getBaseUrl(){
		return rootbaseImg;
	}
	
	public File getFileImage(String url,String urlImage) throws IOException{
		String pathImage = getPathImg(url,urlImage);
		File file = new File(pathImage);
		if(!file.exists()){
			return null;
		}
		return file;
	}
}
