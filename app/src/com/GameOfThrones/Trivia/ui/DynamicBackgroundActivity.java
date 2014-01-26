package com.GameOfThrones.Trivia.ui;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.R.drawable;
import com.GameOfThrones.Trivia.R.string;
import com.GameOfThrones.Trivia.core.Session;

/**
 * Used to change the drawable that is used as background image periodically
 * throughout the applications runtime
 * 
 * @author andre
 * 
 */
public abstract class DynamicBackgroundActivity extends Activity {
	/**
	 * Used to store which background drawables are being used
	 */
	protected Session session = Session.getInstance();

	protected int backgroundPort;
	/**
	 * drawable reference to background image when in landscape mode
	 */
	protected int backgroundLand;
	/**
	 * GameCharacters created created in application
	 */

	/**
	 * Stores drawable references for landscape mode
	 */
	// @TODO must obtain these at runtime
	protected static final int[] landscapePics = new int[] {
			R.drawable.dany_land, R.drawable.eddard_land,
			R.drawable.eddard_land1, R.drawable.jamie_land,
			R.drawable.jamie_land1, R.drawable.ygritte_land,
			R.drawable.y_john_land };

	/**
	 * Stores drawable references for landscape mode
	 */
	// @TODO must obtain these at runtime
	protected static final int[] portraitPics = new int[] {
			R.drawable.dany_port, R.drawable.eddard_port,
			R.drawable.jamie_port, R.drawable.tyrion_port };

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Sets the background drawable that is pre-determined
	 */
	public void onStart() {
		super.onStart();
		setBackground(getBackgroundLayout());
	}

	/**
	 * Sets the background for the current activity
	 * 
	 * @param backgroundId
	 *            - background drawable reference
	 */
	protected void setBackground(int backgroundId) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (backgroundPort == 0) {
				refreshBackground();
			}
			findViewById(backgroundId).setBackgroundResource(backgroundPort);
		} else {
			if (backgroundLand == 0) {
				refreshBackground();
			}
			findViewById(backgroundId).setBackgroundResource(backgroundLand);
		}
	}

	/**
	 * Randomly selects new background drawables for both orientations
	 */
	protected void refreshBackground() {
		int resourcePort = portraitPics[new Random()
				.nextInt(portraitPics.length)];
		int resourceLand = landscapePics[new Random()
				.nextInt(landscapePics.length)];

		backgroundPort = resourcePort;
		backgroundLand = resourceLand;

	}

	/**
	 * Subclasses will implement this to return the appropriate view background
	 * view object
	 * 
	 * @return
	 */
	protected abstract int getBackgroundLayout();

	/**
	 * Starts the next activity and finishes current one
	 * 
	 * @param cls
	 *            - the Class signature of Activity to start
	 */
	protected void nextActivity(Class<?> cls) {
		Intent aboutIntent = new Intent(this, cls);
		startActivity(aboutIntent);
		finish();
	}

	/**
	 * Starts the next activity, finishes current one and passes information in
	 * the form of a Bundle
	 * 
	 * @param bundle
	 *            - Bundle used to send info to next activity
	 * @param cls
	 *            - the Class signature of Activity to start
	 */
	protected void nextActivity(Bundle bundle, Class<?> cls) {
		Intent aboutIntent = new Intent(this, cls);
		aboutIntent.putExtras(bundle);
		startActivity(aboutIntent);
		finish();
	}

	final static String MY_EMAIL = "akperkins1@gmail.com";

	/**
	 * send - Helper function to send an email intent.
	 * 
	 * @param activity
	 *            Activity - instance of activiy that is sending intent -
	 * @param str
	 *            String - subject
	 */
	final protected void send(String str) {
		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);

		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { MY_EMAIL });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, str);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}

	final static private String NEW = "NEW";

	/**
	 * Obtain package info for Application
	 * 
	 * @return
	 */
	final protected PackageInfo getPackageInfo(Activity mActivity) {
		PackageInfo pi = null;
		try {
			pi = mActivity.getPackageManager().getPackageInfo(
					mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * Displays a dialog box. Displays strings stored in string resources.
	 * 
	 * @param stringIds
	 *            - String res ids to display in message
	 * @param acceptBooleans
	 *            - true if ok and cancel button, false if just ok displayed
	 * @param showEverytime
	 *            -showEveryTime if true box displays everytime. if false
	 *            displays if first time for new version of app
	 */
	final protected void show(int[] stringIds, boolean showEverytime) {
		// Includes the updates as well so users know what changed.
		String message = "";

		for (int stringId : stringIds) {
			message += (this.getString(stringId) + "\n\n");
		}
		show(message, showEverytime);
	}

	/**
	 * Displays a dialog box.
	 * 
	 * @param message
	 *            - String that is shown in dialog box
	 * @param acceptButtons
	 *            - true if ok and cancel button, false if just ok displayed
	 * @param showEveryTime
	 *            if true box displays everytime. if false displays if first
	 *            time for new version of app
	 * 
	 */
	// @TODO Refactor this - remove showEveryTime param
	final protected void show(String message, boolean showEveryTime) {
		PackageInfo versionInfo = getPackageInfo(this);

		/**
		 * the eulaKey changes every time you increment the version number in
		 * the AndroidManifest.xml
		 **/
		final String eulaKey = NEW + versionInfo.versionCode;
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean hasBeenShown = prefs.getBoolean(eulaKey, false);

		boolean show = false;

		if (!showEveryTime) {
			show = !hasBeenShown;
		} else {
			show = true;
		}

		if (show) {
			String title = this.getString(R.string.app_name) + " v"
					+ versionInfo.versionName;

			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new Dialog.OnClickListener() {
								public void onClick(
										DialogInterface dialogInterface, int i) {
								}
							});
			builder.create().show();
		}
	}
}
