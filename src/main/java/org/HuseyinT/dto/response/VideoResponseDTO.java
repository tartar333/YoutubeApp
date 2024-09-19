package org.YoutubeApp.dto.response;

import com.huseyint.entity.enums.ECategory;

public class VideoResponseDTO {
	private Long id;
	private Long uploaderId; // Uploader bilgisi önemli olabilir, bu yüzden tutulabilir.
	private String title;
	private String description;
	private ECategory category;
	private Integer viewCount;
	private Integer likeCount;
	private Integer dislikeCount;
	
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
	
	public Integer getViewCount() {
		return viewCount;
	}
	
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	public Integer getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	public Integer getDislikeCount() {
		return dislikeCount;
	}
	
	public void setDislikeCount(Integer dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	
	@Override
	public String toString() {
		return "VideoResponseDTO{" + "id=" + getId() + ", uploaderId=" + getUploaderId() + ", title='" + getTitle() + '\'' + ", description='" + getDescription() + '\'' + ", category=" + getCategory() + ", viewCount=" + getViewCount() + ", likeCount=" + getLikeCount() + ", dislikeCount=" + getDislikeCount() + '}';
	}
}