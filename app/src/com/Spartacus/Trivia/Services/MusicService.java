package com.Spartacus.Trivia.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

	private final IBinder mbinder = new LocalBinder();
	MediaPlayer player;
	int resId;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mbinder;
	}

	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	public void setSong(int resId) {
		this.resId = resId;
		player = MediaPlayer.create(this.getBaseContext(), this.resId);
	}

	public void playPlayer() {
		player.start();
	}

	public void pausePlayer() {
		player.pause();
	}

	public void releasePlayer() {
		player.release();
	}

	public void onCreate() {
		super.onCreate();
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public void onDestroy() {
		player.release();
		player = null;
		super.onDestroy();
	}

}
