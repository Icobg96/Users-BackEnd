package com.user.test.Integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.dto.RoleDto;
import com.user.dto.UserDto;
import com.user.service.RoleRestService;
import com.user.service.UserRestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {
	
	@Autowired
	private UserRestService userService;
	@Autowired
	private RoleRestService roleService;
	
	@Before
	public void inti() {
		UserDto dtoUser1= new UserDto();
		dtoUser1.setUserName("ivan43");
		dtoUser1.setFirstName("Ivan");
		dtoUser1.setLastName("ivanov");
		dtoUser1.setAge(34);
		UserDto dtoUser2= new UserDto();
		dtoUser2.setUserName("dragan33");
		dtoUser2.setFirstName("Drago");
		dtoUser2.setLastName("Stoqnov");
		dtoUser2.setAge(33);
		userService.createUser(dtoUser1);
		userService.createUser(dtoUser2);
		
		RoleDto role1 = new RoleDto();
		role1.setId(1l);
		role1.setName("user");
        role1.setDescription("asd");
        RoleDto role2 = new RoleDto();
        role2.setId(2l);
        role2.setName("admin");
        role2.setDescription("asdf");
        
        roleService.createRole(role1);
        roleService.createRole(role2);
		
	}
	
	@Test
	public void createUserTest() {
		UserDto dtoUser= new UserDto();
		dtoUser.setUserName("ivan43");
		dtoUser.setFirstName("Ivan");
		dtoUser.setLastName("ivanov");
		dtoUser.setAge(34);
		
		ResponseEntity<Long>response = userService.createUser(dtoUser);
		
	    UserDto user = userService.getUser(response.getBody());
	    
	    assertNotNull(response.getBody());
	    assertNotNull(user);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(user.getUserName(),dtoUser.getUserName());
        
	}
	@Test
	public void getAllUsersTest() {
		Collection<UserDto> users = userService.getUsers();
		assertEquals(users.size(), 2);
	}
	@Test
	public void getUserTest() {
		ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getUsers();
		UserDto user = userService.getUser(users.get(0).getId());
		
		assertNotNull(user);
		assertEquals(user.getUserName(),users.get(0).getUserName());
		assertEquals(user.getFirstName(),users.get(0).getFirstName());
		assertNotEquals(user.getId(), users.get(1).getId());
	}
	@Test
	public void updateUserTest() {
		ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getUsers();
		
		String userNameBeforeUpdate = users.get(0).getUserName();
		String firstNameBeforUpdate = users.get(0).getFirstName();
		String lastNameBeforUpdate = users.get(0).getLastName();
		int ageBeforUpdate = users.get(0).getAge();
		
		UserDto userForUpdate = users.get(0);
		userForUpdate.setUserName("ivan_n_43");
		userForUpdate.setFirstName("Ivann");
		userForUpdate.setLastName("Ivanovv");
		userForUpdate.setAge(userForUpdate.getAge() + 10);
		
		ResponseEntity<Object> response =userService.updateUser(userForUpdate);
		
		UserDto userAfterUpdate = userService.getUser(userForUpdate.getId());
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		assertNotNull(userAfterUpdate);
		
		assertEquals(userAfterUpdate.getUserName(),userForUpdate.getUserName());
		assertEquals(userAfterUpdate.getFirstName(),userForUpdate.getFirstName());
		assertEquals(userAfterUpdate.getLastName(),userForUpdate.getLastName());
		assertEquals(userAfterUpdate.getAge(),userForUpdate.getAge());
		
		assertNotEquals(userAfterUpdate.getUserName(),userNameBeforeUpdate);
		assertNotEquals(userAfterUpdate.getFirstName(),firstNameBeforUpdate);
		assertNotEquals(userAfterUpdate.getLastName(),lastNameBeforUpdate);
		assertNotEquals(userAfterUpdate.getAge(),ageBeforUpdate);		
		
	}
	@Test
	public void updateRoleToUserTest() {
		ArrayList<RoleDto> roles = (ArrayList<RoleDto>) roleService.getRoles();
		
		ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getUsers();
		
		users.get(0).getRoles().add(roles.get(0));
		
		ResponseEntity<Object> response =userService.updateRoleToUser(users.get(0));
		
		UserDto user = userService.getUser(users.get(0).getId());
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		assertNotNull(user.getRoles());
		
		assertTrue(user.getRoles().size() > 0);
		
		user.setRoles(new HashSet<RoleDto>());
		
		response =userService.updateRoleToUser(user);
		
		user = userService.getUser(users.get(0).getId());
		
        assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		assertNotNull(user.getRoles());
		
		assertTrue(user.getRoles().size() == 0);
		
		
		
		
	}
	@Test
	public void deleteUser() {
		ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getUsers();
		
		ResponseEntity<Object> response =userService.deleteUser(users.get(0).getId());
		
		ArrayList<UserDto> usersAfterDelete = (ArrayList<UserDto>) userService.getUsers();
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertTrue(users.size() > usersAfterDelete.size());
		assertFalse(usersAfterDelete.contains(users.get(0)));
		
	}

}
