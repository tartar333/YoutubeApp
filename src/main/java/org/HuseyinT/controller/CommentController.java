package org.YoutubeApp.controller;

import com.huseyint.dto.request.CommentSaveRequestDTO;
import com.huseyint.dto.request.CommentUpdateRequestDTO;
import com.huseyint.dto.response.CommentResponseDTO;
import com.huseyint.service.CommentService;

import java.util.List;
import java.util.Optional;

public class CommentController {
	private static CommentController instance;
	private final CommentService commentService;
	
	public CommentController() {
		this.commentService = new CommentService();
	}
	
	public static synchronized CommentController getInstance(){
		if (instance == null){
			instance = new CommentController();
		}
		return instance;
	}
	
	public Optional<CommentResponseDTO> createComment(CommentSaveRequestDTO requestDTO) {
		return commentService.saveComment(requestDTO);
	}
	
	public Optional<CommentResponseDTO> updateComment(CommentUpdateRequestDTO requestDTO) {
		return commentService.updateComment(requestDTO);
	}
	
	public void softDeleteComment(Long commentId) {
		commentService.softDelete(commentId);
	}
	
	public List<CommentResponseDTO> getAllComments() {
		return commentService.getAllComments();
	}
	
	public Optional<CommentResponseDTO> getCommentById(Long id) {
		return commentService.getCommentById(id);
	}
	
	public List<CommentResponseDTO> findCommentsByVideoId(Long videoId) {
		return commentService.findCommentsByVideoId(videoId);
	}
	
	public List<CommentResponseDTO> findCommentsByUserId(Long userId) {
		return commentService.findCommentsByUserId(userId);
	}
	
	public void updateCommentState(Long commentId, Byte newState) {
		commentService.updateCommentState(commentId, newState);
	}
}