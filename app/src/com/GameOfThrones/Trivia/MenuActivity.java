package com.GameOfThrones.Trivia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.util.SendEmail;

/**
 * Main Menu of application.
 * 
 * @author andre
 * 
 */
public class MenuActivity extends DynamicBackgroundActivity implements
		OnClickListener {

	Button qGame;
	Button email;
	Button highScore;
	Button about;
	Button characterGame;

	final static int GAME_ACTIVITY_RESULT_CODE = 1;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.menu);
		qGame = (Button) findViewById(R.id.quickbutton);
		email = (Button) findViewById(R.id.mainEmail);
		highScore = (Button) findViewById(R.id.highScore);
		about = (Button) findViewById(R.id.about);
		characterGame = (Button) findViewById(R.id.characterGame);
		characterGame.setOnClickListener(this);
		highScore.setOnClickListener(this);
		qGame.setOnClickListener(this);
		email.setOnClickListener(this);
		about.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.characterGame:
			Intent triviaSelect = new Intent(this,
					TriviaSelectionActivity.class);
			startActivity(triviaSelect);
			break;
		case R.id.quickbutton:
			Intent intent = new Intent(this, GameActivity.class);
			startActivityForResult(intent, GAME_ACTIVITY_RESULT_CODE);
			break;
		case R.id.mainEmail:
			SendEmail.send(this, "Concerns about Spartacus app");
			break;
		case R.id.highScore:
			Intent scoreIntent = new Intent(this, HighScoreActivity.class);
			startActivity(scoreIntent);
			break;
		case R.id.about:
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case GAME_ACTIVITY_RESULT_CODE:
			if (resultCode == RESULT_OK) {
				finish();
			}
			break;
		}
	}

	/**
	 * onDestroy() - cleans up instance variables
	 */
	public void onDestoy() {
		super.onDestroy();
		qGame = null;
		email = null;
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.menuActivity;
	}
}