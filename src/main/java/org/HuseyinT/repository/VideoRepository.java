package org.YoutubeApp.repository;

import com.huseyint.entity.Video;
import com.huseyint.entity.enums.ECategory;
import com.huseyint.utility.ConnectionProvider;
import com.huseyint.utility.ICRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoRepository implements ICRUD<Video> {
	
	private final ConnectionProvider connectionProvider;
	private String sql = "";
	
	public VideoRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Video> save(Video video) {
		sql =
				"INSERT INTO tblvideo(uploaderid, title, description, category, viewcount, likecount, dislikecount)" + "VALUES (?, ?, ?, ?::video_category, ?, ?, ?);";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, video.getUploaderId());
			statement.setString(2, video.getTitle());
			statement.setString(3, video.getDescription());
			statement.setString(4, video.getCategory().name());
			statement.setLong(5, video.getViewCount());
			statement.setLong(6, video.getLikeCount());
			statement.setLong(7, video.getDislikeCount());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Error recording a video " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(video);
	}
	
	@Override
	public Optional<Video> update(Video video) {
		sql =
				"UPDATE tblvideo SET uploaderid=?, title=?, description=?, category=?::video_category, viewcount=?, " + "likecount=?, dislikecount=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, video.getUploaderId());
			statement.setString(2, video.getTitle());
			statement.setString(3, video.getDescription());
			statement.setString(4, video.getCategory().name());
			statement.setLong(5, video.getViewCount());
			statement.setLong(6, video.getLikeCount());
			statement.setLong(7, video.getDislikeCount());
			statement.setLong(8, video.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Video güncellenirken hata oluştu: Güncelleme başarısız.");
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Video güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(video);
	}
	
	@Override
	public void delete(Long silinecekId) {
		sql = "DELETE FROM tblvideo WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, silinecekId);
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Video silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Video> findAll() {
		sql = "SELECT * FROM tblvideo";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql);
		     ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				videoList.add(mapVideoFromResultSet(rs).orElse(null));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Video verileri alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return videoList;
	}
	
	@Override
	public Optional<Video> findById(Long id) {
		sql = "SELECT * FROM tblvideo WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return mapVideoFromResultSet(rs);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Video bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public List<Video> findVideosByTitle(String title) {
		sql = "SELECT * FROM tblvideo WHERE title ILIKE ?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, "%" + title + "%");
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					videoList.add(mapVideoFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding videos by title: " + e.getMessage());
			e.printStackTrace();
		}
		return videoList;
	}
	
	public List<Video> findVideosOfUser(Long userId) {
		sql = "SELECT * FROM tblvideo WHERE uploaderid=?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					videoList.add(mapVideoFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding videos of user: " + e.getMessage());
			e.printStackTrace();
		}
		return videoList;
	}
	
	public void increaseViewCount(Long videoId) {
		sql = "UPDATE tblvideo SET viewcount=viewcount+1 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error increasing view count: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Video> findTrendingVideos(int limit) {
		sql = "SELECT * FROM tblvideo ORDER BY viewcount DESC LIMIT ?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setInt(1, limit);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					videoList.add(mapVideoFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding trending videos: " + e.getMessage());
			e.printStackTrace();
		}
		return videoList;
	}
	
	public List<Video> findVideosByCategory(ECategory category) {
		sql = "SELECT * FROM tblvideo WHERE category=?::video_category";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, category.name());
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					videoList.add(mapVideoFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding videos by category: " + e.getMessage());
			e.printStackTrace();
		}
		return videoList;
	}
	
	public void updateVideoTitle(Long videoId, String newTitle) {
		sql = "UPDATE tblvideo SET title=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, newTitle);
			statement.setLong(2, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error updating video title: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateVideoDescription(Long videoId, String newDescription) {
		sql = "UPDATE tblvideo SET description=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, newDescription);
			statement.setLong(2, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error updating video description: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void incrementLikeCount(Long videoId) {
		sql = "UPDATE tblvideo SET likecount = likecount + 1 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Beğeni sayısı artırılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void incrementDislikeCount(Long videoId) {
		sql = "UPDATE tblvideo SET dislikecount = dislikecount + 1 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Dislike sayısı artırılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void softDelete(Long videoId) {
		sql = "UPDATE tbluser SET state=0 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error soft deleting like: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Optional<Video> mapVideoFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		Long uploaderid = rs.getLong("uploaderid");
		String title = rs.getString("title");
		String description = rs.getString("description");
		ECategory category = ECategory.valueOf(rs.getString("category"));
		Long viewcount = rs.getLong("viewcount");
		Long likecount = rs.getLong("likecount");
		Long dislikecount = rs.getLong("dislikecount");
		Byte state = rs.getByte("state");
		Long createdAt = rs.getLong("createdAt");
		Long updatedAt = rs.getLong("updatedAt");
		return Optional.of(new Video(id, uploaderid, title, description, category, viewcount, likecount, dislikecount, state, createdAt, updatedAt));
	}
}