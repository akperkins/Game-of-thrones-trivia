package com.GameOfThrones.Trivia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

public class TriviaSelectionActivity extends DynamicBackgroundActivity {
	final String[] characters = new String[] { "Danerys", "John", "Jamie",
			"Eddard", "Tyrion" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_selection);

		final ListView listview = (ListView) findViewById(R.id.charactersList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), android.R.layout.simple_list_item_1,
				characters);
		// Bind to our new adapter.
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Toast.makeText(getBaseContext(),
						"Clicked " + characters[position], Toast.LENGTH_LONG)
						.show();
				startGameForCharacter(characters[position]);
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