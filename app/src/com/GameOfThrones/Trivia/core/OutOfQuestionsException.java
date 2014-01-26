package com.GameOfThrones.Trivia.core;

/**
 * Exception thrown by the Questions Manager object to indicate that there aren't
 * anymore trivia questions remaining
 * @author andre
 *
 */
public class OutOfQuestionsException extends RuntimeException {

	/**
	 * Used to identify object during serialization processes
	 */
	private static final long serialVersionUID = -3468988262116632664L;
	
	/**	
	 * Default constructor that displays class custom error message
	 */
	public OutOfQuestionsException() {
		super("Out of trivia questions");
	}
	
	/**
	 * Constructor
	 * @param message - situation custom error message
	 */
	public OutOfQuestionsException(String message) {
		super(message);
	}

}