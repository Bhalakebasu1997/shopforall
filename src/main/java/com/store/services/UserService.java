package com.store.services;

import java.util.List;

import com.store.Dtos.PageableResponse;
import com.store.Dtos.UserDto;

public interface UserService {
	
	
	//create
    UserDto createUser(UserDto userDto);


    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);


    //get all users
 //   List<UserDto> getAllUser(pageNumber, pagesize);


    //get single user by id
    UserDto getUserById(String userId);

    //get  single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);


    PageableResponse<UserDto> getAllUser(int pageNumber, int pagesize, String sortBy, String sortDir);

    //other user specific features

  //  Optional<User> findUserByEmailOptional(String email);

}
