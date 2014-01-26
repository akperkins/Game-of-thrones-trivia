package com.GameOfThrones.Trivia.ui;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.R.id;
import com.GameOfThrones.Trivia.R.layout;
import com.GameOfThrones.Trivia.R.string;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * End of game activity.
 * 
 * @author andre
 * 
 */
public class ResultsActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	/** Stores the game statsView from the previous user game */
	int correct, total;

	/** References to the views in the UI layout */
	TextView tv, scoreText;
	Button submit, b1, backMain, rateApp;
	/** Score the user obtained while playing game in GameActivity */
	int score;

	/**
	 * onCreate(Bundle) - Sets the UI layout and initializes instance variables
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#onCreate
	 * (android.os.Bundle)
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
			score = extras.getInt("scoreView");
		}
		tv = (TextView) findViewById(R.id.endView1);
		tv.setVisibility(2);
		b1 = (Button) findViewById(R.id.endbutton1);
		b1.setOnClickListener(this);
		scoreText = (TextView) findViewById(R.id.scoreText);

		backMain = (Button) findViewById(R.id.backMain);
		backMain.setOnClickListener(this);

		rateApp = (Button) findViewById(R.id.rateApp);
		rateApp.setOnClickListener(this);
	}

	/**
	 * onReumse() - show the scoreView calculation results in textview
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
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
		scoreText.setText("Score: " + score);
	}

	/**
	 * onClick() - Performs the appropriate action for the button the user
	 * selects
	 * */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View arg0) {
		super.refreshBackground();
		switch (arg0.getId()) {
		case R.id.endbutton1:
			nextActivity(TriviaSelectionActivity.class);
			break;
		case R.id.backMain:
			nextActivity(MenuActivity.class);
			break;
		case R.id.rateApp:
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(this.getString(R.string.appUrl)));
			startActivity(i);
			break;
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#
	 * getBackgroundLayout()
	 */
	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.resultsActivity;
	}
}