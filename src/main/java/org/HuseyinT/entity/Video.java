package org.YoutubeApp.entity;

import com.huseyint.entity.enums.ECategory;

public class Video extends BaseEntity{
	private Long id;
	private Long uploaderId;
	private String title;
	private String description;
	private ECategory category;
	private Long viewCount;
	private Long likeCount;
	private Long dislikeCount;
	
	public Video() {
	}
	
	public Video(Long uploaderId, String title, String description, ECategory category, Long viewCount, Long likeCount, Long dislikeCount) {
		this.uploaderId = uploaderId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
	}
	
	public Video(Long id, Long uploaderId, String title, String description, ECategory category, Long viewCount, Long likeCount, Long dislikeCount,Byte state, Long createdAt, Long updatedAt) {
		super(state, createdAt, updatedAt);
		this.id = id;
		this.uploaderId = uploaderId;
		this.title = title;
		this.description = description;
		this.category = category;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUploaderId() {
		return uploaderId;
	}
	
	public void setUploaderId(Long uploaderId) {
		this.uploaderId = uploaderId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ECategory getCategory() {
		return category;
	}
	
	public void setCategory(ECategory category) {
		this.category = category;
	}
	
	public Long getViewCount() {
		return viewCount;
	}
	
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
	
	public Long getDislikeCount() {
		return dislikeCount;
	}
	
	public void setDislikeCount(Long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	
	@Override
	public String toString() {
		return "Video{" + "id=" + getId() + ", uploaderId=" + getUploaderId() + ", title='" + getTitle() + '\'' + ", description='" + getDescription() + '\'' + ", category=" + getCategory() + ", viewCount=" + getViewCount() + ", likeCount=" + getLikeCount() + ", dislikeCount=" + getDislikeCount() + ", state=" + getState() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
	}
}