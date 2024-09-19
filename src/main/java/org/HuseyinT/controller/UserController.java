package org.YoutubeApp.controller;

import com.huseyint.dto.request.UserSaveRequestDTO;
import com.huseyint.dto.request.UserUpdateRequestDTO;
import com.huseyint.dto.response.UserResponseDTO;
import com.huseyint.entity.User;
import com.huseyint.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserController {
	private static UserController instance;
	private final UserService userService;
	
	public UserController() {
		this.userService = new UserService();
	}
	
	public static synchronized UserController getInstance(){
		if (instance == null){
			instance=new UserController();
		}
		return instance;
	}
	
	public Optional<UserResponseDTO> saveUser(UserSaveRequestDTO requestDTO) {
		return userService.saveUser(requestDTO);
	}
	
	public Optional<UserResponseDTO> updateUser(UserUpdateRequestDTO requestDTO) {
		return userService.updateUser(requestDTO);
	}
	
	public void softDeleteUser(Long id) {
		userService.softDelete(id);
	}
	
	public List<UserResponseDTO> getAllUsers() {
		return userService.getAllUsers();
	}
	
	public Optional<UserResponseDTO> getUserById(Long id) {
		return userService.getUserById(id);
	}
	
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		return userService.findByUsernameAndPassword(username, password);
	}
	
	public boolean isExistUsername(String username) {
		return userService.isExistUsername(username);
	}
	
	public boolean isExistEmail(String email) {
		return userService.isExistEmail(email);
	}
	
	public Optional<User> findByEmail(String email) {
		return userService.findByEmail(email);
	}
	
	public void changePassword(Long userId, String newPassword) {
		userService.changePassword(userId, newPassword);
	}
	
	public List<UserResponseDTO> findActiveUsers() {
		return userService.findActiveUsers();
	}
}