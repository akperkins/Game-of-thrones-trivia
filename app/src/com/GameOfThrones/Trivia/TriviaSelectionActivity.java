package com.GameOfThrones.Trivia;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.GameOfThrones.Trivia.Characters.GameCharacter;
import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

public class TriviaSelectionActivity extends DynamicBackgroundActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_selection);

		ArrayList<GameCharacter> gameCharacters = session.getCharacters();

		String[] temp = StartActivity.characters;

		for (int i = 0; i < temp.length; i++) {
			temp[i] = gameCharacters.get(i).getName();
		}
		
		final String[] charactersName = temp;

		final ListView listview = (ListView) findViewById(R.id.charactersList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), android.R.layout.simple_list_item_1,
				charactersName);
		// Bind to our new adapter.

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Toast.makeText(getBaseContext(),
						"Clicked " + charactersName[position],
						Toast.LENGTH_LONG).show();
				startGameForCharacter(charactersName[position]);
			}
		});
	}

	public void startGameForCharacter(String character) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("gameCharacters", character);
		startActivityForResult(intent, 0);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				finish();
			}
			break;
		}
	}

	@Override
	protected int getBackgroundLayout() {
		return R.id.triviaSelection;
	}

}