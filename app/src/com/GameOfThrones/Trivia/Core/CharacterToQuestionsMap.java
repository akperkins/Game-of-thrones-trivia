package com.GameOfThrones.Trivia.Core;

import java.util.ArrayList;

/**
 * Creates and stores mapping info of characters to questions that reference the
 * characters.
 * 
 * @author andre
 * 
 */
public class CharacterToQuestionsMap {
	/**
	 * 
	 */
	protected ArrayList<ArrayList<Integer>> gameCharactersIDs;
	protected ArrayList<GameCharacter> gameCharacters;
	
	public CharacterToQuestionsMap(ArrayList<GameCharacter> gameCharacters) {
		this.gameCharacters = gameCharacters;
		gameCharactersIDs = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < gameCharacters.size(); i++) {
			gameCharactersIDs.add(new ArrayList<Integer>());
		}
	}

	/**
	 * Examines each string associated with a trivia and if the trivia
	 * matches to one of the characters aliases then add that to the mappings
	 * collection
	 * 
	 * TODO - Expensive to perform for each trivia on each startup. will have
	 * to find a way to cache mappings. Probably move all related pieces to DB
	 * 
	 * @param trivia
	 */
	public void addMappings(Question question) {
		for (int i = 0; i < gameCharacters.size(); i++) {
			ArrayList<String> searchEle = gameCharacters.get(i)
					.getSearchTerms();
			for (String s : question.getStrings()) {
				if (searchEle.contains(s)) {
					gameCharactersIDs.get(i).add(question.getId());
					break;
				}
			}
		}
	}

	/**
	 * Returns the ids of questions mapped to a particular character
	 * 
	 * @param i
	 * @return
	 */
	public ArrayList<Integer> get(int i) {
		ArrayList<Integer> ele = gameCharactersIDs.get(i);
		return ele;
	}

}
