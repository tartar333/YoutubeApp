package org.YoutubeApp.dto.response;

public class UserResponseDTO {
	private String name;
	private String surname;
	private String email;
	private String username;
	
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
	
	@Override
	public String toString() {
		return "UserResponseDTO{" + "name='" + getName() + '\'' + ", surname='" + getSurname() + '\'' + ", email='" + getEmail() + '\'' + ", username='" + getUsername() + '\'' + '}';
	}
}