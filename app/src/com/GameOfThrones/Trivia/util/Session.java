package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Characters.GameCharacter;

public class Session {
	private static Session instance;
	private String username;
	private int textSize;
	private int background;
	private ArrayList<GameCharacter> characters;

	private Session() {
		username = "";
		textSize = 0;
		background = 0;
		characters = new ArrayList<GameCharacter>();
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

	/**
	 * @return the characters
	 */
	public ArrayList<GameCharacter> getCharacters() {
		return characters;
	}

	/**
	 * @param characters
	 *            the characters to set
	 */
	public void setCharacters(ArrayList<GameCharacter> characters) {
		this.characters = characters;
	}

	/**
	 * @param object
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(GameCharacter object) {
		return characters.add(object);
	}

}