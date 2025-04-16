package my.mood.UserManagementSystem.User_Management_System.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import my.mood.UserManagementSystem.User_Management_System.DTO.UserLoginDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserRegistrationDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Exception.UserNotFoundException;
import my.mood.UserManagementSystem.User_Management_System.Repository.UserRepository;
import my.mood.UserManagementSystem.User_Management_System.Role.User_Role;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder encoder;
	
	@Mock
	AuthenticationManager authenticationManager;
	
	@Mock
	JWTService jwtService;
	
	@InjectMocks
	UserService userService;
	
	User_Entity user = null;
	
	@BeforeEach
	public void init() {

		System.out.println("Before Each!");
		
		user = new User_Entity(1, "dummy", "d@gmail.com", "dummy123", User_Role.ROLE_USER);
		
	}
	
	@Test
	public void shouldRetrieveLoggedInUser_WhenRetrieveUserIsCalled() {
		
		System.out.println("Retrieve a logged-in user!");
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.of(user));
		
		User_Entity result = userService.retrieveUser(principal);
		
		assertNotNull(result);
		assertEquals(user.getName(), result.getName());
		assertEquals(user.getId(), result.getId());
		
	}
	
	@Test
	public void shouldNotRetrieveLoggedInUser_WhenUserIsInvalid() {
		
		System.out.println("Negative: User not found!");

		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> {
	        userService.retrieveUser(principal);
	    });
		
	}
	
	@Test
	public void shouldDeleteLoggedInUser_WhenDeleteUserIsCalled() {
		
		System.out.println("Delete a logged-in user!");
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.of(user));
		doNothing().when(userRepository).delete(user);
		
		ResponseEntity<String> result = userService.deleteUser(principal);
		
		assertNotNull(result);
		assertEquals("User account deleted successfully with id = 1", result.getBody());
		
	}
	
	@Test
	public void shouldNotDeleteLoggedInUser_WhenUserIsInvalid() {
		
		System.out.println("Negative: User not found!");

		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> {
	        userService.deleteUser(principal);
	    });
		
	}
	
	@Test
	public void shouldUpdateLoggedInUser_WhenUpdateUserIsCalled() {
		
		System.out.println("Update a logged-in user!");
		
		UserUpdateDTO updateUser = new UserUpdateDTO();
		updateUser.setName("new dummy");
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.of(user));
		
		ResponseEntity<String> result = userService.updateUser(principal, updateUser);
		
		assertNotNull(result);
		assertEquals("User updated successfully with id = 1", result.getBody());
		
	}
	
	@Test
	public void shouldNotUpdateLoggedInUser_WhenUserIsInvalid() {
		
		System.out.println("Negative: User not found!");
		
		UserUpdateDTO updateUser = new UserUpdateDTO();
		updateUser.setName("new dummy");
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("d@gmail.com");
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> {
	        userService.updateUser(principal, updateUser);
	    });
		
	}
	
	@Test
	public void shouldRegisterNewUser_WhenRegisterUserIsCalled() {
		
		System.out.println("Register new user!");
		
		UserRegistrationDTO registerUser = new UserRegistrationDTO("dummy", "d@gmail.com", "dummy123");
		
		User_Entity user = new User_Entity();
		user.setId(1);
		user.setEmail(registerUser.getEmail());
		user.setName(registerUser.getName());
		user.setPassword(registerUser.getPassword());
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(Mockito.any(User_Entity.class))).thenReturn(user);

		ResponseEntity<String> result = userService.RegisterNewUser(registerUser);
		
		assertNotNull(result);
		assertEquals("User registered successfully with role: ROLE_USER", result.getBody());
		
	}
	
	@Test
	public void shouldNotRegisterNewUser_WhenUserIsExist() {
		
		System.out.println("Negative: User already exist!");
		
		UserRegistrationDTO registerUser = new UserRegistrationDTO("dummy", "d@gmail.com", "dummy123");
		
		User_Entity user = new User_Entity();
		user.setEmail(registerUser.getEmail());
		
		Mockito.when(userRepository.findByEmail("d@gmail.com")).thenReturn(Optional.of(user));

		ResponseEntity<String> result = userService.RegisterNewUser(registerUser);
		
		assertNotNull(result);
		assertEquals("Username already exists!", result.getBody());
		
	}
	
	@Test
	public void shouldLoginSuccessfully_WhenLoginIsCalled() {
		
		UserLoginDTO user = new UserLoginDTO("d@gmail.com", "dummy123");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		
		Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		Mockito.when(authentication.isAuthenticated()).thenReturn(true);
		Mockito.when(jwtService.generateToken("d@gmail.com")).thenReturn("dummy-token");
		
		String result = userService.verify(user);
		
		assertNotNull(result);
	    assertEquals("dummy-token", result);
	    
	}
	
	@Test
	public void shouldNotLoginSuccessfully_WhenCredentialsIsInvalid() {
		
		UserLoginDTO user = new UserLoginDTO("d@gmail.com", "dummy123");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		
		Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		Mockito.when(authentication.isAuthenticated()).thenReturn(false);
		
		assertThrows(BadCredentialsException.class, () -> {
	        userService.verify(user);
	    });
	    
	}
	
}
