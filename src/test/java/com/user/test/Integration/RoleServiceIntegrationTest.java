package com.user.test.Integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.dto.RoleDto;
import com.user.service.RoleRestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceIntegrationTest {
    @Autowired
	private RoleRestService roleService;
    
    @Before
    public void init() {
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
    public void createRoleTest() {
    	RoleDto dtoRole = new RoleDto();
    	dtoRole.setName("premium");
    	dtoRole.setDescription("premium user");
    	
    	ResponseEntity<Long> response = roleService.createRole(dtoRole);
    	
    	RoleDto role = roleService.getRole(response.getBody());
    	
    	assertNotNull(response.getBody());
    	assertNotNull(role);
    	
    	assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    	assertEquals(dtoRole.getName(), role.getName());
    	
    	
    }
    @Test
    public void getAllRolesTest() {
    	Collection<RoleDto> roles = roleService.getRoles();
    	
    	assertEquals(roles.size(), 2);
    }
    @Test
    public void getRoleTest() {
    	ArrayList<RoleDto> roles = (ArrayList<RoleDto>) roleService.getRoles();
    	
    	RoleDto role = roleService.getRole(roles.get(0).getId());
    	
    	assertNotNull(role);
    	assertEquals(roles.get(0).getName(), role.getName());
    	assertEquals(roles.get(0).getDescription(), role.getDescription());
    	assertNotEquals(roles.get(1).getId(), role.getId());
    }
    @Test
    public void updateRoleTest() {
    	ArrayList<RoleDto> roles = (ArrayList<RoleDto>) roleService.getRoles();
    	String nameBeforeUpdate = roles.get(0).getName();
    	String descriptionBeforeUpdate = roles.get(0).getDescription();
    	
    	RoleDto roleForUpdate = roles.get(0);
    	roleForUpdate.setName("userr");
    	roleForUpdate.setDescription("user description");
    	
    	ResponseEntity<Object> response =roleService.updateRole(roleForUpdate);
    	
    	RoleDto roleAfterUpdate = roleService.getRole(roleForUpdate.getId());
    	
        assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		assertNotNull(roleAfterUpdate);
		
		assertEquals(roleForUpdate.getName(), roleAfterUpdate.getName());
		assertEquals(roleForUpdate.getDescription(), roleAfterUpdate.getDescription());
		
		assertNotEquals(roleAfterUpdate.getName(), nameBeforeUpdate);
		assertNotEquals(roleAfterUpdate.getDescription(), descriptionBeforeUpdate);
    }
    @Test
    public void deleteRole() {
		ArrayList<RoleDto> roles = (ArrayList<RoleDto>) roleService.getRoles();
			    	
		ResponseEntity<Object> response = roleService.deleteRole(roles.get(0).getId());
		
		ArrayList<RoleDto> rolesAfterDelete = (ArrayList<RoleDto>) roleService.getRoles();
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertTrue(roles.size() > rolesAfterDelete.size());
		assertFalse(rolesAfterDelete.contains(roles.get(0)));
    }
}
