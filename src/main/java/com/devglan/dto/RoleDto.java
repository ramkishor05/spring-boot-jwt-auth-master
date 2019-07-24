package com.devglan.dto;

import java.io.Serializable;
import java.util.Collection;

public class RoleDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private Collection<String> privileges;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Collection<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<String> privileges) {
		this.privileges = privileges;
	}
    
}