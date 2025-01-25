package com.zipreel.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.zipreel.icache.Cache;
import com.zipreel.model.Movie;

public class L1Cache implements Cache {
	private final int MAX_ENTRIES = 5; // Maximum 5 entries per user
	private Map<Integer, LinkedHashMap<Integer, Movie>> userCache;

	public L1Cache() {
		this.userCache = new LinkedHashMap<>();
	}

	@Override
	public void put(int userId, Movie movie) {
		userCache.computeIfAbsent(userId, k -> new LinkedHashMap<>());

		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		movies.put(movie.getMovieId(), movie);

		// If the size exceeds MAX_ENTRIES, remove the least recently used entry
		if (movies.size() > MAX_ENTRIES) {
			evict(userId);
		}
	}

	@Override
	public Movie get(int userId, int movieId) {
		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		if (movies != null) {
			Movie movie = movies.get(movieId);
			if (movie != null) {
				// Move the accessed movie to the end to mark it as recently used
				movies.remove(movieId);
				movies.put(movieId, movie);
				return movie;
			}
		}
		return null; // Not found
	}

	@Override
	public Movie get(int userId, String key) {
		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		if (movies != null) {
			Movie movie = movies.get(key);
			if (movie != null) {
				// Move the accessed movie to the end to mark it as recently used
				movies.remove(key);
				movies.put(userId, movie);
				return movie;
			}
		}
		return null;
	}

	@Override
	public Movie get(int userId, String genre, int releaseYear, Double minRating) {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		if (movies != null) {
			// Iterate through the movies to find a match based on the provided parameters
			for (Movie movie : movies.values()) {
				boolean matches = true;

				// Check genre
				if (genre != null && !movie.getGenre().equalsIgnoreCase(genre)) {
					matches = false;
				}

				// Check release year
				if (releaseYear >= 0 && movie.getReleaseYear() != releaseYear) {
					matches = false;
				}

				// Check minimum rating
				if (minRating != null && movie.getRating() < minRating) {
					matches = false;
				}

				// If all criteria match, return the movie
				if (matches) {
					// Move the accessed movie to the end to mark it as recently used
					movies.remove(movie.getMovieId()); // Assuming movieId is the key
					movies.put(movie.getMovieId(), movie); // Reinsert to mark as recently used
					return movie;
				}
			}

		}
		return null;
	}

	@Override
	public void remove(int userId, int movieId) {
		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		if (movies != null) {
			movies.remove(movieId);
		}
	}

	@Override
	public boolean contains(int userId, int movieId) {
		return get(userId, movieId) != null;
	}

	@Override
	public void evict(int userId) {
		LinkedHashMap<Integer, Movie> movies = userCache.get(userId);
		if (movies != null && !movies.isEmpty()) {
			Integer oldestKey = movies.keySet().iterator().next();
			movies.remove(oldestKey);
		}
	}

}