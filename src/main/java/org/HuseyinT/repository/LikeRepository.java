package org.YoutubeApp.repository;

import com.huseyint.entity.Like;
import com.huseyint.entity.enums.ELikeStatus;
import com.huseyint.utility.ConnectionProvider;
import com.huseyint.utility.ICRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LikeRepository implements ICRUD<Like> {
	
	private final ConnectionProvider connectionProvider;
	private String sql = "";
	
	public LikeRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Like> save(Like like) {
		sql = "INSERT INTO tbllike(likestatus, videoId, userId)VALUES (?::like_status, ?, ?)";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, like.getLikeStatus().name());
			statement.setLong(2, like.getVideoId());
			statement.setLong(3, like.getUserId());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Like kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(like);
	}
	
	@Override
	public Optional<Like> update(Like like) {
		sql = "UPDATE tbllike SET likestatus=?::like_status, videoid=?, userid=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, like.getLikeStatus().name());
			statement.setLong(2, like.getVideoId());
			statement.setLong(3, like.getUserId());
			statement.setLong(4, like.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Like güncellenirken hata oluştu: Güncelleme başarısız.");
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Like güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(like);
	}
	
	@Override
	public void delete(Long silinecekId) {
		sql = "DELETE FROM tbllike WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, silinecekId);
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Like silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Like> findAll() {
		sql = "SELECT * FROM tbllike";
		List<Like> likeList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql);
		     ResultSet rs = statement.executeQuery()) {
			while (rs.next()){
				likeList.add(mapLikeFromResultSet(rs).orElse(null));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Like verileri alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return likeList;
	}
	
	@Override
	public Optional<Like> findById(Long id) {
		sql = "SELECT * FROM tbllike WHERE id=?";
		try(PreparedStatement statement = connectionProvider.getPreparedStatement(sql)){
			statement.setLong(1,id);
			try(ResultSet rs = statement.executeQuery()) {
				if (rs.next()){
					return mapLikeFromResultSet(rs);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Like bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public int countLikesByVideoId(Long videoId) {
		sql = "SELECT COUNT(*) FROM tbllike WHERE videoid=? AND likestatus='LIKE'";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error counting likes: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public int countDislikesByVideoId(Long videoId) {
		sql = "SELECT COUNT(*) FROM tbllike WHERE videoid=? AND likestatus='DISLIKE'";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error counting dislikes: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Like> findLikesByUserId(Long userId) {
		sql = "SELECT * FROM tbllike WHERE userid=?";
		List<Like> likeList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					likeList.add(mapLikeFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding likes by user ID: " + e.getMessage());
			e.printStackTrace();
		}
		return likeList;
	}
	
	public Optional<Like> findByUserIdAndVideoId(Long userId, Long videoId) {
		sql = "SELECT * FROM tbllike WHERE userid=? AND videoid=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			statement.setLong(2, videoId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return mapLikeFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding like by user and video ID: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public boolean isLikeExist(Long userId, Long videoId) {
		sql = "SELECT 1 FROM tbllike WHERE userid=? AND videoid=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			statement.setLong(2, videoId);
			try (ResultSet rs = statement.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error checking like existence: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public void softDelete(Long likeId) {
		sql = "UPDATE tbllike SET state=0 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, likeId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error soft deleting like: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Optional<Like> mapLikeFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		ELikeStatus likestatus = ELikeStatus.valueOf(rs.getString("likestatus"));
		Long videoid = rs.getLong("videoid");
		Long userId = rs.getLong("userId");
		Byte state = rs.getByte("state");
		Long createdAt = rs.getLong("createdAt");
		Long updatedAt = rs.getLong("updatedAt");
		return Optional.of(new Like(id, likestatus, videoid, userId, state, createdAt, updatedAt));
	}
}