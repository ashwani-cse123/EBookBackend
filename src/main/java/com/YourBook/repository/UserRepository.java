package com.YourBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.YourBook.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);

}
