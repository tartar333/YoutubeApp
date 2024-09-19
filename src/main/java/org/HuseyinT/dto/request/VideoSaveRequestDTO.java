package org.YoutubeApp.dto.request;

import com.huseyint.entity.enums.ECategory;

public class VideoSaveRequestDTO {
	private Long uploaderId;
	private String title;
	private String description;
	private ECategory category;
	
	public VideoSaveRequestDTO(Long uploaderId, String title, String description, ECategory category) {
		this.uploaderId = uploaderId;
		this.title = title;
		this.description = description;
		this.category = category;
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
}