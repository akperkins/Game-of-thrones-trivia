package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.Spartacus.Trivia.util.SendEmail;
import com.Spartacus.Trivia.util.SessionManager;

public class MenuActivity extends Activity implements OnClickListener {

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
			SessionManager.startSession(null);
			SessionManager.getCurrent().store("game", "quick");
			startActivity(new Intent(this, GameActivity.class));
			break;
		case R.id.mainEmail:
			SendEmail.send(this, "Concerns about Spartacus app");
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
}