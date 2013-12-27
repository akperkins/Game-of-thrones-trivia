package com.GameOfThrones.Trivia.Exceptions;

public class OutOfQuestionsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468988262116632664L;

	public OutOfQuestionsException() {
		super("Out of trivia questions");
	}

	public OutOfQuestionsException(String message) {
		super(message);
	}

}
