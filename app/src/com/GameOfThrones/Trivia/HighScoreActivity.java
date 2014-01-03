package com.GameOfThrones.Trivia;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.GameOfThrones.Trivia.HighScore.HighScore;
import com.GameOfThrones.Trivia.HighScore.HighScorePrefs;
import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

public class HighScoreActivity extends DynamicBackgroundActivity {

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

	public ArrayList<String> getHighScores() {
		ArrayList<String> list = new ArrayList<String>();
		HighScorePrefs prefs = new HighScorePrefs(getBaseContext());
		for (int i = 0; i < prefs.getHighScoreCount(); i++) {
			HighScore temp = prefs.getHighScore(i);

			list.add(temp.toString());
		}
		return list;
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.highScoreActivity;
	}

}
