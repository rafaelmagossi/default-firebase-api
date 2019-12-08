package com.magossi.defaultfirebase.repository;

import com.magossi.defaultfirebase.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository extends FirestoreRepository<User>{

	public UserRepository(){
		super(User.class, "users");
	}

	public List<User> findAll(){
		return super.findAll();
	}

	public User find(String id){
		return super.find(id);
	}

	public User save(User user){
		return super.save(user);
	}
}
