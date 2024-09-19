package org.YoutubeApp.service;

import com.huseyint.dto.request.VideoSaveRequestDTO;
import com.huseyint.dto.request.VideoUpdateRequestDTO;
import com.huseyint.dto.response.VideoResponseDTO;
import com.huseyint.entity.Video;
import com.huseyint.entity.enums.ECategory;
import com.huseyint.repository.VideoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VideoService {
	
	private final VideoRepository videoRepository;
	
	public VideoService() {
		this.videoRepository = new VideoRepository();
	}
	
	// Yeni bir video kaydetmek için
	public Optional<VideoResponseDTO> saveVideo(VideoSaveRequestDTO requestDTO) {
		try {
			Video video = new Video(
					requestDTO.getUploaderId(),
					requestDTO.getTitle(),
					requestDTO.getDescription(),
					requestDTO.getCategory(),
					0L, // İlk kayıtta viewCount, likeCount ve dislikeCount 0 olarak başlatılır.
					0L,
					0L
			);
			
			Optional<Video> savedVideo = videoRepository.save(video);
			if (savedVideo.isPresent()) {
				System.out.println("VideoService: Video başarıyla kaydedildi.");
			}
			return savedVideo.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("VideoService: Video kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Mevcut bir videoyu güncellemek için
	public Optional<VideoResponseDTO> updateVideo(VideoUpdateRequestDTO requestDTO) {
		try {
			Optional<Video> existingVideo = videoRepository.findById(requestDTO.getId());
			
			if (existingVideo.isPresent()) {
				Video video = existingVideo.get();
				video.setUploaderId(requestDTO.getUploaderId());
				video.setTitle(requestDTO.getTitle());
				video.setDescription(requestDTO.getDescription());
				video.setCategory(requestDTO.getCategory());
				
				Optional<Video> updatedVideo = videoRepository.update(video);
				if (updatedVideo.isPresent()) {
					System.out.println("VideoService: Video başarıyla güncellendi.");
				}
				return updatedVideo.map(this::mapToResponseDTO);
			} else {
				System.out.println("VideoService: Güncellenecek video bulunamadı.");
			}
			
			return Optional.empty();
		} catch (Exception e) {
			System.err.println("VideoService: Video güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Video silmek için (soft-delete)
	public void softDelete(Long videoId) {
		try {
			videoRepository.softDelete(videoId);
			System.out.println("UserService: Video başarıyla soft-delete ile silindi.");
		} catch (Exception e) {
			System.err.println("UserService: Kullanıcı silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Tüm videoları listelemek için
	public List<VideoResponseDTO> getAllVideos() {
		try {
			List<VideoResponseDTO> videos = videoRepository.findAll()
			                                               .stream()
			                                               .map(this::mapToResponseDTO)
			                                               .collect(Collectors.toList());
			System.out.println("VideoService: Tüm videolar başarıyla listelendi.");
			return videos;
		} catch (Exception e) {
			System.err.println("VideoService: Videolar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of(); // Hata durumunda boş bir liste döndürülür.
		}
	}
	
	// ID'ye göre videoyu bulmak için
	public Optional<VideoResponseDTO> getVideoById(Long id) {
		try {
			Optional<Video> video = videoRepository.findById(id);
			if (video.isPresent()) {
				System.out.println("VideoService: Video başarıyla bulundu.");
			} else {
				System.out.println("VideoService: Video bulunamadı.");
			}
			return video.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("VideoService: Video bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Başlık ile video aramak
	public List<VideoResponseDTO> findVideosByTitle(String title) {
		try {
			List<Video> videos = videoRepository.findVideosByTitle(title);
			return videos.stream()
			             .map(this::mapToResponseDTO)
			             .collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("VideoService: Videolar aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Kullanıcının videolarını listelemek
	public List<VideoResponseDTO> findVideosOfUser(Long userId) {
		try {
			List<Video> videos = videoRepository.findVideosOfUser(userId);
			return videos.stream()
			             .map(this::mapToResponseDTO)
			             .collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("VideoService: Kullanıcının videoları aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// İzlenme sayısını artırmak
	public void increaseViewCount(Long videoId) {
		try {
			videoRepository.increaseViewCount(videoId);
			System.out.println("VideoService: İzlenme sayısı başarıyla artırıldı.");
		} catch (Exception e) {
			System.err.println("VideoService: İzlenme sayısı artırılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Trend olan videoları listelemek
	public List<VideoResponseDTO> findTrendingVideos(int limit) {
		try {
			List<Video> videos = videoRepository.findTrendingVideos(limit);
			return videos.stream()
			             .map(this::mapToResponseDTO)
			             .collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("VideoService: Trend videolar aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Belirli bir kategoriye ait videoları listelemek
	public List<VideoResponseDTO> findVideosByCategory(ECategory category) {
		try {
			List<Video> videos = videoRepository.findVideosByCategory(category);
			return videos.stream()
			             .map(this::mapToResponseDTO)
			             .collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("VideoService: Kategoriye göre videolar aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Videonun başlığını güncellemek
	public void updateVideoTitle(Long videoId, String newTitle) {
		try {
			videoRepository.updateVideoTitle(videoId, newTitle);
			System.out.println("VideoService: Video başlığı başarıyla güncellendi.");
		} catch (Exception e) {
			System.err.println("VideoService: Video başlığı güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Videonun açıklamasını güncellemek
	public void updateVideoDescription(Long videoId, String newDescription) {
		try {
			videoRepository.updateVideoDescription(videoId, newDescription);
			System.out.println("VideoService: Video açıklaması başarıyla güncellendi.");
		} catch (Exception e) {
			System.err.println("VideoService: Video açıklaması güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Videonun beğeni sayısını artırmak
	public void incrementLikeCount(Long videoId) {
		try {
			videoRepository.incrementLikeCount(videoId);
			System.out.println("VideoService: Video beğeni sayısı başarıyla artırıldı.");
		} catch (Exception e) {
			System.err.println("VideoService: Video beğeni sayısı artırılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Videonun dislike sayısını artırmak
	public void incrementDislikeCount(Long videoId) {
		try {
			videoRepository.incrementDislikeCount(videoId);
			System.out.println("VideoService: Video dislike sayısı başarıyla artırıldı.");
		} catch (Exception e) {
			System.err.println("VideoService: Video dislike sayısı artırılırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Video entity'sini VideoResponseDTO'ya dönüştürmek için yardımcı metot
	private VideoResponseDTO mapToResponseDTO(Video video) {
		VideoResponseDTO responseDTO = new VideoResponseDTO();
		responseDTO.setId(video.getId());
		responseDTO.setUploaderId(video.getUploaderId());
		responseDTO.setTitle(video.getTitle());
		responseDTO.setDescription(video.getDescription());
		responseDTO.setCategory(video.getCategory());
		responseDTO.setViewCount(video.getViewCount().intValue());
		responseDTO.setLikeCount(video.getLikeCount().intValue());
		responseDTO.setDislikeCount(video.getDislikeCount().intValue());
		return responseDTO;
	}
}