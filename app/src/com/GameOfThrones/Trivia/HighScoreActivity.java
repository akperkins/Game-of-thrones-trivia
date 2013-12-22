package com.GameOfThrones.Trivia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class HighScoreActivity extends ListActivity {
	static final String HIGHSCORE_FILENAME = "high_score";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score);

		String[] highScores = getHighScores();

		ListAdapter adapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, highScores);
		// Bind to our new adapter.
		setListAdapter(adapter);
	}

	public String[] getHighScores() {
		FileInputStream fis = null;
		ArrayList<String> highScores = new ArrayList<String>();

		try {
			fis = openFileInput(HIGHSCORE_FILENAME);

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = "";

			while ((line = br.readLine()) != null) {
				highScores.add(line);
			}

			br.close();

		} catch (FileNotFoundException e) {
			return new String[0];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] temp = new String[highScores.size()];

		for (int i = 0; i < highScores.size(); i++) {
			temp[i] = highScores.get(i);
		}

		return temp;
	}

}
