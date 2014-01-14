package com.GameOfThrones.Trivia.SuperActivities;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.util.Session;

public abstract class DynamicBackgroundActivity extends Activity {
	protected Session session = Session.getInstance();

	private static final int[] landscapePics = new int[] {
			R.drawable.dany_land, R.drawable.eddard_land,
			R.drawable.eddard_land1, R.drawable.jamie_land,
			R.drawable.jamie_land1, R.drawable.ygritte_land,
			R.drawable.y_john_land };

	private static final int[] portraitPics = new int[] { R.drawable.dany_port,
			R.drawable.eddard_port, R.drawable.jamie_port,
			R.drawable.tyrion_port };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		setBackground(getBackgroundLayout());
	}

	protected void setBackground(int backgroundId) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (session.getBackgroundPort() == 0) {
				refreshBackground();
			}
			findViewById(backgroundId).setBackgroundResource(
					session.getBackgroundPort());
		} else {
			if (session.getBackgroundLand() == 0) {
				refreshBackground();
			}
			findViewById(backgroundId).setBackgroundResource(
					session.getBackgroundLand());
		}

	}

	protected void refreshBackground() {

		int resourcePort = portraitPics[new Random()
				.nextInt(portraitPics.length)];
		int resourceLand = landscapePics[new Random()
				.nextInt(landscapePics.length)];

		session.setBackgroundPort(resourcePort);
		session.setBackgroundLand(resourceLand);

	}

	protected abstract int getBackgroundLayout();

	public void nextActivity(Class<?> cls) {
		Intent aboutIntent = new Intent(this, cls);
		startActivity(aboutIntent);
		finish();
	}

	public void nextActivity(Bundle bundle, Class<?> cls) {
		Intent aboutIntent = new Intent(this, cls);
		aboutIntent.putExtras(bundle);
		startActivity(aboutIntent);
		finish();
	}
	
	/*protected void hideActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
	}*/
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.about, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/
}
