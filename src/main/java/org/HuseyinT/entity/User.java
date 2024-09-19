package org.YoutubeApp.entity;

import com.huseyint.entity.enums.ERole;

public class User extends BaseEntity{
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String username;
	private String password;
	private ERole role;
	
	public User() {
	}
	
	public User(String name, String surname, String email, String username, String password, ERole role) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public User(Long id, String name, String surname, String email, String username, String password, ERole role, Byte state,
	            Long createdAt, Long updatedAt) {
		super(state, createdAt, updatedAt);
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
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
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public ERole getRole() {
		return role;
	}
	
	public void setRole(ERole role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User{" + "id=" + getId() + ", name='" + getName() + '\'' + ", surname='" + getSurname() + '\'' + ", username='" + getUsername() + '\'' + ", password='" + getPassword() + '\'' + ", role=" + getRole() + ", state=" + getState() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
	}
}