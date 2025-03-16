package my.mood.UserManagementSystem.User_Management_System.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User_Entity, Integer>{

	Optional<User_Entity> findByEmail(String email);
	Optional<User_Entity> findByName(String name);
	
	Page<User_Entity> findAll(Pageable pageable);
}
