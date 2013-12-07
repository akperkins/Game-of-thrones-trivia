package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * This activity does nothing except display the disclaimer message for 7
 * seconds before launching the activity.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class StartActivity extends Activity {

	/**
	 * onCreate() - set the layout
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	/**
	 * Sends a message to the current thread (via the handler) to wait 7 seconds
	 * before continuing to the MenuActivity
	 */
	public void onResume() {
		super.onResume();
		final Intent intent = new Intent(this, MenuActivity.class);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(intent);
			}
		}, 7000);
	}

	/**
	 * onStop - Destroys the current activity after the MenuActivity is
	 * launched
	 */
	public void onStop() {
		super.onStop();
		finish();
	}
}