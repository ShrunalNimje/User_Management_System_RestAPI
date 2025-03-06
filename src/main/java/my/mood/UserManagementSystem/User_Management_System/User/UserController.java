package my.mood.UserManagementSystem.User_Management_System.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class UserController {

//	UserService service;
	
	@Autowired
	UserRepository repository;
	
	public UserController(/*UserService service, */ UserRepository repository) {
//		this.service = service;
		this.repository = repository;
	}
	
	@GetMapping("/users")
	public List<User_Entity> retrieveUsers() {
//		return service.retrieveAllUser();
		return repository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User_Entity> retrieveSpecificUser(@PathVariable int id) {
//		return service.retrieveSpecificUser(id);
		return repository.findById(id);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@Valid @PathVariable int id) {
//		service.deleteSpecificUser(id);
		repository.deleteById(id);
	}
	
	@PostMapping("/users")
	public void addUser(@Valid @RequestBody User_Entity user) {
//		service.addSpecificUser(user);
		repository.save(user);
	}
	
	@PutMapping("/users/{id}") 
	public User_Entity updateUser(@Valid @RequestBody User_Entity user, @Valid @PathVariable int id) {
//		service.updateSpecificUser(id, user);
		Optional<User_Entity> existingUser = retrieveSpecificUser(id);
		
		if(existingUser.isPresent()) {
			User_Entity tempUser = existingUser.get();
			
			tempUser.setEmail(user.getEmail());
			tempUser.setName(user.getName());
			tempUser.setPassword(user.getPassword());
			tempUser.setRole(user.getRole());
			
			tempUser.setId(id);
			
			return repository.save(tempUser);
		}
		else {
			throw new RuntimeException("User not found with id = " + id);
		}
	}
	
}
