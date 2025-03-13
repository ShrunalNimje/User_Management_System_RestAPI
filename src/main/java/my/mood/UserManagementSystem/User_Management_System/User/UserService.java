package my.mood.UserManagementSystem.User_Management_System.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private static List<User_Entity> users = new ArrayList<User_Entity>();
	
	private static int count = 0;
	
	static {
		users.add(new User_Entity(++count, "Shrunal", "sn@gmail.com", "sn123", User_Role.ROLE_ADMIN));
		users.add(new User_Entity(++count, "Ritik", "rn@gmail.com", "rn123", User_Role.ROLE_USER));
		
	}
	
	// Retrieve all users
	public List<User_Entity> retrieveAllUser() {
		return users;
	}
	
	// Retrieve a specific user by id
	public User_Entity retrieveSpecificUser(int id) {
		return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("User not found with id = " + id));
	}
	
	// delete a specific user by id
	public boolean deleteSpecificUser(int id) {
		return users.removeIf(user -> user.getId() == id);
	}
	
	// add a specific user by id
	public User_Entity addSpecificUser(User_Entity user) {
		user.setId(++count);
		users.add(user);
		return user;
	}
	
	// update a specific user by id
	 public Optional<User_Entity> updateSpecificUser(int id, User_Entity updatedUser) {
        for (User_Entity user : users) {
        	
            if (user.getId() == id) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                user.setRole(updatedUser.getRole());
                return Optional.of(user);
            }
        }
        
        return Optional.empty();  
    }
	
}
