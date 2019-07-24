package com.devglan.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devglan.entities.Privilege;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {

	Privilege findByName(String name);

}
