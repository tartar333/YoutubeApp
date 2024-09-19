package org.YoutubeApp.entity;

import com.huseyint.entity.enums.ELikeStatus;

public class Like extends BaseEntity{
	private Long id;
	private ELikeStatus likeStatus;
	private Long videoId;
	private Long userId;
	
	public Like() {
	}
	
	public Like(ELikeStatus likeStatus, Long videoId, Long userId) {
		this.likeStatus = likeStatus;
		this.videoId = videoId;
		this.userId = userId;
	}
	
	public Like(Long id, ELikeStatus likeStatus, Long videoId, Long userId,Byte state, Long createdAt, Long updatedAt) {
		super(state, createdAt, updatedAt);
		this.id = id;
		this.likeStatus = likeStatus;
		this.videoId = videoId;
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public ELikeStatus getLikeStatus() {
		return likeStatus;
	}
	
	public void setLikeStatus(ELikeStatus likeStatus) {
		this.likeStatus = likeStatus;
	}
	
	public Long getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Like{" + "id=" + getId() + ", likeStatus=" + getLikeStatus() + ", videoId=" + getVideoId() + ", userId=" + getUserId() + ", state=" + getState() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
	}
}