package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Characters.CharacterToQuestionsMap;
import com.GameOfThrones.Trivia.Characters.GameCharacter;

public class Session {
	private static Session instance;
	private String username;
	private int backgroundPort;
	private int backgroundLand;
	private ArrayList<GameCharacter> characters;
	CharacterToQuestionsMap map;

	private Session() {
		username = "";
		backgroundPort = 0;
		backgroundLand = 0;
		characters = new ArrayList<GameCharacter>();
	}

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	/**
	 * @return the backgroundPort
	 */
	public int getBackgroundPort() {
		return backgroundPort;
	}

	/**
	 * @param backgroundPort the backgroundPort to set
	 */
	public void setBackgroundPort(int backgroundPort) {
		this.backgroundPort = backgroundPort;
	}

	/**
	 * @return the backgroundLand
	 */
	public int getBackgroundLand() {
		return backgroundLand;
	}

	/**
	 * @param backgroundLand the backgroundLand to set
	 */
	public void setBackgroundLand(int backgroundLand) {
		this.backgroundLand = backgroundLand;
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