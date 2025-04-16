package my.mood.UserManagementSystem.User_Management_System.Entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import my.mood.UserManagementSystem.User_Management_System.Role.User_Role;

@Entity
public class User_Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Name cannot be empty!")
	@Size(min = 5, message = "Name cannot be less than 5 characters!")
	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	@Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid and contain a domain!")
	@NotBlank(message = "Email cannot be empty!")
	private String email;
	
	@Size(min = 8, message = "Password cannot be less than 8 characters!")
	@NotBlank(message = "Password cannot be empty!")
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private User_Role role;
	
	public User_Entity() {
		
	}
	
	public User_Entity(int id, String name, String email, String password, User_Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User_Entity [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "]";
	}
	
	// This will encrypt passwords before saving them in the database
	public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
	
}
