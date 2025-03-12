package my.mood.UserManagementSystem.User_Management_System.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import my.mood.UserManagementSystem.User_Management_System.User.UserRepository;
import my.mood.UserManagementSystem.User_Management_System.User.User_Entity;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository repository;
	
	public CustomUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User_Entity user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email = " + email));
		
		return User.withUsername(user.getEmail())
                .password(user.getPassword()) // The password must already be encoded!
                .roles(user.getRole().name()) // Convert Role Enum to String
                .build();
	}

}
