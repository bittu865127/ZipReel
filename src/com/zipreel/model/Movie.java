package com.zipreel.model;

public class Movie {
    private int movieId; // Unique ID
    private String title;
    private String genre;
    private int releaseYear;
    private double rating;

    public Movie(int movieId, String title, String genre, int releaseYear, double rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	// Getters
    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                '}';
    }
}