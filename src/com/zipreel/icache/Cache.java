package com.zipreel.icache;

import com.zipreel.model.Movie;

public interface Cache {

	void evict(int userId);

	void put(int userId,Movie movie);

	Movie get(int userId,int movieId);
	
	Movie get(int userId,String key);

	void remove(int userId,int movieId);

	boolean contains(int userId,int movieId);
}