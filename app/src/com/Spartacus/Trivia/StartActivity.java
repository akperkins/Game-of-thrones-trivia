package com.Spartacus.Trivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.Spartacus.Trivia.Media.MusicPlayer;

/**
 * This activity does nothing except display the disclaimer message for 7
 * seconds before launching the activity.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class StartActivity extends Activity {

	MusicPlayer music;

	/**
	 * onCreate() - set the layout
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		music = new MusicPlayer(this, R.raw.spartacus_theme_song, false);
	}

	/**
	 * Sends a message to the current thread (via the handler) to wait 7 seconds
	 * before continuing to the MenuActivity
	 */
	public void onResume() {
		super.onResume();
		final Intent intent = new Intent(this, MenuActivity.class);
		music.start();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(intent);
			}
		}, 5000);
	}

	public void onPause() {
		super.onPause();
		music.pause();
	}

	/**
	 * onStop - Destroys the current activity after the MenuActivity is launched
	 */
	public void onStop() {
		super.onStop();
		finish();
	}

	public void onDestroy() {
		super.onDestroy();
		music.kill();
		music = null;
	}
}