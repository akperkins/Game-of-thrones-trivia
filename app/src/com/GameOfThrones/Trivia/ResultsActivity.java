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
	TextView tv;
	Button submit;
	Button b1;
	Button email;
	Button backMain;
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
		email = (Button) findViewById(R.id.emailButton);
		email.setOnClickListener(this);
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
		if (score >= 1500) {
			str = "Congratulations. \nYou just may be the slayer of death after all.....\n"
					+ "You were " + correct + " out of " + total + ".";
		} else if (score >= 600) {
			str = "Good attempt but you still produce cock and piss....\n"
					+ "You were " + correct + " out of " + total + ".";
		} else {
			str = "Jupiters Cock!!\n You were " + correct + " out of " + total
					+ ".";
		}
		str += "\nScore : " + score;
		tv.setText(str);
	}

	/**
	 * onClick() - Performs the appropriate action for the button the user
	 * selects
	 * */
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.endbutton1:
			startActivity(new Intent(this, GameActivity.class));
			finish();
			break;
		case R.id.emailButton:
			SendEmail.send(this, "Concerns about Spartacus app");
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
		email = null;
		backMain = null;
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.resultsActivity;
	}
}