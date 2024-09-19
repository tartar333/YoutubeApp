package org.YoutubeApp.repository;

import com.huseyint.entity.Comment;
import com.huseyint.utility.ConnectionProvider;
import com.huseyint.utility.ICRUD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements ICRUD<Comment> {
	
	private final ConnectionProvider connectionProvider;
	private String sql = "";
	
	public CommentRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Comment> save(Comment comment) {
		sql = "INSERT INTO tblcomment(content, videoid, userid)VALUES (?, ?, ?)";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, comment.getComment());
			statement.setLong(2, comment.getVideoId());
			statement.setLong(3, comment.getUserId());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Yorum kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(comment);
	}
	
	@Override
	public Optional<Comment> update(Comment comment) {
		sql = "UPDATE tblcomment SET content=?, videoid=?, userid=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setString(1, comment.getComment());
			statement.setLong(2, comment.getVideoId());
			statement.setLong(3, comment.getUserId());
			statement.setLong(4, comment.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				System.err.println("Repository: Yorum güncellenirken hata oluştu: Güncelleme başarısız.");
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Yorum güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.ofNullable(comment);
	}
	
	@Override
	public void delete(Long silinecekId) {
		sql = "DELETE FROM tblcomment WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, silinecekId);
			statement.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("Repository: Yorum silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Comment> findAll() {
		// content=?, videoid=?, userid=?
		sql = "SELECT * FROM tblcomment";
		List<Comment> commentList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql);
		     ResultSet rs = statement.executeQuery()) {
			while (rs.next()){
				commentList.add(mapCommentFromResultSet(rs).orElse(null));
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Yorum verileri alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	
	@Override
	public Optional<Comment> findById(Long id) {
		sql = "SELECT * FROM tblcomment WHERE id=?";
		try(PreparedStatement statement = connectionProvider.getPreparedStatement(sql)){
			statement.setLong(1,id);
			try(ResultSet rs = statement.executeQuery()){
				if (rs.next()){
					return mapCommentFromResultSet(rs);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("Repository: Yorum bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public List<Comment> findCommentsByVideoId(Long videoId) {
		sql = "SELECT * FROM tblcomment WHERE videoid=?";
		List<Comment> commentList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, videoId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					commentList.add(mapCommentFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding comments by video ID: " + e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	
	public void softDelete(Long commentId) {
		sql = "UPDATE tblcomment SET state=0 WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, commentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error soft deleting comment: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Comment> findCommentsByUserId(Long userId) {
		sql = "SELECT * FROM tblcomment WHERE userid=?";
		List<Comment> commentList = new ArrayList<>();
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setLong(1, userId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					commentList.add(mapCommentFromResultSet(rs).orElse(null));
				}
			}
		} catch (SQLException e) {
			System.err.println("Repository: Error finding comments by user ID: " + e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	
	public void updateCommentState(Long commentId, Byte newState) {
		sql = "UPDATE tblcomment SET state=? WHERE id=?";
		try (PreparedStatement statement = connectionProvider.getPreparedStatement(sql)) {
			statement.setByte(1, newState);
			statement.setLong(2, commentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Repository: Error updating comment state: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Optional<Comment> mapCommentFromResultSet(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String content = rs.getString("content");
		Long videoid = rs.getLong("videoid");
		Long userId = rs.getLong("userId");
		Byte state = rs.getByte("state");
		Long createdAt = rs.getLong("createdAt");
		Long updatedAt = rs.getLong("updatedAt");
		return Optional.of(new Comment(id, userId, videoid, content, state, createdAt, updatedAt));
	}
}