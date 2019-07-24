package com.devglan.dto;

import java.io.Serializable;

public class UserDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
		return role;
	}
    
    public void setRole(String role) {
		this.role = role;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    public String getEmail() {
		return email;
	}
}
