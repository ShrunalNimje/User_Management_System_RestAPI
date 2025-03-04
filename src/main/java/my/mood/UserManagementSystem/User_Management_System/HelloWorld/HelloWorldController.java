package my.mood.UserManagementSystem.User_Management_System.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@GetMapping("/HelloWorld")
	public String helloWorld() {
		return "Welcome to User Management System RestAPI";
	}
	
}
