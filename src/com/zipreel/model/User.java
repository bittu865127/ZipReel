package com.zipreel.model;

public class User {
    private int userId; // Unique ID
    private String name;
    private String preferredGenre;

    public User(int userId, String name, String preferredGenre) {
        this.userId = userId;
        this.name = name;
        this.preferredGenre = preferredGenre;
    }

    public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPreferredGenre(String preferredGenre) {
		this.preferredGenre = preferredGenre;
	}

	// Getters
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPreferredGenre() {
        return preferredGenre;
    }

    @Override
    public String toString() {
        return "User {" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", preferredGenre='" + preferredGenre + '\'' +
                '}';
    }
}