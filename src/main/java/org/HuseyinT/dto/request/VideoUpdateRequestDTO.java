package org.YoutubeApp.dto.request;

import com.huseyint.entity.enums.ECategory;

public class VideoUpdateRequestDTO {
	
	private Long id;
	private Long uploaderId;
	private String title;
	private String description;
	private ECategory category;
	
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
}