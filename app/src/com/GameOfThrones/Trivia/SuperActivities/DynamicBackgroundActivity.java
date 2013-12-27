package com.GameOfThrones.Trivia.SuperActivities;

import java.util.Random;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.R.drawable;
import com.GameOfThrones.Trivia.util.Session;

public abstract class DynamicBackgroundActivity extends Activity {
	Session session = Session.getInstance();

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
		if (session.getBackground() == 0) {
			refreshBackground();
		}
		findViewById(backgroundId).setBackgroundResource(
				session.getBackground());
	}

	protected int refreshBackground() {
		int[] temp;
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			temp = portraitPics;
		} else {
			temp = landscapePics;
		}
		int resource = temp[new Random().nextInt(temp.length)];

		session.setBackground(resource);
		return resource;
	}

	protected abstract int getBackgroundLayout();
}
