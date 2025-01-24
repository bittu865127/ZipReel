package com.zipreel.iservices;

import com.zipreel.exception.DuplicateEntryException;

public interface IMovieService {
	void addMovie(int movieId, String title, String genre, int releaseYear, double rating)
			throws DuplicateEntryException;
}