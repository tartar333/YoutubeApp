package org.YoutubeApp.gui;

import com.huseyint.entity.Comment;
import com.huseyint.repository.CommentRepository;

public class CommentGui {
	public static void main(String[] args) {
		Comment comment = new Comment(1L,1L,"yorum");
		CommentRepository commentRepository = new CommentRepository();
		
		commentRepository.save(comment);
	}
}