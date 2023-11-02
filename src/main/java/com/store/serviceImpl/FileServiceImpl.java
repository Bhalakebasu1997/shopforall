package com.store.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.store.exception.BadApiRequestExcepiton;
import com.store.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	
	private Logger loger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFileName= file.getOriginalFilename();
		loger.info("FileName : {}", originalFileName);
		String fileName = UUID.randomUUID().toString();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileNamewithextension= fileName+extension;
		String fullPathWithFileName= path + fileNamewithextension;
		
		loger.info("FileName : {}", fullPathWithFileName);
		
		if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")) {
			// file save
			loger.info("FileName : {}", extension);
			
			File folder= new File(path);
			
			if(!folder.exists()) {
			// create the folder
				folder.mkdirs();
		}
			
			// upload
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
		//	return fileNamewithextension;
			
		}else {
			throw new BadApiRequestExcepiton("File with this " +extension + "not allowed!!");
		}
		return fileNamewithextension;
		
	}

	@Override
	public InputStream getsource(String path, String name) throws FileNotFoundException {
		String fullPath= path + name;
	//	String fullPath= path +File.separator + name;
		InputStream inputStream  = new FileInputStream(fullPath);
		return inputStream;
	}
	

}
