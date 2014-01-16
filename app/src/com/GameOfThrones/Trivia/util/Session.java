package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Characters.CharacterToQuestionsMap;
import com.GameOfThrones.Trivia.Characters.GameCharacter;

/**
 * Singleton used to store persistent data throughout application
 * 
 * @author andre
 * 
 */
public class Session {
	/**
	 * Session object for this application execution
	 */
	private static Session instance;
	/**
	 * drawable reference to background image when in portrait mode
	 */
	private int backgroundPort;
	/**
	 * drawable reference to background image when in landscape mode
	 */
	private int backgroundLand;
	/**
	 * GameCharacters created created in application
	 */
	private ArrayList<GameCharacter> characters;
	/**
	 * Used to know which questions reference which GameCharacter
	 */
	CharacterToQuestionsMap map;

	/**
	 * private Constructor - ensures only this class can create session instance
	 */
	private Session() {
		backgroundPort = 0;
		backgroundLand = 0;
		characters = new ArrayList<GameCharacter>();
	}

	/**
	 * Session instance - guaranteed to always return same instance (Lazy
	 * instantiation)
	 * 
	 * @return
	 */
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
	 * @param backgroundPort
	 *            the backgroundPort to set
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
	 * @param backgroundLand
	 *            the backgroundLand to set
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