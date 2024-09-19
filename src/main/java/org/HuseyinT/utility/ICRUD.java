package org.YoutubeApp.utility;

import java.util.List;
import java.util.Optional;

public interface ICRUD<T> {
	Optional<T> save(T t);
	Optional<T> update (T t);
	void delete(Long id);
	List<T> findAll();
	Optional<T> findById(Long id);
}