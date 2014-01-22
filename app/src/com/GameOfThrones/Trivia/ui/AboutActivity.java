package com.GameOfThrones.Trivia.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.GameOfThrones.Trivia.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.R.id;
import com.GameOfThrones.Trivia.R.layout;
import com.GameOfThrones.Trivia.R.string;
import com.GameOfThrones.Trivia.util.ShowInfoBox;

/**
 * Activity that displays information about the application
 * 
 * @author andre
 * 
 */
public class AboutActivity extends DynamicBackgroundActivity implements
		OnItemClickListener {
	/**
	 * Buttons used to start dialog messages
	 */
	Button eula, sources, disclaimer;
	/**
	 * Displays buttons in a list
	 */
	ListView list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#onCreate
	 * (android.os.Bundle)
	 */
	/**
	 * Initialize instance variables
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		String[] str = new String[3];
		str[0] = "FOSS";
		str[1] = "sources";
		str[2] = "disclaimer";

		final ListView listview = (ListView) findViewById(R.id.listview);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.clickable_row, str);
		// Bind to our new adapter.
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#
	 * getBackgroundLayout()
	 */
	@Override
	protected int getBackgroundLayout() {
		return R.id.aboutActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	/**
	 * Displays a dialog with different message depending on the button clicked
	 * by user
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			new ShowInfoBox(this).show(new int[] { R.string.updates,
					R.string.license }, false, true);
			break;
		case 1:
			new ShowInfoBox(this).show(new int[] { R.string.sources }, false,
					true);
			break;
		case 2:
			new ShowInfoBox(this).show(new int[] { R.string.disclaimer_ },
					false, true);
			break;
		}
	}
}