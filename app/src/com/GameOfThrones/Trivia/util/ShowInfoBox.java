package com.GameOfThrones.Trivia.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.GameOfThrones.Trivia.R;

public class ShowInfoBox {

	private String EULA_PREFIX = "eula_";
	private Activity mActivity;

	public ShowInfoBox(Activity context) {
		mActivity = context;
	}

	private PackageInfo getPackageInfo() {
		PackageInfo pi = null;
		try {
			pi = mActivity.getPackageManager().getPackageInfo(
					mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	public void show(int[] stringIds, boolean acceptBooleans,
			boolean showEverytime) {
		PackageInfo versionInfo = getPackageInfo();

		// the eulaKey changes every time you increment the version number in
		// the AndroidManifest.xml
		final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		boolean hasBeenShown = prefs.getBoolean(eulaKey, false);

		boolean show = false;

		if (!showEverytime) {
			show = !hasBeenShown;
		} else {
			show = true;
		}

		if (show) {

			// Show the Eula
			String title = mActivity.getString(R.string.app_name) + " v"
					+ versionInfo.versionName;

			// Includes the updates as well so users know what changed.
			String message = "";

			for (int stringId : stringIds) {
				message += (mActivity.getString(stringId) + "\n\n");
			}

			if (acceptBooleans) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton(android.R.string.ok,
								new Dialog.OnClickListener() {

									public void onClick(
											DialogInterface dialogInterface,
											int i) {
										// Mark this version as read.
										SharedPreferences.Editor editor = prefs
												.edit();
										editor.putBoolean(eulaKey, true);
										editor.commit();
										dialogInterface.dismiss();
									}
								})
						.setNegativeButton(android.R.string.cancel,
								new Dialog.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// Close the activity as they have
										// declined
										// the EULA
										mActivity.finish();
									}

								});
				builder.create().show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton(android.R.string.ok,
								new Dialog.OnClickListener() {
									public void onClick(
											DialogInterface dialogInterface,
											int i) {
										// Mark this version as read.

									}
								});
				builder.create().show();
			}
		}
	}
}
