package com.asiantech.auction.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	ResponseEntity<byte[]> getResponseImage(String url,String urlImage) throws IOException;
	public String updateImage(MultipartFile file,String forderName);
}
