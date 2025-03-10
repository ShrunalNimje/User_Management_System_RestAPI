package my.mood.UserManagementSystem.User_Management_System.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "Welocome to Admin Dashboard!";
	}
	
}
