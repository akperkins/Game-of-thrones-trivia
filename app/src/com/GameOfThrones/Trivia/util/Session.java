package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Characters.CharacterToQuestionsMap;
import com.GameOfThrones.Trivia.Characters.GameCharacter;

public class Session {
	private static Session instance;
	private String username;
	private int background;
	private ArrayList<GameCharacter> characters;
	CharacterToQuestionsMap map;

	private Session() {
		username = "";
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
		map = new CharacterToQuestionsMap(characters);
	}

	/**
	 * @param object
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(GameCharacter object) {
		return characters.add(object);
	}

	/**
	 * @return the map
	 */
	public CharacterToQuestionsMap getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(CharacterToQuestionsMap map) {
		this.map = map;
	}

}