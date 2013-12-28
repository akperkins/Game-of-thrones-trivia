package com.GameOfThrones.Trivia.Characters;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Question.Question;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;
import com.GameOfThrones.Trivia.util.HashArray;

public class CharacterToQuestionsMap {
	HashArray<GameCharacter, Integer> map;
	ArrayList<GameCharacter> gameCharacters;

	public CharacterToQuestionsMap(ArrayList<GameCharacter> gameCharacters) {
		this.gameCharacters = gameCharacters;
		map = new HashArray<GameCharacter, Integer>();
	}

	/**
	 * TODO - Expensive to perform for each question on each startup. will have
	 * to find a way to cache mappings. Probably move all related pieces to DB
	 * 
	 * @param question
	 */
	public void addMappings(Question question) {
		String[] questions = question.getStrings();
		for (GameCharacter c : gameCharacters) {
			ArrayList<String> searchEle = c.getSearchTerms();
			for (String s : questions) {
				if (GeneralAlgorithms.containsString(s, searchEle)) {
					map.add(c, question.getId());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CharacterToQuestionsMap [map=" + map + "]";
	}

	/**
	 * @return the map
	 */
	public HashArray<GameCharacter, Integer> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(HashArray<GameCharacter, Integer> map) {
		this.map = map;
	}

	public ArrayList<Integer> get(GameCharacter gameCharacter) {
		return map.getElement(gameCharacter);
	}

}
