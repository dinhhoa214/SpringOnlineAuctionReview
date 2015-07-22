package com.asiantech.auction.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import com.asiantech.auction.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {
	@Autowired
	private ImageService imageSv;
	
	//http://localhost:8080/TemplateUI/web/onlineAuctionLogo.PNG
	//http://localhost:8080/TemplateUI/item/1434096859141data-types.jpg
	@RequestMapping("/{path}/{pathImage:.*}")
	public ResponseEntity<byte[]> testphoto(@PathVariable String path,@PathVariable String pathImage,
			HttpServletResponse response) throws IOException {  
		if(StringUtils.isBlank(pathImage)){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} 
		ResponseEntity<byte[]> responseEntity = imageSv.getResponseImage(path,pathImage);
		if(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return responseEntity;
	}
}
