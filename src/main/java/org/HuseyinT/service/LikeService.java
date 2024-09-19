package org.YoutubeApp.service;

import com.huseyint.dto.request.LikeSaveRequestDTO;
import com.huseyint.dto.request.LikeUpdateRequestDTO;
import com.huseyint.entity.Like;
import com.huseyint.repository.LikeRepository;

import java.util.List;
import java.util.Optional;

public class LikeService {
	
	private final LikeRepository likeRepository;
	
	public LikeService() {
		this.likeRepository = new LikeRepository();
	}
	
	// Yeni bir like kaydetmek için
	public Optional<Like> saveLike(LikeSaveRequestDTO requestDTO) {
		try {
			Like like = new Like(
					requestDTO.getLikeStatus(),
					requestDTO.getVideoId(),
					requestDTO.getUserId()
			);
			
			Optional<Like> savedLike = likeRepository.save(like);
			if (savedLike.isPresent()) {
				System.out.println("LikeService: Beğeni başarıyla kaydedildi.");
			}
			return savedLike;
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Mevcut bir like'ı güncellemek için
	public Optional<Like> updateLike(LikeUpdateRequestDTO requestDTO) {
		try {
			Optional<Like> existingLike = likeRepository.findById(requestDTO.getId());
			
			if (existingLike.isPresent()) {
				Like like = existingLike.get();
				like.setUserId(requestDTO.getUserId());
				like.setVideoId(requestDTO.getVideoId());
				like.setLikeStatus(requestDTO.getLikeStatus());
				
				Optional<Like> updatedLike = likeRepository.update(like);
				if (updatedLike.isPresent()) {
					System.out.println("LikeService: Beğeni başarıyla güncellendi.");
				}
				return updatedLike;
			} else {
				System.out.println("LikeService: Güncellenecek beğeni bulunamadı.");
			}
			
			return Optional.empty();
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Like silmek için (soft-delete)
	public void softDelete(Long likeId) {
		try {
			likeRepository.softDelete(likeId);
			System.out.println("LikeService: Beğeni başarıyla soft-delete ile silindi.");
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Tüm beğenileri listelemek için
	public List<Like> getAllLikes() {
		try {
			List<Like> likes = likeRepository.findAll();
			System.out.println("LikeService: Tüm beğeniler başarıyla listelendi.");
			return likes;
		} catch (Exception e) {
			System.err.println("LikeService: Beğeniler listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of(); // Hata durumunda boş liste döndürülür.
		}
	}
	
	// ID'ye göre beğeniyi bulmak için
	public Optional<Like> getLikeById(Long id) {
		try {
			Optional<Like> like = likeRepository.findById(id);
			if (like.isPresent()) {
				System.out.println("LikeService: Beğeni başarıyla bulundu.");
			} else {
				System.out.println("LikeService: Beğeni bulunamadı.");
			}
			return like;
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	// Belirli bir videoya ait toplam beğeni sayısını getirmek
	public int countLikesByVideoId(Long videoId) {
		try {
			return likeRepository.countLikesByVideoId(videoId);
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni sayısı alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	// Belirli bir videoya ait toplam dislike sayısını getirmek
	public int countDislikesByVideoId(Long videoId) {
		try {
			return likeRepository.countDislikesByVideoId(videoId);
		} catch (Exception e) {
			System.err.println("LikeService: Dislike sayısı alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	// Bir kullanıcının yaptığı tüm beğenileri listelemek
	public List<Like> findLikesByUserId(Long userId) {
		try {
			return likeRepository.findLikesByUserId(userId);
		} catch (Exception e) {
			System.err.println("LikeService: Kullanıcı beğenileri alınırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Kullanıcı ve video ilişkili beğeni arama
	public Optional<Like> findByUserIdAndVideoId(Long userId, Long videoId) {
		try {
			return likeRepository.findByUserIdAndVideoId(userId, videoId);
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni aranırken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Bir beğeni var mı kontrolü
	public boolean isLikeExist(Long userId, Long videoId) {
		try {
			return likeRepository.isLikeExist(userId, videoId);
		} catch (Exception e) {
			System.err.println("LikeService: Beğeni kontrol edilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}