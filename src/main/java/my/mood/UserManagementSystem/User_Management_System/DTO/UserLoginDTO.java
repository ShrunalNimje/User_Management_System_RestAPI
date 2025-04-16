package my.mood.UserManagementSystem.User_Management_System.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "Email cannot be empty!")
	private String email;
	
	@NotBlank(message = "Password cannot be empty!")
	@Column(nullable = false)
	private String password;
	
	public UserLoginDTO() {
		
	}
	
	public UserLoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
