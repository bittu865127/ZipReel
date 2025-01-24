package com.zipreel.iservices;

import com.zipreel.exception.MovieNotFoundException;
import com.zipreel.model.Movie;

import java.util.List;

public interface ISearchService {
    List<Movie> searchByTitle(int userId, String title) throws MovieNotFoundException;
    List<Movie> searchByGenre(int userId,String genre) throws MovieNotFoundException;
    List<Movie> searchByReleaseYear(int userId,int year) throws MovieNotFoundException;
    List<Movie> searchMovies(int userId,String title, String genre, Integer releaseYear, Double minRating) throws MovieNotFoundException;
}