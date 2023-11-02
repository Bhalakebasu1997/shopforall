package com.store.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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
import com.store.Dtos.UserDto;
import com.store.services.FileService;
import com.store.services.UserService;


@RestController
@RequestMapping("/users")
//@Api(value="User_API", description = "REST API related to perform user operations !!")

public class UserController {

//	private static final UserDto String = null;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	
	private Logger loger= LoggerFactory.getLogger(UserController.class);
	
	// create
	@PostMapping
	public ResponseEntity<UserDto> createUser( @RequestBody UserDto userdto){
		UserDto createUser = userService.createUser(userdto);
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
	}
	
	
	// update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId, @RequestBody UserDto userdto){
		
		UserDto updateUser = userService.updateUser(userdto, userId);
		return new ResponseEntity<UserDto>(updateUser , HttpStatus.OK);
		
	}
	
	// delete 
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
		userService.deleteUser(userId);
		ApiResponseMessage message = ApiResponseMessage.builder()
		.message("User is deleted Successfull !!")
		.success(true)
		.status(HttpStatus.OK).build();
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	
	@GetMapping()
	//@ApiOperation(value="get all user", tags = {"User Controller"})
	// get all users
	public ResponseEntity<PageableResponse> getAllUser(
			@RequestParam (value="pageNumber", defaultValue="0", required=false)int pageNumber,
			@RequestParam (value= "pagesize", defaultValue="10", required=false) int pagesize,
			@RequestParam (value= "sortBy", defaultValue="name", required=false) String sortBy,
			@RequestParam (value= "sortDir", defaultValue="10", required=false) String sortDir
	){
		return new ResponseEntity<PageableResponse>(userService.getAllUser(pageNumber,pagesize, sortBy, sortDir), HttpStatus.OK);
		
	}
	
	
	/*
	 * @GetMapping("/{userId}") // get single user by id
	 * 
	 * @ApiOperation(value= "Get single user by userId!!")
	 * 
	 * @ApiResponses(value= {
	 * 
	 * @ApiResponse(code = 200, message= "success | Ok"),
	 * 
	 * @ApiResponse(code = 401, message="not authorized"),
	 * 
	 * @ApiResponse(code = 201, message = "new User Created!!")
	 * 
	 * })
	 */
	
	 @GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUse(@PathVariable String userId){
		UserDto userById = userService.getUserById(userId);
		return new ResponseEntity<UserDto> (userById, HttpStatus.OK);
	}
	
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		UserDto userByEmail = userService.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userByEmail, HttpStatus.OK);
	}
	
	
	@GetMapping("/search/{keywords}")
public ResponseEntity<List<UserDto> >searchUser(@PathVariable String keywords){	
		List<UserDto> searchUser = userService.searchUser(keywords);
	return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
	}
	

	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse>uploadimageName(@PathVariable String userId, @RequestParam("userImage") MultipartFile image) throws IOException{
		String imageName = fileservice.uploadFile(image, imageUploadPath);
		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);
		UserDto userDto = userService.updateUser(user, userId);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("image is upload successfully").status(HttpStatus.CREATED).build();
		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
		
	}
	
	
	// serve user file 
	@GetMapping(value="/image/{userId}")
	public void serverUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		UserDto user = userService.getUserById(userId);
		loger.info("User image Name : {}", user);
		InputStream resource = fileservice.getsource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	
	
}
