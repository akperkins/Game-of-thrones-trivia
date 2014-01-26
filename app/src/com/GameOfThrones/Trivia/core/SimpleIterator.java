package com.GameOfThrones.Trivia.core;

/**
 * Simple iterator interface
 * 
 * @author andre
 * 
 * @param <T>
 */
public interface SimpleIterator<T> {
	/**
	 * Moves object position to before the first element.
	 */
	public void beforeFirst();

	/**
	 * Moves to the next object
	 */
	public void next();

	/**
	 * Obtains the current object
	 * 
	 * @return current object
	 */
	public T current();

	/**
	 * Determines if you have reached the end of all the objects
	 * 
	 * @return
	 */
	public boolean isDone();

}
