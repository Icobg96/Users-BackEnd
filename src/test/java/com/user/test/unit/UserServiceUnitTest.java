package com.user.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.dto.RoleDto;
import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.service.UserRestService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceUnitTest {

	@Autowired
	private UserRestService userService;
	@MockBean
	private UserRepository userRepository;
	
	private User user1;
	private User user2;
	private UserDto dtoUser;
	
	@Before
	public void init() {
		
		user1 = new User();
		user1.setId(1l);
		user1.setUserName("ivan43");
		user1.setFirstName("Ivan");
		user1.setLastName("ivanov");
		user1.setAge(34);
		
		user2 = new User();
		user2.setId(2l);
		user2.setUserName("tenata_56");
		user2.setFirstName("Stoqn");
		user2.setLastName("Petkov");
		user2.setAge(34);	
		
		dtoUser = new UserDto();
		dtoUser.setId(1l);
		dtoUser.setUserName("ivan43");
		dtoUser.setFirstName("Ivan");
		dtoUser.setLastName("ivanov");
		dtoUser.setAge(34);
		
	}
	
	@Test
	public void createUserTest() {
		
		Mockito.when(userRepository.save(user1)).thenReturn(user1);
		
		ResponseEntity<Long>response = userService.createUser(dtoUser);
		
	    assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(dtoUser.getId(), response.getBody());
		
	}
	@Test
	public void updateUser() {
		
		Mockito.when(userRepository.findOne(Mockito.anyLong())).thenReturn(user1);
		
		dtoUser.setUserName("ivan4344");
		dtoUser.setFirstName("Ivannn");
		dtoUser.setLastName("ivanovvv");
		dtoUser.setAge(54);
		
		ResponseEntity<Object>response = userService.updateUser(dtoUser);
		
		assertNotNull(response.getBody());

	    assertEquals(response.getStatusCode(), HttpStatus.OK);
	    assertEquals("user is updated successsfully", response.getBody());
		
	}
	@Test
	public void updateRoleToUserTest() {
		
		Mockito.when(userRepository.findOne(Mockito.anyLong())).thenReturn(user1);
		
		HashSet<RoleDto> roles = new HashSet<RoleDto>();
		roles.add(new RoleDto(1l, "User", "user role"));
		dtoUser.setRoles(roles);
		
        ResponseEntity<Object>response = userService.updateRoleToUser(dtoUser);
		
		assertNotNull(response.getBody());

	    assertEquals(response.getStatusCode(), HttpStatus.OK);
	    assertEquals("roles of the user are udated successfully", response.getBody());
		
	}
	
	@Test
	public void getUserTest() {

		Mockito.when(userRepository.findOne(Mockito.anyLong())).thenReturn(user1);
		UserDto dtoUser = userService.getUser(user1.getId());
			    
		assertNotNull(dtoUser);
		
		assertEquals(dtoUser.getUserName(), user1.getUserName());
		assertEquals(dtoUser.getFirstName(), user1.getFirstName());
		assertEquals(dtoUser.getLastName(), user1.getLastName());
		assertEquals(dtoUser.getAge(), user1.getAge());
	}	
	@Test
	public void getAllUsersTest() {
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		Mockito.when(userRepository.findAll()).thenReturn(users);
	    
		ArrayList<UserDto> dtoUsers = (ArrayList<UserDto>) userService.getUsers();
		
		assertNotNull(dtoUsers);
		assertEquals(users.size(), dtoUsers.size());
	}
	@Test
	public void deleteUserTest() {
		
		ResponseEntity<Object>response = userService.deleteUser(user1.getId());
		
		assertNotNull(response.getBody());

	    assertEquals(response.getStatusCode(), HttpStatus.OK);
	    assertEquals("User is deleted successsfully", response.getBody());
	}
}
