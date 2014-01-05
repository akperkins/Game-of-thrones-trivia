package com.GameOfThrones.Trivia.HighScore;

public class HighScore {

	private int id; // used for object storage
	private String date;
	private int score;

	public HighScore(int id, String date, int score) {
		this.id = id;
		this.date = date;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getScore() {
		return score;
	}

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
