package org.YoutubeApp.gui;

import com.huseyint.entity.Like;
import com.huseyint.entity.enums.ELikeStatus;
import com.huseyint.repository.LikeRepository;

public class LikeGui {
	public static void main(String[] args) {
		Like like = new Like(ELikeStatus.DISLIKE, 1L, 1L);
		Like like1 = new Like(ELikeStatus.LIKE, 2L, 2L);
		
		LikeRepository likeRepository = new LikeRepository();
		
//		likeRepository.save(like1);
		
//		likeRepository.findAll().forEach(System.out::println);
		
//		System.out.println(likeRepository.findById(1L).get());
		
//		like.setId(1L);
//		like.setLikeStatus(ELikeStatus.LIKE);
//		likeRepository.update(like);
		
		
	}
}