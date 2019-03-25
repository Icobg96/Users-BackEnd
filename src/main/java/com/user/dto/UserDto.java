package com.user.dto;

import java.util.HashSet;
import java.util.Set;

import com.user.entity.Role;
import com.user.entity.User;

public class UserDto implements IDto {
	private Long id;
	private String userName;
	private String firstName;
	private String lastName;
	private int age;
	private Set<RoleDto> roles= new HashSet<RoleDto>();
	
	//Constructors
	public UserDto() {};
	public UserDto(Long id,String userName,String fistName,String lastName,int age,Set<Role>roless) {
		this.id=id;
		this.userName=userName;
		this.firstName=fistName;
		this.lastName=lastName;
		this.age=age;
		roless.stream().forEach(role->roles.add(role.convertOnDto()));
	}
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age=age;
	}
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	
	public User convertToEntity() {
		User user = new User();
		
		user.setId(this.id);
		user.setUserName(this.userName);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setAge(this.age);
		
		return user;
	}



}
