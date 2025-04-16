package my.mood.UserManagementSystem.User_Management_System.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserCreateDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Service.AdminService;

@Tag(name = "Admin API", description = "Admin can manages all users profile including Update, Delete, Create and Retrieve")
@RestController
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	PasswordEncoder encoder;
	
	public AdminController(AdminService adminService, PasswordEncoder encoder) {
		this.adminService = adminService;
		this.encoder = encoder;
	}
	
	@Operation(summary = "Get all users", description = "Get all user with thier role and can accessible by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/users/")
	public Page<User_Entity> retrieveUsers(Pageable pageable) {
		return adminService.retrieveAllUser(pageable);
	}
	
	@Operation(summary = "Get an user", description = "Get an user with thier role and can accessible by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/users/{id}/")
	public Optional<User_Entity> retrieveSpecificUser(@Valid @PathVariable int id) {
		return adminService.retrieveSpecificUser(id);
	}
	
	@Operation(summary = "Delete an user", description = "Delete an user and can delete by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/users/delete/{id}/")
	public ResponseEntity<String> deleteUser(@Valid @PathVariable int id) {
		return adminService.deleteSpecificUser(id);
	}
	
	@Operation(summary = "Delete all users", description = "Delete all user and can delete by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/users/delete/")
	public ResponseEntity<String> deleteAll() {
		return adminService.deleteAllusers();
	}
	
	@Operation(summary = "Create new user", description = "Create new user with role and can create by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/users/create/")
	public ResponseEntity<String> addUser(@Valid @RequestBody UserCreateDTO user) {
		return adminService.addSpecificUser(user);
	}
	
	@Operation(summary = "Update an user", description = "Update an user and can updatable by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/users/update/{id}/") 
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDTO user, @Valid @PathVariable int id) {
		return adminService.updateSpecificUser(id, user);
	}
	
	@Operation(summary = "Get admin profile", description = "Get admin profile and can accessible by admin only")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/dashboard/")
	public User_Entity adminDashboard(Principal principal) {
		return adminService.retrieveAdmin(principal);
	}
	
}
