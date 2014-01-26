package com.GameOfThrones.Trivia.core;

import java.util.ArrayList;

public class CharacterTriviaGame extends TriviaGame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503849567206717914L;
	GameCharacter character;
	
	public CharacterTriviaGame(String[] array, int questionAmount, GameCharacter character) {
		super(array, questionAmount);
		this.character = character;
		
		
		CharacterToQuestionsMap map = session.getMap();
		for (Question q : qManager.getAllQuestions()) {
			map.addMappings(q);
		}
		ArrayList<Integer> ids = map.get(chosenCharacter - 1);
		qManager.keepOnly(ids);
	
	}

}
