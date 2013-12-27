package com.GameOfThrones.Trivia;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;

/**
 * This activity does nothing except display the disclaimer message for 7
 * seconds before launching the activity.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class StartActivity extends DynamicBackgroundActivity {

	/**
	 * onCreate() - set the layout
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Debug.waitForDebugger();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
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
		}, 4000);
		
	}

	public void onPause() {
		super.onPause();
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
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.startActivity;
	}
}