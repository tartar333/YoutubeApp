package org.YoutubeApp.dto.request;

public class CommentSaveRequestDTO {
	
	private String content;
	private Long videoId;
	private Long userId;
	
	public CommentSaveRequestDTO(String content, Long videoId, Long userId) {
		this.content = content;
		this.videoId = videoId;
		this.userId = userId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
}