package com.zipreel.iservices;

import com.zipreel.exception.DuplicateEntryException;

public interface IUserService {
	void addUser(int userId, String name, String preferredGenre) throws DuplicateEntryException;
}