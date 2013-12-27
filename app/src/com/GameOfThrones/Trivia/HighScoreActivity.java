package com.GameOfThrones.Trivia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HighScoreActivity extends DynamicBackgroundActivity {
	static final String HIGHSCORE_FILENAME = "high_score";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score);
		
		final ListView listview = (ListView) findViewById(R.id.listview);

		String[] highScores = getHighScores();

		ListAdapter adapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, highScores);
		// Bind to our new adapter.
		listview.setAdapter(adapter);
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

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.highScoreActivity;
	}

}
