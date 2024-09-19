package org.YoutubeApp.entity;

public class Comment extends BaseEntity{
	private Long id;
	private Long userId;
	private Long videoId;
	private String comment;
	
	public Comment() {
	}
	
	public Comment(Long userId, Long videoId, String comment) {
		this.userId = userId;
		this.videoId = videoId;
		this.comment = comment;
	}
	
	public Comment(Long id, Long userId, Long videoId, String comment,Byte state, Long createdAt, Long updatedAt) {
		super(state, createdAt, updatedAt);
		this.id = id;
		this.userId = userId;
		this.videoId = videoId;
		this.comment = comment;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "Comment{" + "id=" + getId() + ", userId=" + getUserId() + ", videoId=" + getVideoId() + ", comment='" + getComment() + '\'' + ", state=" + getState() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
	}
}