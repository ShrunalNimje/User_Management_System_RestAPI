package my.mood.UserManagementSystem.User_Management_System.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import my.mood.UserManagementSystem.User_Management_System.DTO.UserCreateDTO;
import my.mood.UserManagementSystem.User_Management_System.DTO.UserUpdateDTO;
import my.mood.UserManagementSystem.User_Management_System.Entity.User_Entity;
import my.mood.UserManagementSystem.User_Management_System.Exception.UserNotFoundException;
import my.mood.UserManagementSystem.User_Management_System.Repository.AdminRepository;
import my.mood.UserManagementSystem.User_Management_System.Role.User_Role;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@Mock
	AdminRepository adminRepository;
	
	@Mock
	PasswordEncoder encoder;
	
	@InjectMocks
	AdminService adminService;
	
	List<User_Entity> users = null;
	
	@BeforeEach
	public void init() {
		
		System.out.println("Before Each!");
		
		users = new ArrayList<User_Entity>();
		
		users.add(new User_Entity(1, "admin", "admin@gmail.com", "admin123", User_Role.ROLE_ADMIN));
		users.add(new User_Entity(2, "user", "user@gmail.com", "user123", User_Role.ROLE_USER));
		
	}
	
	@Test
	public void shouldRetrieveAllUsers_WhenRetrieveAllUserIsCalled() {
		
		System.out.println("Retrieve all user!");
				
		Pageable pageable = PageRequest.of(0, 2);
		Page<User_Entity> page = new PageImpl<User_Entity>(users, pageable, users.size());
		
		Mockito.when(adminRepository.findAll(pageable)).thenReturn(page);
		
		Page<User_Entity> result = adminService.retrieveAllUser(pageable);
		
		assertNotNull(result);
		assertEquals(2, result.getNumberOfElements());
		assertEquals(2, result.getContent().get(1).getId());
		
	}
	
	@Test
	public void shouldNotRetrieveAllUsers_WhenUserIsUnauthorized() {
		
		System.out.println("Negative: Unauthorized User or Admin!");
				
		Pageable pageable = PageRequest.of(0, 2);
		Page<User_Entity> page = new PageImpl<User_Entity>(new ArrayList<>(), pageable, 0);
		
		Mockito.when(adminRepository.findAll(pageable)).thenReturn(page);
		
		Page<User_Entity> result = adminService.retrieveAllUser(pageable);
		
		assertNotNull(result);
		assertEquals(0, result.getNumberOfElements());
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldRetrieveUser_WhenRetrieveUserIsCalled() {
		
		System.out.println("Retrieve an user!");
		
		Mockito.when(adminRepository.findById(2)).thenReturn(Optional.of(users.get(1)));
		
		Optional<User_Entity> result = adminService.retrieveSpecificUser(2);
		
		assertNotNull(result);
		assertEquals(2, result.get().getId());
		
	}
	
	@Test
	public void shouldNotRetrieveUser_WhenUserIsNotExist() {
		
		System.out.println("Negative: User does not exist!");
		
		Mockito.when(adminRepository.findById(99)).thenReturn(Optional.empty());
		
		Optional<User_Entity> result = adminService.retrieveSpecificUser(99);
		
		assertNotNull(result);
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldRetrieveAdminProfile_WhenRetrieveAdminIsCalled() {
		
		System.out.println("Retrieve an admin profile!");
		
		Principal principal = Mockito.mock(Principal.class);
		
		Mockito.when(principal.getName()).thenReturn("admin@gmail.com");
		Mockito.when(adminRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(users.get(1)));
		
		User_Entity result = adminService.retrieveAdmin(principal);
		
		assertNotNull(result);
		assertEquals(2, result.getId());
		
	}
	
	@Test
	public void shouldNotRetrieveAdminProfile_WhenAdminIsNotExist() {
		
		System.out.println("Negative: Admin profile not exist!");
		
		Principal principal = Mockito.mock(Principal.class);
		
		Mockito.when(principal.getName()).thenReturn("admin@gmail.com");
		Mockito.when(adminRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.empty());
				
		assertThrows(UserNotFoundException.class, ()-> {
			adminService.retrieveAdmin(principal);
		});
		
	}
	
	@Test
	public void shouldDeleteUser_WhenDeleteUserByIdIsCalled() {
		
		System.out.println("Delete an user by unique id!");
		
		Mockito.when(adminRepository.findById(2)).thenReturn(Optional.of(users.get(1)));
		
		doNothing().when(adminRepository).deleteById(2);
		
		ResponseEntity<String> result = adminService.deleteSpecificUser(2);
		
		assertNotNull(result);
		assertEquals("User Deleted successfully with id = 2", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
	
	@Test
	public void shouldNotDeleteUser_WhenUserIsInvalid() {
		
		System.out.println("Negative: Invalid user id!");
		
		Mockito.when(adminRepository.findById(99)).thenReturn(Optional.empty());
				
		assertThrows(UserNotFoundException.class, ()-> {
			adminService.deleteSpecificUser(99);
		});
		
	}
	
	@Test
	public void shouldDeleteAllUser_WhenDeleteAllUserIsCalled() {
		
		System.out.println("Delete all user!");
				
		doNothing().when(adminRepository).deleteAll();
		
		ResponseEntity<String> result = adminService.deleteAllusers();
		
		assertNotNull(result);
		assertEquals("All Users Deleted successfully", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
	
	@Test
	public void shouldNotDeleteUser_WhenAdminIsInvalid() {
		
		System.out.println("Negative: Invalid admin!");
		
		Mockito.doThrow(new RuntimeException("Admin invalid or DB error!")).when(adminRepository).deleteAll();
				
		try {
			adminService.deleteAllusers();
		}
		catch (RuntimeException e) {
			assertEquals("Admin invalid or DB error!", e.getMessage());
		}
		
	}
	
	@Test
	public void shouldCreateNewUser_WhenAddSpecificUserIsCalled() {
		
		System.out.println("Add a new user!");
				
		UserCreateDTO addUser = new UserCreateDTO("user", "user@gmail.com", "user123", User_Role.ROLE_USER);
		
		User_Entity user = new User_Entity();
		user.setEmail(addUser.getEmail());
		user.setName(addUser.getName());
		user.setPassword(addUser.getPassword());
		user.setRole(addUser.getRole());
		
		Mockito.when(adminRepository.save(Mockito.any(User_Entity.class))).thenReturn(user);
		
		ResponseEntity<String> result = adminService.addSpecificUser(addUser);
		
		assertNotNull(result);
		assertEquals("User Created successfully with id = " + user.getId(), result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
	
	@Test
	public void shouldUpdateExistingUser_WhenUpdateUserIsCalled() {
		
		System.out.println("Update an existing user!");
		
		UserUpdateDTO userUpdate = new UserUpdateDTO();
		userUpdate.setPassword("new pasword");
		
		User_Entity existingUser = users.get(0);
		existingUser.setPassword(userUpdate.getPassword());
		
		Mockito.when(adminRepository.findById(1)).thenReturn(Optional.of(existingUser));
		Mockito.when(adminRepository.save(existingUser)).thenReturn(existingUser);
		
		ResponseEntity<String> result = adminService.updateSpecificUser(1, userUpdate);
		
		assertNotNull(result);
		assertEquals("User updated successfully with id = 1", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
	
	@Test
	public void shouldNotUpdateExistingUser_WhenUserIdIsInvalid() {
		
		System.out.println("Negative: User id in invalid!");
		
		UserUpdateDTO userUpdate = new UserUpdateDTO();
		
		Mockito.when(adminRepository.findById(99)).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> {
			adminService.updateSpecificUser(99, userUpdate);
		});
		
	}
	
}
