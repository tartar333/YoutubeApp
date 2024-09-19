package org.YoutubeApp.gui;


import com.huseyint.entity.Video;
import com.huseyint.entity.enums.ECategory;
import com.huseyint.repository.VideoRepository;

public class VideoGui {
	public static void main(String[] args) {
		Video video = new Video(1L, "transfer", "açıklama", ECategory.SPORTS,0L,0L,0L);
		Video video1 = new Video(2L,"trabzonspor","açıklama",ECategory.SPORTS,0L,0L,0L);
		Video video2 = new Video(4L,"zayıflama","açıklama",ECategory.TECHNOLOGY,0L,0L,0L);
		
		VideoRepository videoRepository = new VideoRepository();
		
//		videoRepository.save(video2);
		
//		videoRepository.findAll().forEach(System.out::println);
		
//		System.out.println(videoRepository.findById(1L).get());
		
//		video2.setId(3L);
//		video2.setTitle("fenerbahçe");
//		video2.setCategory(ECategory.SPORTS);
//		videoRepository.update(video2);
		
//		videoRepository.delete(3L);
	}
}