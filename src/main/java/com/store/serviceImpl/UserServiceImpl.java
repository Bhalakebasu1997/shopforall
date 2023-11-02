package com.store.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.Dtos.PageableResponse;
import com.store.Dtos.UserDto;
import com.store.entities.User;
import com.store.exception.ResourceNotFoundException;
import com.store.healpher.Helper;
import com.store.repository.UserRepository;
import com.store.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	//private static final Object User = null;


	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private ModelMapper mapper;
	
	/*@Autowired
	private PasswordEncoder passwordencoder;
*/
	//@Value("${user.profile.image.path}")
	@org.springframework.beans.factory.annotation.Value("${user.profile.image.path}")
	private String imagePath;
	
	private Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = mapper.map(userDto , User.class);
		
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
	//	userDto.setPassword(passwordencoder.encode(userDto.getPassword()));
		
	 User user2 = mapper.map(userDto, User.class);
		User saveuser = userrepo.save(user2);
		
		return mapper.map(saveuser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found for update"));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		//user.setName(userDto.getImageName());
		user.setImageName(userDto.getImageName());
		User saveUser = userrepo.save(user);
		
		UserDto userDto2 = mapper.map(saveUser, UserDto.class);
		//UserDto updatedto = entityToDto(saveUser);
		return userDto2;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not foundd for delete"));
		// delete user profile image
		// images/users/abc.png
		
		String fullPath= imagePath+user.getImageName();
		
		try {
		Path path = Paths.get(fullPath);
		Files.delete(path);
		}catch(NoSuchFileException ex) {
			logger.info("User Image not found in the folder");
			ex.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		
		// delete users
		userrepo.delete(user);
		
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber, int pagesize, String sortBy, String sortDir) {
		
	
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
	//	Sort sort= Sort.by(sortBy);
		
		Pageable pageable = PageRequest.of(pageNumber, pagesize, sort);
		
	 Page<User> page = userrepo.findAll(pageable);
	// Helper.getPageableResponse(page, User.class);
		
	/* List<User> users= page.getContent();
		List<UserDto> dtolist = users.stream().map(User ->entityToDto(User)).collect(Collectors.toList());
	
		PageableResponse<UserDto> response= new PageableResponse<UserDto>();
		response.setContent(dtolist);
		response.setPageNummber(page.getNumber());
		response.setPagesize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());
		*/
	 PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		
	 return response;
	}

	@Override
	public UserDto getUserById(String userId) {
		// optional containing under result 
		User user1 = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found for getbyId"));
		UserDto userDto = mapper.map(user1 , UserDto.class);
		return userDto;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userrepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found by the Email"));
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userrepo.findByNameContaining(keyword);
		/*
		 * UserDto dto = mapper.map(users, UserDto.class); List<UserDto> dtolist =
		 * users.stream().map(User -> dto).collect(Collectors.toList());
		 */
		List<UserDto> dtolist = users.stream().map(User ->entityToDto(User)).collect(Collectors.toList());

		return dtolist;	
	}

	/*
	 * @Override public Optional<User> findUserByEmailOptional(String email) {
	 * 
	 * 
	 * return Optional.empty(); }
	 */

	  private UserDto entityToDto(User user) {
//  UserDto userDto = UserDto.builder()
//          .userId(savedUser.getUserId())
//          .name(savedUser.getName())
//          .email(savedUser.getEmail())
//          .password(savedUser.getPassword())
//          .about(savedUser.getAbout())
//          .gender(savedUser.getGender())
//          .imageName(savedUser.getImageName())
//          .build();
  return mapper.map(user, UserDto.class);

}

private User dtoToEntity(UserDto userDto) {
//  User user = User.builder()
//          .userId(userDto.getUserId())
//          .name(userDto.getName())
//          .email(userDto.getEmail())
//          .password(userDto.getPassword())
//          .about(userDto.getAbout())
//          .gender(userDto.getGender())
//          .imageName(userDto.getImageName())
//          .build();
  return mapper.map(userDto, User.class);
}


}
