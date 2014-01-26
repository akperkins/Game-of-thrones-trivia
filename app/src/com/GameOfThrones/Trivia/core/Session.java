package com.GameOfThrones.Trivia.core;

import java.util.ArrayList;

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
	protected static Session instance;
	/**
	 * drawable reference to background image when in portrait mode
	 */

	protected ArrayList<GameCharacter> characters;
	/**
	 * Used to know which questions reference which GameCharacter
	 */
	
	protected CharacterToQuestionsMap map;

	/**
	 * private Constructor - ensures only this class can create session instance
	 */
	private Session() {
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