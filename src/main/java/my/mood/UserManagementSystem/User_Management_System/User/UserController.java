package my.mood.UserManagementSystem.User_Management_System.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	UserRepository repository;
	PasswordEncoder encoder;
	
	public UserController(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@GetMapping("/user/profile")
	public String userProfile() {
		return "Welcome to User Profile!";
	}
	
}
