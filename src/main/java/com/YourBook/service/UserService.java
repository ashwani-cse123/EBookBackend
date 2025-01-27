package com.YourBook.service;

import com.YourBook.dto.UserDto;

public interface UserService {
	public String addUser(UserDto userDto);
	public String loginUser(UserDto userDto);
	

}
