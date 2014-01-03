package com.GameOfThrones.Trivia;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.util.ShowInfoBox;

public class AboutActivity extends DynamicBackgroundActivity implements
		OnItemClickListener {
	Button eula, sources, disclaimer;
	ListView list;

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
				getBaseContext(), R.layout. clickable_row, str);
		// Bind to our new adapter.
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.aboutActivity;
	}

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
