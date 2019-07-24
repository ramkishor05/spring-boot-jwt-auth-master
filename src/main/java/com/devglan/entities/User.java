package com.devglan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private int id;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private String username;
    
    @Column
    @JsonIgnore
    private String password;
    
    @Column
	private String email;
	
    @Column
	private boolean enabled;
    
    @ManyToOne
    @JoinColumn(name="role_id" ,referencedColumnName="id")
    private Role role;

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

	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}

	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
}
