package com.GameOfThrones.Trivia;

import com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity;

import android.os.Bundle;

public class AboutActivity extends DynamicBackgroundActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.aboutActivity;
	}
	
	
}
