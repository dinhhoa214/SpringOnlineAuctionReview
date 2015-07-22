package com.asiantech.auction.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class UpdateImage {
	public String UploadFileToDisc(MultipartFile file,String forderName){ 
        try {
        	byte[] bytes = file.getBytes(); 
            String rootPath = System.getProperty("user.home");
            
            File dir = new File(rootPath + File.separator + "images" + 
            					File.separator + forderName); 
            if (!dir.exists())
                dir.mkdirs(); 
            // Create the file on server
            System.out.println(forderName +  
                    File.separator + file.getOriginalFilename() );
            File saveFile = new File(dir.getAbsolutePath() + 
                   File.separator + file.getOriginalFilename() );
              
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(saveFile)); 
            stream.write(bytes);
            stream.close();
            return "" + forderName +  
                    File.separator + file.getOriginalFilename();
        } 
        catch (Exception e) {
            return "Image Error";
        }
      
	}
}
