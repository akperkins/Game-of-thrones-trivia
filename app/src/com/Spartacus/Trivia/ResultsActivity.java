package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.Spartacus.Trivia.util.SendEmail;
import com.Spartacus.Trivia.util.Session;
import com.Spartacus.Trivia.util.SessionManager;

public class ResultsActivity extends Activity implements OnClickListener {
	/** Stores the game stats from the previous user game */
	int correct, total;
	double score;

	/** References to the views in the UI layout */
	TextView tv;
	Button submit;
	Button b1;
	Button email;
	Button backMain;

	/**
	 * onCreate(Bundle) - Sets the UI layout and initializes instance variables
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);
		correct = 0;
		total = 0;
		Session session = SessionManager.getCurrent();
		correct = Integer.parseInt(session.get("correct"));
		total = Integer.parseInt(session.get("total"));
		score = calcScore();
		tv = (TextView) findViewById(R.id.endView1);
		tv.setVisibility(2);
		b1 = (Button) findViewById(R.id.endbutton1);
		b1.setOnClickListener(this);
		email = (Button) findViewById(R.id.emailButton);
		email.setOnClickListener(this);
		backMain = (Button) findViewById(R.id.backMain);
		backMain.setOnClickListener(this);
	}

	public double calcScore() {
		return (correct * 1.0 / total) * 100;
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
		if (score >= 90.0) {
			tv.setText("Congratulations. \nYou just may be the slayer of death after all.....\n"
					+ "You were " + correct + " out of " + total + ".");

		} else if (score >= 60) {
			tv.setText("Good attempt but you still produce cock and piss....\n"
					+ "You were " + correct + " out of " + total + ".");

		} else {
			tv.setText("Jupiters Cock!!\n You were " + correct + " out of "
					+ total + ".");

		}
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
}