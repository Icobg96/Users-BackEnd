package com.user.dto;

import com.user.entity.Role;

public class RoleDto implements IDto{
	private Long id;
	private String name;
	private String description;
	
	//Constructors
	public RoleDto() {};
	public RoleDto(Long id,String title,String description) {
		this.id=id;
		this.name=title;
		this.description=description;
	}
	//geters and seters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Role convertToEntity() {
		Role role = new Role();
		role.setId(this.id);
		role.setName(this.name);
		role.setDescription(this.description);
		return role;
	}
	
}
