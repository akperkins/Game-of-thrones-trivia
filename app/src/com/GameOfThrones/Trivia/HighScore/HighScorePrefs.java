package com.GameOfThrones.Trivia.HighScore;

import com.GameOfThrones.Trivia.R;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Cool way to store multiple HighScores in SharedPreferences
 * 
 * @author andre
 * 
 */
public class HighScorePrefs {

	/** This application's preferences label */

	private static String PREFS_NAME;

	private String appName;

	/** This application's preferences */

	private static SharedPreferences settings;

	/** This application's settings editor */

	private static SharedPreferences.Editor editor;

	public String KEY_PREFIX = appName + ".KEY";
	public String KEY_SCORE_COUNT = appName + ".ScoreCount";

	/** generic field keys */
	private String KEY_DATE = appName + ".KEY_DATE";
	private String KEY_SCORE = appName + ".KEY_SCORE";

	/**
	 * Constructor
	 * 
	 * @param ctx
	 *            - Context in which the sharedPreferences are stored
	 */
	public HighScorePrefs(Context ctx) {
		this.appName = ctx.getString(R.string.app_name);
		if (settings == null) {
			PREFS_NAME = appName + ".HighScore";
			settings = ctx.getSharedPreferences(PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		editor = settings.edit();

		KEY_PREFIX = appName + ".KEY";
		KEY_SCORE_COUNT = appName + ".ScoreCount";
		
		/** generic field keys */
		KEY_DATE = appName + ".KEY_DATE";
		KEY_SCORE = appName + ".KEY_SCORE";
	}

	/** The prefix for flattened user keys */

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

	/**
	 * Saves a new highscore in the shared preferences
	 * @param date
	 * @param score
	 * @return
	 */
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

	/**
	 * Retrieves the HighScore that is mapped to this id
	 * 
	 * @param id
	 * @return
	 */
	public HighScore getHighScore(int id) {
		String date = settings.getString(getFieldKey(id, KEY_DATE), ""); // default
																			// value
		int score = settings.getInt(getFieldKey(id, KEY_SCORE), 0); // default
																	// value
		return new HighScore(id, date, score);
	}
	
	/**
	 * Returns the number of high scores currently saved
	 * @return
	 */
	public int getHighScoreCount() {
		return settings.getInt(KEY_SCORE_COUNT, 0);
	}
	
	/**
	 * Increment High score count.
	 * @param number
	 * @return
	 */
	public int addScoreCount(int number) {
		int scoreCount = getHighScoreCount();
		editor.putInt(KEY_SCORE_COUNT, scoreCount + number);

		editor.commit();
		return getHighScoreCount();
	}
}