package my.mood.UserManagementSystem.User_Management_System.Admin;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import my.mood.UserManagementSystem.User_Management_System.Exception.UserNotFoundException;
import my.mood.UserManagementSystem.User_Management_System.User.UserRepository;
import my.mood.UserManagementSystem.User_Management_System.User.User_Entity;

@RestController
public class AdminController {

	UserRepository repository;
	PasswordEncoder encoder;
	
	public AdminController(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@GetMapping("/admin/users/")
	public Page<User_Entity> retrieveUsers(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@GetMapping("/admin/users/{id}/")
	public Optional<User_Entity> retrieveSpecificUser(@Valid @PathVariable int id) {
		return repository.findById(id);
	}
	
	@DeleteMapping("/admin/users/{id}/")
	public ResponseEntity<String> deleteUser(@Valid @PathVariable int id) {
		repository.deleteById(id);
		
		return ResponseEntity.ok("User deleted successfully with id :" + id);
	}
	
	@DeleteMapping("/admin/users/")
	public ResponseEntity<String> deleteAll() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All Users are deleted successfully!");
	}
	
	@PostMapping("/admin/users/")
	public ResponseEntity<String> addUser(@Valid @RequestBody User_Entity user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repository.save(user);
		
		return ResponseEntity.ok("User created successfully with role :" + user.getRole());
	}
	
	@PutMapping("/admin/users/{id}/") 
	public ResponseEntity<String> updateUser(@Valid @RequestBody User_Entity user, @Valid @PathVariable int id) {
		Optional<User_Entity> existingUser = retrieveSpecificUser(id);
		
		if(existingUser.isPresent()) {
			User_Entity tempUser = existingUser.get();
			
			tempUser.setName(user.getName());
			tempUser.setPassword(encoder.encode(user.getPassword()));
			tempUser.setRole(user.getRole());
			
			tempUser.setId(id);
			
			repository.save(tempUser);
			
			return ResponseEntity.ok("User updated successfully with role :" + tempUser.getRole());
		}
		else {
			throw new UserNotFoundException("User not found with id = " + id);
		}
	}
	
	@PostMapping("/register/")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userDTO) {
		Optional<User_Entity> existingUser = repository.findByEmail(userDTO.getEmail());
		
		if(existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists!");
		}
		
		User_Entity users = new User_Entity();
		
		users.setName(userDTO.getName());
		users.setEmail(userDTO.getEmail());
		users.setPassword(encoder.encode(userDTO.getPassword()));
		users.setRole((userDTO.getRole()));
		
		repository.save(users);
		
		return ResponseEntity.ok("User registered successfully with role: " + users.getRole());
	}
	
	@GetMapping("/admin/dashboard/")
	public String adminDashboard() {
		return "Welocome to Admin Dashboard!";
	}
	
}
