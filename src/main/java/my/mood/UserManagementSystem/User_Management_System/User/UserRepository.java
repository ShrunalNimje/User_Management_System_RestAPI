package my.mood.UserManagementSystem.User_Management_System.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User_Entity, Integer>{
	
}
