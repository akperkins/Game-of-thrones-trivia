package com.GameOfThrones.Trivia.core;

public interface SimpleIterator<T> {

	public void beforeFirst();

	public void next();

	public T current();

	public boolean isDone();

}
