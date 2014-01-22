package com.GameOfThrones.Trivia.Core;

/**
 * Score that a user obtain at the end of a game.
 * 
 * @author andre
 * 
 */
public class HighScore {
	/** unique identifier */
	protected int id;
	/** date the score was obtained */
	protected String date;
	/** numeric value of score */
	protected int score;

	/**
	 * Construtor
	 * 
	 * @param id
	 *            - unique identifier
	 * @param date
	 *            - date the score was obtained
	 * @param score
	 *            - int - value of score
	 */
	public HighScore(int id, String date, int score) {
		this.id = id;
		this.date = date;
		this.score = score;
	}

	/**
	 * id getter
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * id setter
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * date getter
	 * 
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * date setter
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * score getter
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * score setter
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entry=" + id + ", date=" + date + ", score=" + score;
	}

}
