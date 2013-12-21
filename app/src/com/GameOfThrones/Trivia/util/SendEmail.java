package com.GameOfThrones.Trivia.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Only used to send an email intent. Class will be used in a command class
 * pattern.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class SendEmail {
	/**
	 * My developer email address.
	 */
	final static String MY_EMAIL = "akperkins1@gmail.com";

	/**
	 * send(Activity,String) - Helper function to send an email intent.
	 * 
	 * @param activity
	 *            Activity - instance of activiy that is sending intent -
	 * @param str
	 *            String - subject
	 */
	public static void send(Activity activity, String str) {
		/* Create the Intent */
		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { MY_EMAIL });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, str);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

		/* Send it off to the Activity-Chooser */
		// This should be replaced with code that will go through
		// Android's default app selection. This code forces the user
		// to choose the email activity that they would like to use
		activity.startActivity(Intent
				.createChooser(emailIntent, "Send mail..."));
	}
}
