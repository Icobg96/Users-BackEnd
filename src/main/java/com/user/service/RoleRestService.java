package com.user.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.user.dto.RoleDto;
import com.user.entity.Role;
import com.user.repository.RoleRepository;


@Path("/Role")
@Component
public class RoleRestService {
	   @Autowired
	   private RoleRepository repository;
	
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Collection<RoleDto> getRoles(){
		   Collection<Role> roles=(Collection<Role>) repository.findAll();
		   ArrayList<RoleDto>dtoRoles = new ArrayList<RoleDto>();
		   roles.stream().forEach(role->dtoRoles.add(role.convertOnDto()));
	      return dtoRoles;
	   }
	   @GET
	   @Path("/{roleid}")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public RoleDto getRole(@PathParam("roleid") Long roleid){
		   Role role=repository.findOne(roleid);
		   return role.convertOnDto();
	   }

	   @POST
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Long> createRole(RoleDto dtoRole){
		   Role role =new Role();
		   role.updateFromDto(dtoRole);
		   role = repository.save(role);
		   return new ResponseEntity<Long>(role.getId(), HttpStatus.CREATED);
	     
	   }

	   @PUT
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Object> updateRole(RoleDto role){
		   Role currentRole = repository.findOne(role.getId());
		   currentRole.updateFromDto(role);
		   repository.save(currentRole);
		   return new ResponseEntity<Object>("Role is updated successsfully", HttpStatus.OK);
	   }

	   @DELETE
	   @Path("/{roleid}")
	   @Produces(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Object> deleteRole(@PathParam("roleid") Long roleid){
		   repository.delete(roleid);
		   return new ResponseEntity<Object>("Role is deleted successsfully", HttpStatus.OK);
	    
	   }
}
