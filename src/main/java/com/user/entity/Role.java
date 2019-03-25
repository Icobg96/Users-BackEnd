package com.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.user.dto.RoleDto;

@Entity
@Table(name="roles")
public class Role implements IEntity {
	@Id
	@GeneratedValue(generator="roleSeq")
    @SequenceGenerator(name="roleSeq",sequenceName="ROLE_AUTO_INCREMENT", allocationSize=1) 
    @Column(name="id")
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created=new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;
    
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
	
	@PrePersist
	protected void onCreate() {
	    updated = new Date();
	    created = new Date();
	}

	 @PreUpdate
	 protected void onUpdate() {
	    updated = new Date();
	 }
	 
	 public void updateFromDto(RoleDto role){
		 this.id=role.getId();
		 this.name=role.getName();
		 this.description=role.getDescription();
		 	   
	 }

	public RoleDto convertOnDto(){
    	 RoleDto role = new RoleDto(this.id,this.name,this.description); 
        
         return role;
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
