package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;

/**
 * QuestionsManageStrategy - Subclasses inherit this class and determine the
 * algorithm to determine the next question to use. This uses the Strategy
 * design pattern.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public abstract class QuestionMangeStrategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4249523061864095095L;

	abstract Questions getNextQuestion(Questions[] questions)
			throws OutOfQuestionsException;

}
