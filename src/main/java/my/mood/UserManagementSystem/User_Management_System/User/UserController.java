package my.mood.UserManagementSystem.User_Management_System.User;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User_Entity> retrieveUsers() {
		return service.retrieveAllUser();
	}
	
	@GetMapping("/users/{id}")
	public User_Entity retrieveSpecificUser(@PathVariable int id) {
		return service.retrieveSpecificUser(id);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteSpecificUser(id);
	}
	
	@PostMapping("/users")
	public void addUser(@RequestBody User_Entity user) {
		service.addSpecificUser(user);
	}
	
	@PutMapping("/users/{id}") 
	public void updateUser(@RequestBody User_Entity user, @PathVariable int id) {
		service.updateSpecificUser(id, user);
	}
	
}
