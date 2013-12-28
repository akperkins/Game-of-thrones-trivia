package com.GameOfThrones.Trivia;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

public class TriviaSelectionActivity extends DynamicBackgroundActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_selection);

		final ListView listview = (ListView) findViewById(R.id.charactersList);

		String[] characters = new String[] { "Danerys", "John", "Jamie",
				"Eddard", "Tyrion" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, characters);
		// Bind to our new adapter.
		listview.setAdapter(adapter);
	}

	@Override
	protected int getBackgroundLayout() {
		return R.id.triviaSelection;
	}

}