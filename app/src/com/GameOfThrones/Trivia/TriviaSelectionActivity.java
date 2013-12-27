package com.GameOfThrones.Trivia;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TriviaSelectionActivity extends DynamicBackgroundActivity {
	ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_selection);

		listview = (ListView) findViewById(R.id.characters);

		String[] characters = new String[] { "Danerys", "John", "Jamie",
				"Catelyn", "Eddard", "Tyrion", "Brienne", "Cersei", "ygritte"};

		ListAdapter adapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, characters);
		// Bind to our new adapter.
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_selection, menu);
		return true;
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.triviaSelection;
	}

}
