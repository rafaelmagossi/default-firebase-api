package com.magossi.defaultfirebase.resource;

import com.magossi.defaultfirebase.model.User;
import com.magossi.defaultfirebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	public UserService userService;

	@GetMapping()
	public ResponseEntity<List<User>> findAll()  {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") String id)  {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PostMapping()
	public ResponseEntity<User> save(@RequestBody User entity)  {
		User savedUser = userService.save(entity);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).body(savedUser);
	}
}
