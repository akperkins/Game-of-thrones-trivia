package com.GameOfThrones.Trivia.util;

public class Session {
	private static Session instance;
	private String username;
	private int textSize;
	private int background;

	private Session() {
		username = "";
		textSize = 0;
		background = 0;
	}

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

}