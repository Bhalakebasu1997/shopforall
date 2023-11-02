package com.store.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.store.Dtos.ApiResponseMessage;
import com.store.Dtos.ImageResponse;
import com.store.Dtos.PageableResponse;
import com.store.Dtos.ProductDto;
import com.store.services.FileService;
import com.store.services.ProductService;



@RestController
@RequestMapping("/product")
public class ProductController<HttpServletResponse> {

	@Autowired
	private ProductService productservice;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	@Autowired
	private FileService fileservice;
	
	
	// create
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){
		 ProductDto productdto = productservice.create(productDto);
		return new ResponseEntity<>(productdto, HttpStatus.CREATED);
	}
	
	
	// update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto ,@PathVariable String productId){
		ProductDto update = productservice.update(productDto, productId);
		return new ResponseEntity<>(update, HttpStatus.OK);	
	}
	
	// delete
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId){
		productservice.delete(productId);
		ApiResponseMessage apiresponsemessage = ApiResponseMessage.builder().message("Product is deleted Succefully !!").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(apiresponsemessage, HttpStatus.OK);
		
	}
	
	
	
	
	// get all id
	 @GetMapping
	 public ResponseEntity<PageableResponse<ProductDto>> getAll(
			 @RequestParam(value= "pageNumber" ,defaultValue="0", required=false) int pageNumber,
			 @RequestParam(value= "pageSize", defaultValue="10", required = false) int pageSize,
			 @RequestParam(value= "sortBy", defaultValue="title", required =false) String sortBy,
			 @RequestParam(value= "sortDir", defaultValue="asc", required=false) String sortDir
){
		 PageableResponse<ProductDto> response = productservice.getAll(pageNumber, pageSize, sortBy, sortDir);
		 
		return new ResponseEntity<>(response, HttpStatus.OK); 
	 }
	
	
	
	// get by single id
	
	 public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			 @RequestParam(value= "pageNumber" ,defaultValue="0", required=false) int pageNumber,
			 @RequestParam(value= "pageSize", defaultValue="10", required = false) int pageSize,
			 @RequestParam(value= "sortBy", defaultValue="title", required =false) String sortBy,
			 @RequestParam(value= "sortDir", defaultValue="asc", required=false) String sortDir){
				
				 
				 PageableResponse<ProductDto> response = productservice.getAlllive(pageNumber, pageSize, sortBy, sortDir);
				return new ResponseEntity<>(response, HttpStatus.OK);
				 
			 }
	 
	 
	// search 
	
	 @GetMapping("/search/{subTitle}")
	 public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
			 
			 @PathVariable String subTitle,
			 @RequestParam(value= "pageNumber" ,defaultValue="0", required=false) int pageNumber,
			 @RequestParam(value= "pageSize", defaultValue="10", required = false) int pageSize,
			 @RequestParam(value= "sortBy", defaultValue="title", required =false) String sortBy,
			 @RequestParam(value= "sortDir", defaultValue="asc", required=false) String sortDir){
				// PageableResponse<ProductDto> response = productservice.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
		 PageableResponse<ProductDto> response = productservice.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
				return new ResponseEntity<>(response, HttpStatus.OK);
				 
			 }
	 
	 
	@PostMapping("/image/{productId}")
	 public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId, @RequestParam("productImage") MultipartFile image) throws IOException{
		 String uploadFile = fileservice.uploadFile(image, imagePath);
		 ProductDto productDto = productservice.get(productId);
		 productDto.setProductImageName(uploadFile);
		 ProductDto update = productservice.update(productDto, productId);
		 ImageResponse imageresponse = ImageResponse.builder().imageName(update.getProductImageName()).message("product image is successfully uploaded!!").status(HttpStatus.CREATED).success(true).build();
		return new ResponseEntity<>(imageresponse, HttpStatus.CREATED);
		 
	 }
	
	 
		/*
		 * public void serveUserImage(@PathVariable String productId,
		 * HttpServletResponse response) throws IOException { ProductDto productDto =
		 * productservice.get(productId); InputStream getsource =
		 * fileservice.getsource(imagePath, productDto.getProductImageName());
		 * response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 * StreamUtils.copy(getsource, response.getOutputStream()); }
		 */
	 
	 
	 
}
