package com.Spartacus.Trivia.Media;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * Plays a music file stored in app's raw resources
 * 
 * @author andre
 * 
 */
public class MusicPlayer {

	MediaPlayer player;

	public MusicPlayer(Activity activity, int resId, boolean looping) {
		newPlayerSetup(activity, resId, looping);
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

	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	public void setCurrentPosition(int pos) {
		player.seekTo(pos);
	}

	public void kill() {
		if (player != null) {
			player.release();
			player = null;
		}
	}

	private void newPlayerSetup(Activity activity, int resId, boolean looping) {
		player = MediaPlayer.create(activity.getBaseContext(), resId);
		player.setLooping(looping);

	}

	public void changeSong(Activity activity, int resId, boolean looping) {
		player.stop();
		newPlayerSetup(activity, resId, looping);
		start();
	}
}