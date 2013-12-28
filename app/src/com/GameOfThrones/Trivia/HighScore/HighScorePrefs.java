package com.GameOfThrones.Trivia.HighScore;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScorePrefs {

	/** This application's preferences label */

	private static final String PREFS_NAME = "com.GameOfThrones.Trivia.HighScore";

	/** This application's preferences */

	private static SharedPreferences settings;

	/** This application's settings editor */

	private static SharedPreferences.Editor editor;

	public HighScorePrefs(Context ctx) {
		if (settings == null) {
			settings = ctx.getSharedPreferences(PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		/*
		 * 
		 * Get a SharedPreferences editor instance.
		 * 
		 * SharedPreferences ensures that updates are atomic
		 * 
		 * and non-concurrent
		 */

		editor = settings.edit();

	}

	/** The prefix for flattened user keys */
	public static final String KEY_PREFIX = "com.GameOfThrones.package.KEY";
	public static final String KEY_SCORE_COUNT = "com.GameOfThrones.ScoreCount";

	/**
	 * Method to return a unique key for any field belonging to a given object
	 * 
	 * @param id
	 *            of the object
	 * @param fieldKey
	 *            of a particular field belonging to that object
	 * @return key String uniquely identifying the object's field
	 */
	private String getFieldKey(int id, String fieldKey) {
		return KEY_PREFIX + id + "_" + fieldKey;
	}

	/** generic field keys */
	private static final String KEY_DATE = "com.GameOfThrones.package.KEY_DATE";
	private static final String KEY_SCORE = "com.GameOfThrones.package.KEY_SCORE";

	/** Add a new high score */
	public HighScore addNewHighScore(String date, int score) {
		if (date == null)
			return null; // don't bother

		int id = getHighScoreCount();
		editor.putString(getFieldKey(id, KEY_DATE), date);
		editor.putInt(getFieldKey(id, KEY_SCORE), score);

		addScoreCount(1);
		editor.commit();

		return new HighScore(id, date, score);
	}

	/** Retrieve */
	public HighScore getHighScore(int id) {
		String date = settings.getString(getFieldKey(id, KEY_DATE), ""); // default
																			// value
		int score = settings.getInt(getFieldKey(id, KEY_SCORE), 0); // default
																	// value
		return new HighScore(id, date, score);
	}

	public int getHighScoreCount() {
		return settings.getInt(KEY_SCORE_COUNT, 0);
	}

	public int addScoreCount(int number) {
		int scoreCount = getHighScoreCount();
		editor.putInt(KEY_SCORE_COUNT, scoreCount + number);

		editor.commit();
		return getHighScoreCount();
	}

}