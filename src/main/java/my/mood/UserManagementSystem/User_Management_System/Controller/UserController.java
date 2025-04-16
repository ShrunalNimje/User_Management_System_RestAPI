package my.mood.UserManagementSystem.User_Management_System.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserLoginDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserRegistrationDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Service.UserService;

@Tag(name = "User API", description = "User can manages thier profile including Update, Delete and Retrieve")
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;
	
	public UserController(UserService userService, PasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}
	
	@Operation(summary = "Delete an user", description = "Delete an user with thier role and can delete by user only")
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/user/profile/delete/")
	public ResponseEntity<String> deleteUser(Principal principal) {
		return userService.deleteUser(principal);
	}
	
	@Operation(summary = "Update an user", description = "Update an user and can updatable by user only")
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/user/profile/update/")
	public ResponseEntity<String> updateUser(Principal principal, @RequestBody UserUpdateDTO updatedUser) {
		return userService.updateUser(principal, updatedUser);
	}
	
	@Operation(summary = "Register new user", description = "Register new user with role 'ROLE_USER'")
	@PostMapping("/register/")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO user) {
		return userService.RegisterNewUser(user);
	}
	
	@Operation(summary = "Get an user profile", description = "Get an user profile with thier role and can retrieve by user only")
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user/profile/")
	public User_Entity userProfile(Principal principal) {
		return userService.retrieveUser(principal);
	}
	
	@Operation(summary = "Login an user", description = "Login an user  with right credentials")
	@PostMapping("/login/")
	public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
		String token = userService.verify(user);
		return ResponseEntity.ok(token);
	}
	
}
