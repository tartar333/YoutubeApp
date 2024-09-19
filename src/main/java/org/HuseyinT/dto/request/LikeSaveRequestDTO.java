package org.YoutubeApp.dto.request;

import com.huseyint.entity.enums.ELikeStatus;

public class LikeSaveRequestDTO {
	private Long userId;
	private Long videoId;
	private ELikeStatus likeStatus;
	
	public LikeSaveRequestDTO(Long userId, Long videoId, ELikeStatus likeStatus) {
		this.userId = userId;
		this.videoId = videoId;
		this.likeStatus = likeStatus;
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
	
	public ELikeStatus getLikeStatus() {
		return likeStatus;
	}
	
	public void setLikeStatus(ELikeStatus likeStatus) {
		this.likeStatus = likeStatus;
	}
}