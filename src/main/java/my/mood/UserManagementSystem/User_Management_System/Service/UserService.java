package my.mood.UserManagementSystem.User_Management_System.Service;


import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.UserManagementSystem.User_Management_System.DTO.UserLoginDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserRegistrationDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Exception.UserNotFoundException;
import my.mood.UserManagementSystem.User_Management_System.Repository.UserRepository;
import my.mood.UserManagementSystem.User_Management_System.Role.User_Role;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	public UserService(UserRepository userRepository, PasswordEncoder encoder, 
			AuthenticationManager authManager, JWTService jwtService) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.authManager = authManager;
		this.jwtService = jwtService;
	}
	
	// Retrieve a logged-in user
	public User_Entity retrieveUser(Principal principal) {
		return userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));
	}
	
	// delete a logged-in user 
	public ResponseEntity<String> deleteUser(Principal principal) {
		User_Entity existingUser = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new UserNotFoundException("User not found!"));
		
		userRepository.delete(existingUser);
		return ResponseEntity.ok("User account deleted successfully with id = " + existingUser.getId());
	}
	
	// update a logged-in user
	 public ResponseEntity<String> updateUser(Principal principal, UserUpdateDTO updatedUser) {
		 User_Entity existingUser = userRepository.findByEmail(principal.getName())
					.orElseThrow(() -> new UserNotFoundException("User not found!"));
        
        if(updatedUser.getName() != null) {
        	existingUser.setName(updatedUser.getName());
        }
        
        if(updatedUser.getPassword() != null) {
        	existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
        }
        
        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully with id = " + existingUser.getId());
    }
	 
	// Register a new user
	public ResponseEntity<String> RegisterNewUser(UserRegistrationDTO user) {
		
		Optional<User_Entity> existingUser = userRepository.findByEmail(user.getEmail());	
		
		if(existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists!");
		}
		
		User_Entity users = new User_Entity();
		
		users.setName(user.getName());
		users.setEmail(user.getEmail());
		users.setPassword(encoder.encode(user.getPassword()));
		users.setRole(User_Role.ROLE_USER);
		
		userRepository.save(users);
		
		return ResponseEntity.ok("User registered successfully with role: " + users.getRole());
	}

	// login an user
	public String verify(UserLoginDTO user) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}
		
		throw new BadCredentialsException("Invalid email and password");
	}
	
}
