package com.user.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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
import com.user.entity.Role;
import com.user.repository.RoleRepository;
import com.user.service.RoleRestService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceUnitTest {
	@Autowired
	private RoleRestService roleService;
	@MockBean
	private RoleRepository roleRepository;
	
	private Role role1;
	private Role role2;
	private RoleDto dtoRole;
	
	@Before
	public void inti() {
		role1 = new Role();
		role1.setId(1l);
		role1.setName("admin");
		role1.setDescription("admin role");
		
		role2 = new Role();
		role2.setId(2l);
		role2.setName("user");
		role2.setDescription("user role");
		
		dtoRole = new RoleDto();
		dtoRole.setId(1l);
		dtoRole.setName("admin");
		dtoRole.setDescription("admin role");
		
	}
	@Test
	public void createRoleTest() {
		
		Mockito.when(roleRepository.save(role1)).thenReturn(role1);
		
		ResponseEntity<Long>response = roleService.createRole(dtoRole);
		
	    assertNotNull(response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(dtoRole.getId(), response.getBody());
	}
	@Test
	public void updateRoleTest() {
		
		Mockito.when(roleRepository.findOne(Mockito.anyLong())).thenReturn(role1);

		dtoRole.setName("adminnn");
		dtoRole.setDescription("adminnn roleee");
		
		ResponseEntity<Object>response = roleService.updateRole(dtoRole);
		
		assertNotNull(response.getBody());

	    assertEquals(response.getStatusCode(), HttpStatus.OK);
	    assertEquals("Role is updated successsfully", response.getBody());
		
	}
	@Test
	public void getRoleTest() {
		Mockito.when(roleRepository.findOne(Mockito.anyLong())).thenReturn(role1);
		
		RoleDto dtoRole = roleService.getRole(role1.getId());
			    
		assertNotNull(dtoRole);
		
		assertEquals(dtoRole.getName() , role1.getName());
		assertEquals(dtoRole.getDescription() , role1.getDescription());
	}
	@Test
	public void getAllRolsTest() {
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role1);
		Mockito.when(roleRepository.findAll()).thenReturn(roles);
	    
		ArrayList<RoleDto> dtoRoles = (ArrayList<RoleDto>) roleService.getRoles();
		
		assertNotNull(roles);
		assertEquals(roles.size(), dtoRoles.size());
	}
	@Test
	public void deleteRoleTest() {
		
        ResponseEntity<Object>response = roleService.deleteRole(role1.getId());
		
		assertNotNull(response.getBody());

	    assertEquals(response.getStatusCode(), HttpStatus.OK);
	    assertEquals("Role is deleted successsfully", response.getBody());
	}

}
