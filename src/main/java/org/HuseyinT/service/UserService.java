package org.YoutubeApp.service;

import com.huseyint.dto.request.UserSaveRequestDTO;
import com.huseyint.dto.request.UserUpdateRequestDTO;
import com.huseyint.dto.response.UserResponseDTO;
import com.huseyint.entity.User;
import com.huseyint.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService() {
		this.userRepository = new UserRepository();
	}
	
	// Yeni bir kullanıcı kaydetmek için
	public Optional<UserResponseDTO> saveUser(UserSaveRequestDTO requestDTO) {
		try {
			User user = new User(
					requestDTO.getName(),
					requestDTO.getSurname(),
					requestDTO.getEmail(),
					requestDTO.getUsername(),
					requestDTO.getPassword(),
					requestDTO.getRole()
			);
			
			Optional<User> savedUser = userRepository.save(user);
			if (savedUser.isPresent()) {
				System.out.println("UserService: Kullanıcı başarıyla kaydedildi.");
			}
			return savedUser.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	
	// Mevcut bir kullanıcıyı güncellemek için
	public Optional<UserResponseDTO> updateUser(UserUpdateRequestDTO requestDTO) {
		try {
			Optional<User> existingUser = userRepository.findById(requestDTO.getId());
			
			if (existingUser.isPresent()) {
				User user = existingUser.get();
				user.setName(requestDTO.getName());
				user.setSurname(requestDTO.getSurname());
				user.setEmail(requestDTO.getEmail());
				user.setUsername(requestDTO.getUsername());
				user.setPassword(requestDTO.getPassword());
				user.setRole(requestDTO.getRole());
				
				Optional<User> updatedUser = userRepository.update(user);
				if (updatedUser.isPresent()) {
					System.out.println("UserService: Kullanıcı başarıyla güncellendi.");
				}
				return updatedUser.map(this::mapToResponseDTO);
			} else {
				System.out.println("UserService: Güncellenecek kullanıcı bulunamadı.");
			}
			
			return Optional.empty();
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Kullanıcı silmek için (soft-delete)
	public void softDelete(Long userId) {
		try {
			userRepository.softDelete(userId);
			System.out.println("UserService: Kullanıcı başarıyla soft-delete ile silindi.");
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Tüm kullanıcıları listelemek için
	public List<UserResponseDTO> getAllUsers() {
		try {
			List<UserResponseDTO> users = userRepository.findAll()
			                                            .stream()
			                                            .map(this::mapToResponseDTO)
			                                            .collect(Collectors.toList());
			System.out.println("UserService: Tüm kullanıcılar başarıyla listelendi.");
			return users;
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcılar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of(); // Hata durumunda boş liste döndürülür.
		}
	}
	
	// ID'ye göre kullanıcıyı bulmak için
	public Optional<UserResponseDTO> getUserById(Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				System.out.println("UserService: Kullanıcı başarıyla bulundu.");
			} else {
				System.out.println("UserService: Kullanıcı bulunamadı.");
			}
			return user.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Kullanıcı girişi için
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		try {
			return userRepository.findByUsernameAndPassword(username, password);
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı girişi yapılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Kullanıcı adı doğrulama için
	public boolean isExistUsername(String username) {
		try {
			return userRepository.isExistUsername(username);
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı adı kontrol edilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	// Email doğrulama için
	public boolean isExistEmail(String email) {
		try {
			return userRepository.isExistEmail(email);
		} catch (Exception e) {
			System.err.println("UserService: Email kontrol edilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	// Email ile kullanıcı arama
	public Optional<User> findByEmail(String email) {
		try {
			return userRepository.findByEmail(email);
		} catch (Exception e) {
			System.err.println("UserService: Email ile kullanıcı aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Kullanıcının şifresini güncellemek için
	public void changePassword(Long userId, String newPassword) {
		try {
			userRepository.changePassword(userId, newPassword);
			System.out.println("UserService: Şifre başarıyla güncellendi.");
		} catch (Exception e) {
			System.err.println("UserService: Şifre güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Aktif durumdaki kullanıcıları listelemek
	public List<UserResponseDTO> findActiveUsers() {
		try {
			List<User> activeUsers = userRepository.findActiveUsers();
			return activeUsers.stream()
			                  .map(this::mapToResponseDTO)
			                  .collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("UserService: Aktif kullanıcılar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// User entity'sini UserResponseDTO'ya dönüştürmek için yardımcı metot
	private UserResponseDTO mapToResponseDTO(User user) {
		UserResponseDTO responseDTO = new UserResponseDTO();
		responseDTO.setName(user.getName());
		responseDTO.setSurname(user.getSurname());
		responseDTO.setEmail(user.getEmail());
		responseDTO.setUsername(user.getUsername());
		return responseDTO;
	}
}