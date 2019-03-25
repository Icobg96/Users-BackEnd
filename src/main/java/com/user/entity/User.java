package com.user.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.user.dto.UserDto;

@Entity
@Table(name="users")
public class User implements IEntity{

	@Id
	@GeneratedValue(generator="UserSeq")
    @SequenceGenerator(name="UserSeq",sequenceName="USER_AUTO_INCREMENT", allocationSize=1) 
    @Column(name="id")
	private Long id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="age")
	private int age;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created=new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    @Version
    private Date updated;
    
    @ManyToMany(fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    @JoinTable(name = "user_role",         
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
   private Set<Role> roles = new HashSet<>();

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
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	@PrePersist
	protected void onCreate() {
	    updated = new Date();
	    created = new Date();
	}

	 @PreUpdate
	 protected void onUpdate() {
	    updated = new Date();
	 }
	 public void updateFromDto(UserDto user){
		this.id=user.getId();
	 	this.userName=user.getUserName();
	 	this.firstName=user.getFirstName();
	 	this.lastName=user.getLastName();
	 	this.age=user.getAge();
	 	
	 }
     public UserDto convertOnDto(){
    	 UserDto user =new UserDto(this.id,this.userName,this.firstName,this.lastName,this.age,this.roles);
       
         return user;
     }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
 
}
