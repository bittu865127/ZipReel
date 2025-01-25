package com.zipreel.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zipreel.icache.Cache;
import com.zipreel.model.Movie;

public class L2Cache implements Cache {
	private final int MAX_ENTRIES = 20; // Maximum 20 entries
	private Map<Integer, Movie> cache;
	private Map<Integer, Integer> accessCount;

	public L2Cache() {
		this.cache = new HashMap<>();
		this.accessCount = new LinkedHashMap<>();
	}

	@Override
	public void put(int userId, Movie movie) {
		if (cache.size() >= MAX_ENTRIES) {
			evict(userId); // Evict the least frequently used movie
		}
		cache.put(movie.getMovieId(), movie);
		accessCount.put(movie.getMovieId(), 0); // Initialize access count
	}

	@Override
	public Movie get(int userId, int movieId) {
		Movie movie = cache.get(movieId);
		if (movie != null) {
			// Increment access count
			accessCount.put(movieId, accessCount.get(movieId) + 1);
			return movie;
		}
		return null; // Not found
	}
	

	@Override
	public Movie get(int userId, String key) {
		return null;
	}
	
	@Override
	public Movie get(int userId, String genre, int releaseYear, Double minRating) {
		return null;
}

	@Override
	public void remove(int userId, int movieId) {
		cache.remove(movieId);
		accessCount.remove(movieId);
	}

	@Override
	public boolean contains(int userId, int movieId) {
		return get(userId, movieId) != null;
	}

	@Override
	public void evict(int userId) {
		// Find the least frequently used movie
		int leastUsedKey = Collections.min(accessCount.entrySet(), Map.Entry.comparingByValue()).getKey();
		cache.remove(leastUsedKey);
		accessCount.remove(leastUsedKey);
	}
}