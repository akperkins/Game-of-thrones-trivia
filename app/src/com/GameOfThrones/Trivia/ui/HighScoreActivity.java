package com.GameOfThrones.Trivia.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.R.id;
import com.GameOfThrones.Trivia.R.layout;
import com.GameOfThrones.Trivia.core.HighScore;
import com.GameOfThrones.Trivia.data.HighScorePrefs;
/**
 * Displays user high scores saved in sharedPreferences
 * 
 * @author andre
 *
 */
public class HighScoreActivity extends DynamicBackgroundActivity {

	/* (non-Javadoc)
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#onCreate(android.os.Bundle)
	 */
	/**
	 * Set up listview
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score);

		final ListView listview = (ListView) findViewById(R.id.listview);

		ArrayList<String> highScores = getHighScores();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.row, highScores);
		// Bind to our new adapter.
		listview.setAdapter(adapter);
	}
	
	/***
	 * Returns an arraylist of Strings that represent each highscore
	 * @return
	 */
	public ArrayList<String> getHighScores() {
		ArrayList<String> list = new ArrayList<String>();
		HighScorePrefs prefs = new HighScorePrefs(getBaseContext());
		for (int i = prefs.getHighScoreCount() -1; i > -1; i--) {
			HighScore temp = prefs.getHighScore(i);

			list.add(temp.toString());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#getBackgroundLayout()
	 */
	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.highScoreActivity;
	}

}
