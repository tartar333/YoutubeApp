package org.YoutubeApp.repository;

import com.huseyint.entity.User;
import com.huseyint.entity.enums.ERole;
import com.huseyint.utility.ConnectionProvider;
import com.huseyint.utility.ICRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ICRUD<User> {
	private final ConnectionProvider connectionProvider;
	private String sql = "";
	
	public UserRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<User> save(User user) {
		sql =
				"INSERT INTO tbluser(name, surname, email, username, password, role)VALUES (?, ?, ?, ?, ?, " +
						"?::user_role)";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getRole().name());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Error registering a user " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public Optional<User> update(User user) {
		sql = "UPDATE tbluser SET name=?, surname=?, email=?, username=?, password=?, role=?::user_role WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getRole().name());
			statement.setLong(7, user.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Error updating user: Update failed.");
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Error updating user: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public void delete(Long silinecekId) {
		sql = "DELETE FROM tbluser WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, silinecekId);
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Error deleting user: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<User> findAll() {
		sql = "SELECT * FROM tbluser";
		List<User> userList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql);
		     ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				userList.add(mapUserFromResultSet(rs).orElse(null));
			}
		}
		catch (SQLException e) {
			System.out.println("Repository: Error listing a user: " + e.getMessage());
			e.printStackTrace();
		}
		return userList;
	}
	
	@Override
	public Optional<User> findById(Long id) {
		sql = "SELECT * FROM tbluser WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return mapUserFromResultSet(rs);
				}
			}
		}
		catch (SQLException e) {
			System.out.println("Repository: User not found." + e.getMessage());
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		sql = "SELECT * FROM tbluser WHERE username=? AND password=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, username);
			statement.setString(2, password);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return mapUserFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding user by username and password: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public boolean isExistUsername(String username) {
		sql = "SELECT 1 FROM tbluser WHERE username=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, username);
			try (ResultSet rs = statement.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error checking if username exists: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isExistEmail(String email) {
		sql = "SELECT 1 FROM tbluser WHERE email=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, email);
			try (ResultSet rs = statement.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error checking if email exists: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public Optional<User> findByEmail(String email) {
		sql = "SELECT * FROM tbluser WHERE email=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, email);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return mapUserFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding user by email: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public void changePassword(Long userId, String newPassword) {
		sql = "UPDATE tbluser SET password=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, newPassword);
			statement.setLong(2, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error changing password: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<User> findActiveUsers() {
		sql = "SELECT * FROM tbluser WHERE state=1";
		List<User> userList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql);
		     ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				userList.add(mapUserFromResultSet(rs).orElse(null));
			}
		} catch (SQLException e) {
			System.out.println("Repository: Error listing active users: " + e.getMessage());
			e.printStackTrace();
		}
		return userList;
	}
	
	public void softDelete(Long userId) {
		sql = "UPDATE tbluser SET state=0 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error soft deleting like: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Optional<User> mapUserFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		String surname = rs.getString("surname");
		String email = rs.getString("email");
		String username = rs.getString("username");
		String password = rs.getString("password");
		ERole role = ERole.valueOf(rs.getString("role"));
		Byte state = rs.getByte("state");
		Long createdAt = rs.getLong("createdAt");
		Long updatedAt = rs.getLong("updatedAt");
		return Optional.of(new User(id, name, surname, email, username, password, role, state, createdAt, updatedAt));
	}
}