package com.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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

import com.user.dto.UserDto;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.repository.UserRepository;


@Path("/Users")
@Component
public class UserRestService {
	
	   @Autowired
	   private UserRepository repository;

	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Collection<UserDto> getUsers(){
		   Collection<User> users=(Collection<User>) repository.findAll();
		   ArrayList<UserDto>dtoUsers = new ArrayList<UserDto>();
		   users.stream().forEach(u->dtoUsers.add(u.convertOnDto()));
	      return dtoUsers;
	   }
	   @GET
	   @Path("/{userid}")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public UserDto getUser(@PathParam("userid") Long userid){
		   User user=repository.findOne(userid);
		   return user.convertOnDto();
	   }

	   @POST
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Long> createUser(UserDto dtoUser){
		   User user =new User();
		   user.updateFromDto(dtoUser);
		   user = repository.save(user);
		   return new ResponseEntity<Long>(user.getId(), HttpStatus.CREATED);
	     
	   }
	   
	   @PUT
	   @Path("/update/role")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Object> updateRoleToUser(UserDto dtoUser){
		   User user = repository.findOne(dtoUser.getId());
		   HashSet<Role>roles = new HashSet<>();
		   dtoUser.getRoles().stream().forEach(role->roles.add(role.convertToEntity()));
		   user.setRoles(roles);
		   repository.save(user);
		   return new ResponseEntity<Object>("roles of the user are udated successfully", HttpStatus.OK);
	     
	   }

	   @PUT
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Object> updateUser(UserDto user){
		   User currentUser = repository.findOne(user.getId());
		   currentUser.updateFromDto(user);
		   repository.save(currentUser);
		   return new ResponseEntity<Object>("user is updated successsfully", HttpStatus.OK);
	   }

	   @DELETE
	   @Path("/{userid}")
	   @Produces(MediaType.APPLICATION_JSON)
	   public ResponseEntity<Object> deleteUser(@PathParam("userid") Long userid){
		   repository.delete(userid);
		   return new ResponseEntity<Object>("User is deleted successsfully", HttpStatus.OK);
	    
	   }

}
