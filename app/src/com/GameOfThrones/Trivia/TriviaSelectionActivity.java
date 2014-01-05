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
import com.GameOfThrones.Trivia.Data.GOTCharacters;
import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

public class TriviaSelectionActivity extends DynamicBackgroundActivity
		implements OnItemClickListener {
	ArrayList<String> charactersName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_selection);

		intitializeSessionData();

		initCharactersName();

		final ListView listview = (ListView) findViewById(R.id.charactersList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.clickable_row, charactersName);
		// Bind to our new adapter.

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	public void initCharactersName() {
		ArrayList<GameCharacter> gameCharacters = session.getCharacters();

		charactersName = new ArrayList<String>();
		charactersName.add("all");

		for (GameCharacter g : gameCharacters) {
			charactersName.add(g.getName());
		}
	}

	public void startGameForCharacter(int character) {
		Bundle bundle = new Bundle();
		bundle.putInt("gameCharacters", character);
		nextActivity(bundle, GameActivity.class);
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

	public void intitializeSessionData() {
		ArrayList<GameCharacter> characters = new ArrayList<GameCharacter>();
		for (String[] characterInfo : GOTCharacters.characters) {
			ArrayList<String> aliases = new ArrayList<String>();
			for (int i = 1; i < characterInfo.length; i++) {
				aliases.add(characterInfo[i]);
			}
			characters.add(new GameCharacter(characterInfo[0], aliases));
		}
		session.setCharacters(characters);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int row, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), "Clicked " + charactersName.get(row),
				Toast.LENGTH_LONG).show();
		// all was selected
		startGameForCharacter(row);
	}

}