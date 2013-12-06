package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Spartacus.Trivia.util.Session;
import com.Spartacus.Trivia.util.SessionManager;

public class EndGameActivity extends Activity implements OnClickListener {

	int correct;
	int total;
	double score;
	TextView tv;
	Button submit;
	Button b1;
	Button email;
	Button backMain;
	String initialStr;
	Context context;
	Toast toast;
	int duration;

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
		context = getApplicationContext();
		duration = Toast.LENGTH_SHORT;
	}

	public double calcScore() {
		return (correct * 1.0 / total) * 100;
	}

	public void onStart() {
		super.onStart();
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

	public void onStop() {
		super.onStop();
		finish();
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.endbutton1:
			Intent intent = new Intent(this, TriviaMenuActivity.class);
			startActivity(intent);
			break;
		case R.id.emailButton:

			/* Create the Intent */
			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND);

			/* Fill it with Data */
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "overnightApps@gmail.com" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Concerns about Spartacus app");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

			/* Send it off to the Activity-Chooser */
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;

		case R.id.backMain:
			Intent intent1 = new Intent(this, MainMenuActivity.class);
			startActivity(intent1);
			break;

		}

	}

}
