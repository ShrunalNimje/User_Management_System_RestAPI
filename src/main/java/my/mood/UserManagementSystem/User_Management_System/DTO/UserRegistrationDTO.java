package my.mood.UserManagementSystem.User_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
    
    public UserRegistrationDTO() {
    	
    }
    
	public UserRegistrationDTO(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
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
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
