package com.zipreel.services;

import java.util.HashMap;
import java.util.Map;

import com.zipreel.exception.DuplicateEntryException;
import com.zipreel.iservices.IMovieService;
import com.zipreel.model.Movie;

public class MovieService implements IMovieService {
	protected Map<Integer, Movie> movieDatabase = new HashMap<>();

	@Override
	public void addMovie(int movieId, String title, String genre, int releaseYear, double rating)
			throws DuplicateEntryException {
		if (movieDatabase.containsKey(movieId)) {
			throw new DuplicateEntryException("Movie with ID " + movieId + " already exists.");
		}
		Movie movie = new Movie(movieId, title, genre, releaseYear, rating);
		movieDatabase.put(movieId, movie);
		System.out.println("Movie '" + title + "' added successfully.");
	}
}