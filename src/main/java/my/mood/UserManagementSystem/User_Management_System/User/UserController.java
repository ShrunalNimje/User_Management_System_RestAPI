package my.mood.UserManagementSystem.User_Management_System.User;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	UserRepository repository;
	PasswordEncoder encoder;
	
	public UserController(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@DeleteMapping("/user/delete/")
	public ResponseEntity<?> deleteUser(Principal principal) {
		User_Entity existingUser = repository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		repository.delete(existingUser);
		
		return ResponseEntity.ok("User account deleted successfully!");
	}
	
	@PutMapping("/user/update/")
	public ResponseEntity<?> updateUser(Principal principal, @RequestBody User_Entity updatedUser) {
		User_Entity existingUser = repository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		if(updatedUser.getName() != null) {
			existingUser.setName(updatedUser.getName());
		}
		
		if(updatedUser.getPassword() != null) {
			existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
		}
		
		repository.save(existingUser);
		
		return ResponseEntity.ok("User account updated successfully!");
	}
	
	@GetMapping("/user/profile/")
	public String userProfile() {
		return "Welcome to User Profile!";
	}
	
}
