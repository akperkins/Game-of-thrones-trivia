package com.GameOfThrones.Trivia.Characters;

import java.util.ArrayList;

import main.HashArray;

import com.GameOfThrones.Trivia.Question.Question;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class CharacterToQuestionsMap {
	HashArray<Character, Integer> map;
	ArrayList<Character> characters;

	public CharacterToQuestionsMap(ArrayList<Character> characters) {
		this.characters = characters;
	}

	/**
	 * TODO - Expensive to perform for each question on each startup. will have
	 * to find a way to cache mappings. Probably move all related pieces to DB
	 * 
	 * @param question
	 */
	public void addMappings(Question question) {
		String[] questions = question.getStrings();
		for (Character c : characters) {
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
	public HashArray<Character, Integer> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(HashArray<Character, Integer> map) {
		this.map = map;
	}

	public ArrayList<Integer> getCharacterQuestion(Character character) {
		return map.getElement(character);
	}

}
