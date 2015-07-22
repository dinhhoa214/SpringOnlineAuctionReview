package com.asiantech.auction.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;  
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.asiantech.auction.common.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService{ 
	
	@Autowired
	private HandleImage handleIm;
	
	@Autowired
	private UpdateImage updateIm;
	
	@Override
	public ResponseEntity<byte[]> getResponseImage(String url,String urlImage) throws IOException {
		
		if(StringUtils.isBlank(urlImage)){
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		File file = handleIm.getFileImage(url,urlImage);
		
		if(file == null){
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		BufferedInputStream bf = new BufferedInputStream(new FileInputStream(file));
		byte[] data = IOUtils.toByteArray(bf);
		
		final HttpHeaders headers = extractHeader(file);
	    return new ResponseEntity<byte[]>(data,headers, HttpStatus.OK);
	}

	private HttpHeaders extractHeader(File file) {
		final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    headers.setContentLength(file.length());
	    headers.set("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		return headers;
	}

	@Override
	public String updateImage(MultipartFile file, String forderName) {
		 
		return updateIm.UploadFileToDisc(file, forderName);
	}
	
	
}
