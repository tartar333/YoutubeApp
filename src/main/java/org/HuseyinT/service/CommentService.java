package org.YoutubeApp.service;

import com.huseyint.dto.request.CommentSaveRequestDTO;
import com.huseyint.dto.request.CommentUpdateRequestDTO;
import com.huseyint.dto.response.CommentResponseDTO;
import com.huseyint.entity.Comment;
import com.huseyint.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public CommentService() {
		this.commentRepository = new CommentRepository();
	}
	
	// Yeni bir yorum kaydetmek için
	public Optional<CommentResponseDTO> saveComment(CommentSaveRequestDTO requestDTO) {
		try {
			Comment comment = new Comment(
					requestDTO.getUserId(),
					requestDTO.getVideoId(),
					requestDTO.getContent()
			);
			
			Optional<Comment> savedComment = commentRepository.save(comment);
			if (savedComment.isPresent()) {
				System.out.println("CommentService: Yorum başarıyla kaydedildi.");
			}
			return savedComment.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("CommentService: Yorum kaydedilirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Mevcut bir yorumu güncellemek için
	public Optional<CommentResponseDTO> updateComment(CommentUpdateRequestDTO requestDTO) {
		try {
			Optional<Comment> existingComment = commentRepository.findById(requestDTO.getId());
			
			if (existingComment.isPresent()) {
				Comment comment = existingComment.get();
				comment.setUserId(requestDTO.getUserId());
				comment.setVideoId(requestDTO.getVideoId());
				comment.setComment(requestDTO.getContent());
				
				Optional<Comment> updatedComment = commentRepository.update(comment);
				if (updatedComment.isPresent()) {
					System.out.println("CommentService: Yorum başarıyla güncellendi.");
				}
				return updatedComment.map(this::mapToResponseDTO);
			} else {
				System.out.println("CommentService: Güncellenecek yorum bulunamadı.");
			}
			
			return Optional.empty();
		} catch (Exception e) {
			System.err.println("CommentService: Yorum güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Yorum silmek için (soft-delete)
	public void softDelete(Long commentId) {
		try {
			commentRepository.softDelete(commentId);
			System.out.println("CommentService: Yorum başarıyla soft-delete ile silindi.");
		} catch (Exception e) {
			System.err.println("CommentService: Yorum silinirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Tüm yorumları listelemek için
	public List<CommentResponseDTO> getAllComments() {
		try {
			List<CommentResponseDTO> comments = commentRepository.findAll()
			                                                     .stream()
			                                                     .map(this::mapToResponseDTO)
			                                                     .collect(Collectors.toList());
			System.out.println("CommentService: Tüm yorumlar başarıyla listelendi.");
			return comments;
		} catch (Exception e) {
			System.err.println("CommentService: Yorumlar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of(); // Hata durumunda boş bir liste döndürülür.
		}
	}
	
	// ID'ye göre yorumu bulmak için
	public Optional<CommentResponseDTO> getCommentById(Long id) {
		try {
			Optional<Comment> comment = commentRepository.findById(id);
			if (comment.isPresent()) {
				System.out.println("CommentService: Yorum başarıyla bulundu.");
			} else {
				System.out.println("CommentService: Yorum bulunamadı.");
			}
			return comment.map(this::mapToResponseDTO);
		} catch (Exception e) {
			System.err.println("CommentService: Yorum bulunurken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	// Belirli bir videoya ait yorumları listelemek için
	public List<CommentResponseDTO> findCommentsByVideoId(Long videoId) {
		try {
			List<CommentResponseDTO> comments = commentRepository.findCommentsByVideoId(videoId)
			                                                     .stream()
			                                                     .map(this::mapToResponseDTO)
			                                                     .collect(Collectors.toList());
			System.out.println("CommentService: Videoya ait yorumlar başarıyla listelendi.");
			return comments;
		} catch (Exception e) {
			System.err.println("CommentService: Videoya ait yorumlar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Belirli bir kullanıcının yaptığı yorumları listelemek
	public List<CommentResponseDTO> findCommentsByUserId(Long userId) {
		try {
			List<CommentResponseDTO> comments = commentRepository.findCommentsByUserId(userId)
			                                                     .stream()
			                                                     .map(this::mapToResponseDTO)
			                                                     .collect(Collectors.toList());
			System.out.println("CommentService: Kullanıcıya ait yorumlar başarıyla listelendi.");
			return comments;
		} catch (Exception e) {
			System.err.println("CommentService: Kullanıcıya ait yorumlar listelenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
	
	// Yorumun durumunu güncellemek için
	public void updateCommentState(Long commentId, Byte newState) {
		try {
			commentRepository.updateCommentState(commentId, newState);
			System.out.println("CommentService: Yorum durumu başarıyla güncellendi.");
		} catch (Exception e) {
			System.err.println("CommentService: Yorum durumu güncellenirken hata oluştu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Comment entity'sini CommentResponseDTO'ya dönüştürmek için yardımcı metot
	private CommentResponseDTO mapToResponseDTO(Comment comment) {
		CommentResponseDTO responseDTO = new CommentResponseDTO();
		responseDTO.setUserId(comment.getUserId());
		responseDTO.setVideoId(comment.getVideoId());
		responseDTO.setContent(comment.getComment());
		return responseDTO;
	}
}