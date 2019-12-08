package com.magossi.defaultfirebase.service;

import com.magossi.defaultfirebase.model.User;
import com.magossi.defaultfirebase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll(){
		return userRepository.findAll();
	}

	public User findById(String id){
		return userRepository.find(id);
	}

	public User save(User entity){
		return userRepository.save(entity);
	}
}
