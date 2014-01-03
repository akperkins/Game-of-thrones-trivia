package com.GameOfThrones.Trivia.Characters;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Question.Question;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class CharacterToQuestionsMap {
	ArrayList<ArrayList<Integer>> gameCharactersIDs;
	ArrayList<GameCharacter> gameCharacters;

	public CharacterToQuestionsMap(ArrayList<GameCharacter> gameCharacters) {
		this.gameCharacters = gameCharacters;
		gameCharactersIDs = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < gameCharacters.size(); i++) {
			gameCharactersIDs.add(new ArrayList<Integer>());
		}
	}

	/**
	 * TODO - Expensive to perform for each question on each startup. will have
	 * to find a way to cache mappings. Probably move all related pieces to DB
	 * 
	 * @param question
	 */
	public void addMappings(Question question) {
		for (int i = 0; i < gameCharacters.size(); i++) {
			ArrayList<String> searchEle = gameCharacters.get(i)
					.getSearchTerms();
			for (String s : question.getStrings()) {
				if (GeneralAlgorithms.containsString(s, searchEle)) {
					gameCharactersIDs.get(i).add(question.getId());
					break;
				}
			}
		}
	}

	public ArrayList<Integer> get(int i) {
		ArrayList<Integer> ele = gameCharactersIDs.get(i);
		return ele;
	}

}
