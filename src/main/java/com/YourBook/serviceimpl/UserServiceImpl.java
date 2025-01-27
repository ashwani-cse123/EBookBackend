package com.YourBook.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.YourBook.dto.UserDto;
import com.YourBook.entity.Role;
import com.YourBook.entity.User;
import com.YourBook.repository.UserRepository;
import com.YourBook.service.UserService;

@Component
public class UserServiceImpl implements  UserService{
	@Autowired
	private UserRepository userRespository;

	@Override
	public String addUser(UserDto userDto) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setRoleName("USER");
		
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setPassword(userDto.getPassword());
		
		user.setRole(role);
		role.setUser(user);
		
		userRespository.save(user);
		return "User save successfully with their role";
	}

	@Override
	public String loginUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = userRespository.findByUsername(userDto.getUsername());
		if(user != null) {
			if(user.getPassword().equalsIgnoreCase(userDto.getPassword())) {
				return user.getRole().getRoleName();
			}else {
				return "Password Incorrect";
			}
		}else {
			return "SignUp";
		}
	}

}
