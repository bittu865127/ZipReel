package com.zipreel.application;

import com.zipreel.cache.L1Cache;
import com.zipreel.cache.L2Cache;
import com.zipreel.controller.CommandController;
import com.zipreel.services.MovieService;
import com.zipreel.services.SearchService;
import com.zipreel.services.UserService;

public class ZipReelApplication {
	public static void main(String[] args) {

		MovieService movieService = new MovieService();
		UserService userService = new UserService();
		L1Cache l1Cache = new L1Cache();
		L2Cache l2Cache= new L2Cache();

		// Initialize SearchService with the movie database
		SearchService searchService = new SearchService(movieService, l1Cache, l2Cache);

		// Initialize CommandController
		CommandController commandController = new CommandController(movieService, userService, searchService);

		// Start command processing
		commandController.start();

	}
}