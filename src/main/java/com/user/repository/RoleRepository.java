package com.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
