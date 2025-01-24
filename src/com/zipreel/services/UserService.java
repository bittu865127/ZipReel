package com.zipreel.services;

import java.util.HashMap;
import java.util.Map;

import com.zipreel.exception.DuplicateEntryException;
import com.zipreel.iservices.IUserService;
import com.zipreel.model.User;

public class UserService implements IUserService {
	private Map<Integer, User> userDatabase = new HashMap<>();

	@Override
	public void addUser(int userId, String name, String preferredGenre) throws DuplicateEntryException {
		if (userDatabase.containsKey(userId)) {
			throw new DuplicateEntryException("User  with ID " + userId + " already exists.");
		}
		User user = new User(userId, name, preferredGenre);
		userDatabase.put(userId, user);
		System.out.println("User  '" + name + "' added successfully.");
	}
}