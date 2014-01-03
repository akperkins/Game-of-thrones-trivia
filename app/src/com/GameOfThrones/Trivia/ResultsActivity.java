package com.GameOfThrones.Trivia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.util.SendEmail;

/**
 * End of game activity.
 * 
 * @author andre
 * 
 */
public class ResultsActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	/** Stores the game stats from the previous user game */
	int correct, total;

	/** References to the views in the UI layout */
	TextView tv, scoreText;
	Button submit, b1, backMain;
	int score;

	/**
	 * onCreate(Bundle) - Sets the UI layout and initializes instance variables
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		correct = 0;
		total = 0;
		score = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			correct = extras.getInt("correct");
			total = extras.getInt("total");
			score = extras.getInt("score");
		}
		tv = (TextView) findViewById(R.id.endView1);
		tv.setVisibility(2);
		b1 = (Button) findViewById(R.id.endbutton1);
		b1.setOnClickListener(this);
		scoreText = (TextView) findViewById(R.id.scoreText);

		backMain = (Button) findViewById(R.id.backMain);
		backMain.setOnClickListener(this);
	}

	/**
	 * onReumse() - show the score calculation results in textview
	 */
	public void onResume() {
		super.onResume();
		showResults();
	}

	/**
	 * setScore() - Shows a message indicating how successful you were
	 */
	public void showResults() {
		String str = "";
		if (score >= 1000) {
			str = "Azor Ahai reborn!!";
		} else if (score >= 600) {
			str = "On your way to the throne....";
		} else {
			str = "Worse than a whitewalker...";
		}
		tv.setText(str);
		scoreText.setText("Score : " + score);
	}

	/**
	 * onClick() - Performs the appropriate action for the button the user
	 * selects
	 * */
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.endbutton1:
			startActivity(new Intent(this, TriviaSelectionActivity.class));
			finish();
			break;
		case R.id.backMain:
			startActivity(new Intent(this, MenuActivity.class));
			finish();
			break;
		}
	}

	public void onStop() {
		super.onStop();
		super.refreshBackground();
	}

	/**
	 * onDestroy() - Cleans up instance variables.
	 */
	public void onDestroy() {
		super.onDestroy();
		tv = null;
		b1 = null;
		backMain = null;
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.resultsActivity;
	}
}