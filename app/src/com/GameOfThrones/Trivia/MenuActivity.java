package com.GameOfThrones.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.GameOfThrones.Trivia.util.SendEmail;
/**
 * Main Menu of application.
 * 
 * @author andre
 *
 */
public class MenuActivity extends Activity implements OnClickListener {

	Button qGame;
	Button email;

	final static int GAME_ACTIVITY_RESULT_CODE = 1;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.menu);
		qGame = (Button) findViewById(R.id.quickbutton);
		email = (Button) findViewById(R.id.mainEmail);
		qGame.setOnClickListener(this);
		email.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quickbutton:
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra("game", "quick");
			startActivityForResult(intent, GAME_ACTIVITY_RESULT_CODE);
			break;
		case R.id.mainEmail:
			SendEmail.send(this, "Concerns about Spartacus app");
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
}