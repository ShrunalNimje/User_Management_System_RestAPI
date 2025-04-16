package my.mood.UserManagementSystem.User_Management_System.Service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.UserManagementSystem.User_Management_System.DTO.UserCreateDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Exception.UserNotFoundException;
import my.mood.UserManagementSystem.User_Management_System.Repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	public AdminService(AdminRepository adminRepository, PasswordEncoder encoder) {
		this.adminRepository = adminRepository;
		this.encoder = encoder;
	}
	
	// Retrieve all users
	public Page<User_Entity> retrieveAllUser(Pageable pageable) {
		return adminRepository.findAll(pageable);
	}
	
	// Retrieve a specific user by id
	public Optional<User_Entity> retrieveSpecificUser(int id) {
		return adminRepository.findById(id);
	}
	
	// Retrieve a specific user by id
	public User_Entity retrieveAdmin(Principal principal) {
		return adminRepository.findByEmail(principal.getName())
				.orElseThrow(()-> new UserNotFoundException("Admin not found!"));
	}
	
	// delete a specific user by id
	public ResponseEntity<String> deleteSpecificUser(int id) {
		adminRepository.findById(id)
		.orElseThrow( ()-> new UserNotFoundException("User not found with id = " + id));
		
		adminRepository.deleteById(id);
		
		return ResponseEntity.ok("User Deleted successfully with id = " + id);	
	}
	
	// delete all users
	public ResponseEntity<String> deleteAllusers() {
		adminRepository.deleteAll();
		
		return ResponseEntity.ok("All Users Deleted successfully");	
	}
	
	// add a specific user
	public ResponseEntity<String> addSpecificUser(UserCreateDTO user) {
		User_Entity createUser = new User_Entity();
		
		createUser.setEmail(user.getEmail());
		createUser.setName(user.getName());
		createUser.setPassword(encoder.encode(user.getPassword()));
		createUser.setRole(user.getRole());

		adminRepository.save(createUser);
		return ResponseEntity.ok("User Created successfully with id = " + createUser.getId());	

	}
	
	// update a specific user by id
	 public ResponseEntity<String> updateSpecificUser(int id, UserUpdateDTO updatedUser) {
        User_Entity existingUser = retrieveSpecificUser(id)
        		.orElseThrow(() -> new UserNotFoundException("user not found with id = " + id));
        
        if(updatedUser.getName() != null) {
        	existingUser.setName(updatedUser.getName());
        }
        
        if(updatedUser.getPassword() != null) {
        	existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
        }
        
        adminRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully with id = " + id);
    }

}
