package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.Spartacus.Trivia.util.Session;
import com.Spartacus.Trivia.util.SessionManager;

public class MainMenuActivity extends Activity implements OnClickListener {

	Button qGame;
	Button email;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.mainmenu);
		qGame = (Button) findViewById(R.id.quickbutton);
		email = (Button) findViewById(R.id.mainEmail);
		qGame.setOnClickListener(this);
		email.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quickbutton:
			Intent intent = new Intent(this, TriviaMenuActivity.class);
			SessionManager.startSession(null);
			SessionManager.getCurrent().store("game", "quick");
			startActivity(intent);
			break;
		case R.id.mainEmail:

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
			// This should be replaced with code that will go through
			// Android's default app selection. This code forces the user
			// to choose the email activity that they would like to use
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			break;
		}

	}
}
