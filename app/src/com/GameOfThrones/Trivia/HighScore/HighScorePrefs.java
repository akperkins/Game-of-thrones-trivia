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
	public static final String KEY_PREFIX = "com.our.package.KEY";

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
	private static final String KEY_DATE = "com.our.package.KEY_DATE";
	private static final String KEY_SCORE = "com.our.package.KEY_SCORE";

	/** Store or Update */
	public void setHighScore(HighScore score) {
		if (score == null)
			return; // don't bother

		int id = score.getId();
		editor.putString(getFieldKey(id, KEY_DATE), score.getDate());
		editor.putInt(getFieldKey(id, KEY_SCORE), score.getScore());

		editor.commit();
	}

	/** Retrieve */
	public HighScore getHighScore(int id) {
		String date = settings.getString(getFieldKey(id, KEY_DATE), ""); // default
																			// value
		int score = settings.getInt(getFieldKey(id, KEY_SCORE), 0); // default
																	// value
		return new HighScore(id, date, score);
	}

	/** Delete */
	public void deleteHighScore(HighScore highScore) {
		if (highScore == null)
			return; // don't bother

		int id = highScore.getId();
		editor.remove(getFieldKey(id, KEY_DATE));
		editor.remove(getFieldKey(id, KEY_SCORE));

		editor.commit();
	}
	
}
