package mx.ipn.cic.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ipn.cic.store.entities.UserEntity;
import mx.ipn.cic.store.services.UserService;

@RestController
@RequestMapping(path="/rest/user")
public class UserRESTController {

	@Autowired
	private UserService userService;

	@GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserEntity>> all() {
		List<UserEntity> users = this.userService.all();
		ResponseEntity<List<UserEntity>> response = new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
		return response;
	}

	@GetMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> byId(@PathVariable(name="id") Integer id) {
		UserEntity user = this.userService.findById(id);
		if (user != null) {
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path="/save", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
		ResponseEntity<UserEntity> response;
		try {
			this.userService.save(user);
			response = new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			response = new ResponseEntity<UserEntity>(HttpStatus.NOT_ACCEPTABLE);
		}
		return response;
	}

	@DeleteMapping(path="/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(name="id") Integer id) {
		ResponseEntity<Boolean> response;
		try {
			boolean deleted = this.userService.delete(id);
			response = new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<Boolean>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}

	@PutMapping(path="/save/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> edit(
			@RequestBody UserEntity user,
			@PathVariable(name="id") Integer id) {
		user.setId(id);
		ResponseEntity<UserEntity> response;
		try {
			this.userService.save(user);
			response = new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<UserEntity>(HttpStatus.NOT_MODIFIED);
		}
		return response;
	}

	@GetMapping(path="/find/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserEntity>> byName(@PathVariable(name="name") String name) {
		ResponseEntity<List<UserEntity>> response;
		try {
			List<UserEntity> foundUsers = this.userService.findByName(name);
			response = new ResponseEntity<List<UserEntity>>(foundUsers, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<List<UserEntity>>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}
}
