package com.YourBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YourBook.dto.UserDto;
import com.YourBook.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/add-user")
	public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
		String s = userService.addUser(userDto);
		return ResponseEntity.ok(s);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDto userDto){
		String s = userService.loginUser(userDto);
		return ResponseEntity.ok(s);
	}

}
