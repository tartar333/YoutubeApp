package org.YoutubeApp.dto.response;

public class CommentResponseDTO {
	
	private Long userId;
	private Long videoId;
	private String content;
	
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "CommentResponseDTO{" + "userId=" + getUserId() + ", videoId=" + getVideoId() + ", content='" + getContent() + '\'' + '}';
	}
}