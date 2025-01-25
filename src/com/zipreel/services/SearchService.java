package com.zipreel.services;

import java.util.ArrayList;
import java.util.List;

import com.zipreel.cache.L1Cache;
import com.zipreel.cache.L2Cache;
import com.zipreel.exception.MovieNotFoundException;
import com.zipreel.iservices.ISearchService;
import com.zipreel.model.Movie;

public class SearchService implements ISearchService {
	MovieService MovieService;
	L1Cache l1Cache;
	L2Cache l2Cache;

	public SearchService(MovieService MovieService, L1Cache l1Cache, L2Cache l2Cache) {
		this.MovieService = MovieService;
		this.l1Cache = new L1Cache();
		this.l2Cache = new L2Cache();
	}

	@Override
	public List<Movie> searchByTitle(int userId, String title) throws MovieNotFoundException {
		if (title == null || title.trim().isEmpty()) {
			throw new IllegalArgumentException("Title cannot be null or empty");
		}

		List<Movie> cachedResults = new ArrayList<>();
		Movie cachedMovie = l1Cache.get(userId, title);
		if (cachedMovie != null) {
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L1 Cache");
			return cachedResults; // Return cached result
		}

		System.out.println();
		// Check L2 Cache
		cachedMovie = l2Cache.get(userId, title);
		if (cachedMovie != null) {
			l1Cache.put(userId, cachedMovie); // Update L1 Cache
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L2 Cache");
			return cachedResults; // Return cached result
		}

		List<Movie> results = new ArrayList<>();

		for (Movie movie : MovieService.movieDatabase.values()) {
			if (movie != null && movie.getTitle() != null && movie.getTitle().equalsIgnoreCase(title)) {
				results.add(movie);
				l1Cache.put(userId, movie); // Update L1 Cache
				l2Cache.put(userId, movie); // Update L2 Cache
			}
		}

		if (results.isEmpty()) {
			throw new MovieNotFoundException("No movies found with title: " + title);
		}
		return results;
	}

	@Override
	public List<Movie> searchByGenre(int userId, String genre) throws MovieNotFoundException {

		if (genre == null || genre.trim().isEmpty()) {
			throw new IllegalArgumentException("Title cannot be null or empty");
		}

		List<Movie> cachedResults = new ArrayList<>();
		Movie cachedMovie = l1Cache.get(userId, genre);
		if (cachedMovie != null) {
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L1 Cache");
			return cachedResults; // Return cached result
		}

		// Check L2 Cache
		cachedMovie = l2Cache.get(userId, genre);
		if (cachedMovie != null) {
			l1Cache.put(userId, cachedMovie); // Update L1 Cache
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L2 Cache");
			return cachedResults; // Return cached result
		}

		List<Movie> results = new ArrayList<>();

		for (Movie movie : MovieService.movieDatabase.values()) {
			if (movie != null && movie.getGenre() != null && movie.getGenre().equalsIgnoreCase(genre)) {
				results.add(movie);
				l1Cache.put(userId, movie); // Update L1 Cache
				l2Cache.put(userId, movie);
			}
		}
		if (results.isEmpty()) {
			throw new MovieNotFoundException("No movies found with genre: " + genre);
		}
		return results;
	}

	@Override
	public List<Movie> searchByReleaseYear(int userId, int year) throws MovieNotFoundException {

		if (year < 0) {
			throw new IllegalArgumentException("Title cannot be null or empty");
		}

		List<Movie> cachedResults = new ArrayList<>();
		Movie cachedMovie = l1Cache.get(userId, year);
		if (cachedMovie != null) {
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L1 Cache");
			return cachedResults; // Return cached result
		}

		// Check L2 Cache
		cachedMovie = l2Cache.get(userId, year);
		if (cachedMovie != null) {
			l1Cache.put(userId, cachedMovie); // Update L1 Cache
			cachedResults.add(cachedMovie);
			System.out.println("List of filtered movies with cache level indicator: L2 Cache");
			return cachedResults; // Return cached result
		}

		List<Movie> results = new ArrayList<>();
		for (Movie movie : MovieService.movieDatabase.values()) {
			if (movie.getReleaseYear() == year) {
				l1Cache.put(userId, movie); // Update L1 Cache
				l2Cache.put(userId, movie);
				results.add(movie);
			}
		}
		if (results.isEmpty()) {
			throw new MovieNotFoundException("No movies found released in year: " + year);
		}
		return results;
	}

	@Override
	public List<Movie> searchMovies(int userId, String title, String genre, Integer releaseYear, Double minRating)
			throws MovieNotFoundException {
		List<Movie> results = new ArrayList<>();
		for (Movie movie : MovieService.movieDatabase.values()) {
			boolean matches = true;

			if (title == null && !movie.getTitle().equalsIgnoreCase(title)) {
				matches = false;
			}
			if (genre == null && !movie.getGenre().equalsIgnoreCase(genre)) {
				matches = false;
			}
			if (releaseYear == null && movie.getReleaseYear() != releaseYear) {
				matches = false;
			}
			if (minRating == null && movie.getRating() < minRating) {
				matches = false;
			}

			List<Movie> cachedResults = new ArrayList<>();
			Movie cachedMovie = l1Cache.get(userId, genre, releaseYear, minRating);
			if (cachedMovie != null) {
				cachedResults.add(cachedMovie);
				System.out.println("List of filtered movies with cache level indicator: L1 Cache");
				return cachedResults; // Return cached result
			}

		}
		if (results.isEmpty()) {
			throw new MovieNotFoundException("No movies found matching the given criteria.");
		}
		return results;
	}

}