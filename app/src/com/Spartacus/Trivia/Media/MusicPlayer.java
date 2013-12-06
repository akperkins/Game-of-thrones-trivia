package com.Spartacus.Trivia.Media;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

	MediaPlayer player;

	public MusicPlayer(Activity activity, int resId) {
		newPlayerSetup(activity, resId);
	}

	public void start() {
		player.start();
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public void pause() {
		player.pause();
	}

	public void kill() {
		if (player != null) {
			player.release();
			player = null;
		}
	}

	private void newPlayerSetup(Activity activity, int resId) {
		player = MediaPlayer.create(activity.getBaseContext(), resId);
		player.setLooping(true);

	}

	public void changeSong(Activity activity, int resId) {
		newPlayerSetup(activity, resId);
		start();
	}

}
