package org.YoutubeApp.controller;

import com.huseyint.dto.request.VideoSaveRequestDTO;
import com.huseyint.dto.request.VideoUpdateRequestDTO;
import com.huseyint.dto.response.VideoResponseDTO;
import com.huseyint.entity.enums.ECategory;
import com.huseyint.service.VideoService;

import java.util.List;
import java.util.Optional;

public class VideoController {
	
	private static VideoController instance;
	private final VideoService videoService;
	
	public VideoController() {
		this.videoService = new VideoService();
	}
	
	public static synchronized VideoController getInstance(){
		if (instance == null){
			instance = new VideoController();
		}
		return instance;
	}
	
	public Optional<VideoResponseDTO> createVideo(VideoSaveRequestDTO requestDTO) {
		return videoService.saveVideo(requestDTO);
	}
	
	public Optional<VideoResponseDTO> updateVideo(VideoUpdateRequestDTO requestDTO) {
		return videoService.updateVideo(requestDTO);
	}
	
	public void softDeleteVideo(Long id) {
		videoService.softDelete(id);
	}
	
	public List<VideoResponseDTO> getAllVideos() {
		return videoService.getAllVideos();
	}
	
	public Optional<VideoResponseDTO> getVideoById(Long id) {
		return videoService.getVideoById(id);
	}
	
	public List<VideoResponseDTO> findVideosByTitle(String title) {
		return videoService.findVideosByTitle(title);
	}
	
	public List<VideoResponseDTO> findVideosOfUser(Long userId) {
		return videoService.findVideosOfUser(userId);
	}
	
	public void increaseViewCount(Long videoId) {
		videoService.increaseViewCount(videoId);
	}
	
	public List<VideoResponseDTO> findTrendingVideos(int limit) {
		return videoService.findTrendingVideos(limit);
	}
	
	public List<VideoResponseDTO> findVideosByCategory(ECategory category) {
		return videoService.findVideosByCategory(category);
	}
	
	public void updateVideoTitle(Long videoId, String newTitle) {
		videoService.updateVideoTitle(videoId, newTitle);
	}
	
	public void updateVideoDescription(Long videoId, String newDescription) {
		videoService.updateVideoDescription(videoId, newDescription);
	}
	
	public void incrementLikeCount(Long videoId) {
		videoService.incrementLikeCount(videoId);
	}
	
	public void incrementDislikeCount(Long videoId) {
		videoService.incrementDislikeCount(videoId);
	}
}