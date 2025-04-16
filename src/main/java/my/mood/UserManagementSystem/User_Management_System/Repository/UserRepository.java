package my.mood.UserManagementSystem.User_Management_System.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;

@Repository
public interface UserRepository extends JpaRepository<User_Entity, Integer>{

	Optional<User_Entity> findByEmail(String name);

}
