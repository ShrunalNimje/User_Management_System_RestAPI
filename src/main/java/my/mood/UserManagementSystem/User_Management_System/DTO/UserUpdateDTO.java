package my.mood.UserManagementSystem.User_Management_System.DTO;

import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
	
	@Size(min = 5, message = "Name cannot be less than 5 characters!")
	private String name;
		
	@Size(min = 8, message = "Password cannot be less than 8 characters!")
	private String password;
	
	public UserUpdateDTO() {
		
	}
	
	public UserUpdateDTO(String name, String password) {
		super();
		this.name = name;
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

}
