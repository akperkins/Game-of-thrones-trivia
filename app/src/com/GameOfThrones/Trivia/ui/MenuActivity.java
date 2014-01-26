package com.GameOfThrones.Trivia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.GameOfThrones.Trivia.R;

/**
 * Main Menu of application.
 * 
 * @author andre
 * 
 */
/**
 * @author andre
 * 
 */
public class MenuActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	/**
	 * Navigation to other sections of the App
	 */
	Button email, highScore, about, characterGame;

	/**
	 * Used to indicate that a a activity startedForResult returned successfully
	 */
	final static int GAME_ACTIVITY_RESULT_CODE = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#onCreate
	 * (android.os.Bundle)
	 */
	/**
	 * Initializes instance variables and displays disclaimer and updates if the
	 * situation is appropriate
	 */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.menu);
		email = (Button) findViewById(R.id.mainEmail);
		highScore = (Button) findViewById(R.id.highScore);
		about = (Button) findViewById(R.id.about);
		characterGame = (Button) findViewById(R.id.game);
		characterGame.setOnClickListener(this);
		highScore.setOnClickListener(this);
		email.setOnClickListener(this);
		about.setOnClickListener(this);

		show(new int[] { R.string.disclaimer_ }, 	false);

		show(new int[] { R.string.updates,
				R.string.license }, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.game:
			nextActivity(TriviaSelectionActivity.class);
			break;
		case R.id.mainEmail:
			send("Concerns about Spartacus app");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
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
		email = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#
	 * getBackgroundLayout()
	 */
	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.menuActivity;
	}

}