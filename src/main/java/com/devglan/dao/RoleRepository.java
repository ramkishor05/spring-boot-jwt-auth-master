package com.devglan.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devglan.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	Role findByName(String name);

}
