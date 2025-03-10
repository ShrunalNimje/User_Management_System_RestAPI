package my.mood.UserManagementSystem.User_Management_System.User;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	UserRepository repository;
	PasswordEncoder encoder;
	
	public UserController(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
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
	
	@PostMapping("/users/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userDTO) {
		Optional<User_Entity> existingUser = repository.findByName(userDTO.getName());
		
		if(existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists!");
		}
		
		User_Entity users = new User_Entity();
		
		users.setName(userDTO.getName());
		users.setEmail(userDTO.getEmail());
		users.setPassword(encoder.encode(userDTO.getPassword()));
		users.setRole(User_Role.valueOf(userDTO.getRole().toUpperCase()));
		
		repository.save(users);
		
		return ResponseEntity.ok("User registered successfully with role: " + users.getRole());
	}
	
	@GetMapping("/users/profile")
	public String userProfile() {
		return "Welcome to User Profile!";
	}
	
	
}
