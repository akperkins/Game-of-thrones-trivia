package com.GameOfThrones.Trivia.util;

public class Session {
	private static Session instance;
	private String username;
	private int textSize;

	private Session() {
		username = "";
		textSize = 0;
	}

	public Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

}