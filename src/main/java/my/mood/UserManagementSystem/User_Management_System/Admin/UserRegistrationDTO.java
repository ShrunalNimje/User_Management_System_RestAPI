package my.mood.UserManagementSystem.User_Management_System.Admin;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import my.mood.UserManagementSystem.User_Management_System.User.User_Role;

public class UserRegistrationDTO {
	
	@NotBlank(message = "Name cannot be empty!")
	@Size(min = 5, message = "Name cannot be less than 5 characters!")
	private String name;
	
	@Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid and contain a domain!")
	@NotBlank(message = "Email cannot be empty!")
	private String email;
	
	@Size(min = 8, message = "Password cannot be less than 8 characters!")
	@NotBlank(message = "Password cannot be empty!")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private User_Role role;
    
    public UserRegistrationDTO() {
    	
    }
    
	public UserRegistrationDTO(String name, String password, User_Role role) {
		super();
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
