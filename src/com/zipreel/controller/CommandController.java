package com.zipreel.controller;

import java.util.List;
import java.util.Scanner;

import com.zipreel.exception.DuplicateEntryException;
import com.zipreel.model.Movie;
import com.zipreel.services.MovieService;
import com.zipreel.services.SearchService;
import com.zipreel.services.UserService;

public class CommandController {
	private final MovieService movieService;
	private final UserService userService;
	private final SearchService searchService;

	public CommandController(MovieService movieService, UserService userService, SearchService searchService) {
		this.movieService = movieService;
		this.userService = userService;
		this.searchService = searchService;
	}

	Scanner scanner = new Scanner(System.in);

	public void executeCommand(String command) {

		try {
			switch (command) {
			case "ADD_MOVIE":
				System.out.println("Enter Movie Details: ");
				String movie_details = scanner.nextLine();

				String[] movie = movie_details.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

				// Remove quotes and trim whitespace
				for (int i = 0; i < movie.length; i++) {
					movie[i] = movie[i].replace("\"", "").trim();
				}

				// String[] movie = movie_details.split(" ", 5);
				if (movie.length < 5) {
					System.out.println("Invalid movie details. Please provide all required fields.");
					return;
				}

				int movieId = Integer.parseInt(movie[0]);
				String title = movie[1]; // Title can contain spaces
				String genre = movie[2]; // Genre can contain spaces
				int releaseYear = Integer.parseInt(movie[3]);
				double rating = Double.parseDouble(movie[4]);
				movieService.addMovie(movieId, title, genre, releaseYear, rating);
				break;

			case "ADD_USER":
				System.out.println("Enter User Details: ");
				String user_details = scanner.nextLine();
				String[] user = user_details.split(" ", 3);
				if (user.length < 3) {
					System.out.println("Invalid user details. Please provide all required fields.");
					return;
				}
				int userId = Integer.parseInt(user[0]);
				String name = user[1].replace("\"", "");
				String preferredGenre = user[2].replace("\"", "");
				userService.addUser(userId, name, preferredGenre);
				break;

			case "SEARCH":

				System.out.println("Enter search Option between title, genre, releaseYear: ");
				String searchType = scanner.nextLine();
				System.out.println("Enter '" + searchType + "' Value: ");
				String searchInput = scanner.nextLine();
				String[] movies = searchInput.split(" ", 2);
				int userid = movies[0].equals("null") ? null : Integer.valueOf(movies[0]);
				String searchValue = movies[1].equals("null") ? null : movies[1].replace("\"", "");

				List<Movie> searchResults;

				if (searchType.toUpperCase().equals("GENRE")) {
					searchResults = searchService.searchByGenre(userid,searchValue.replace("\"", ""));
				} else if (searchType.toUpperCase().equals("TITLE")) {
					searchResults = searchService.searchByTitle(userid,searchValue.replace("\"", ""));
				} else if (searchType.toUpperCase().equals("RELEASEYEAR")) {
					searchResults = searchService.searchByReleaseYear(userid,Integer.parseInt(searchValue));
				} else {
					System.out.println("Unsupported search type: " + searchType);
					return;
				}
				// Output the results
				searchResults.forEach(System.out::println);
				break;

			case "SEARCH_MULTI":

				System.out.println("Enter Movie Details to search Example SEARCH_MULTI 1 Action 2020 8.0 ");
				String search_movie = scanner.nextLine();
				String[] movies1 = search_movie.split(" ", 4);

				if (movies1.length < 4) {
					System.out.println("Invalid search parameters. Please provide all required fields.");
					return;
				}
				int userid1 = movies1[0].equals("null") ? null : Integer.valueOf(movies1[0]);
				String searchTitle = movies1[1].equals("null") ? null : movies1[1].replace("\"", "");
				String searchGenre = movies1[2].equals("null") ? null : movies1[2].replace("\"", "");
				Integer searchYear = movies1[3].equals("null") ? null : Integer.valueOf(movies1[3]);
				Double searchRating = movies1[4].equals("null") ? null : Double.valueOf(movies1[4]);
				List<Movie> foundMovies = searchService.searchMovies(userid1, searchTitle, searchGenre, searchYear,
						searchRating);
				foundMovies.forEach(i -> System.out.println(i));
				break;

			default:
				System.out.println("Unknown command: " + command);
				break;
			}
		} catch (DuplicateEntryException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	public void start() {
		// Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to ZipReel!");
		while (true) {
			System.out.println("Enter commands: ");
			String command = scanner.nextLine();
			if (command.equalsIgnoreCase("EXIT")) {
				break;
			}
			executeCommand(command);
		}
		scanner.close();
	}
}