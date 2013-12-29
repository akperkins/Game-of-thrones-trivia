package com.GameOfThrones.Trivia;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;

import com.GameOfThrones.Trivia.Characters.GameCharacter;
import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.util.ShowInfoBox;

/**
 * This activity does nothing except display the disclaimer message for 7
 * seconds before launching the activity.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class StartActivity extends DynamicBackgroundActivity {
	final static public String[] characters = new String[] { "Daenerys" };

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
		intitializeSessionData();
	}

	/**
	 * onStop - Destroys the current activity after the MenuActivity is launched
	 */
	public void onStop() {
		super.onStop();
		finish();
	}

	public void intitializeSessionData() {
		// Create characters
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add("Mother of Dragons");
		aliases.add("Daenery");
		
		GameCharacter danerys = new GameCharacter(characters[0], aliases);
		
		ArrayList<GameCharacter> characters = new ArrayList<GameCharacter>();
		characters.add(danerys);
		
		session.setCharacters(characters);
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.startActivity;
	}
}