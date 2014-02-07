package com.GameOfThrones.Trivia.ui.music;

import java.util.ArrayList;

import junit.framework.Assert;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * Bounded Service that wraps a Mediaplayer object. Used to play music while
 * adhering to the Android's system AudioManager
 * 
 * @author andre
 * 
 */
/**
 * @author andre
 * 
 */
public class MusicService extends Service implements
		AudioManager.OnAudioFocusChangeListener, MusicPublisher {
	private MediaPlayer player;
	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	private ArrayList<MusicObserver> observers = new ArrayList<MusicObserver>();

	private int resid = 0;

	/**
	 * MusicService inner class used to provide an interface to the service
	 * 
	 * @author andre
	 * 
	 */
	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	/**
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	/**
	 * Initialize mediaplayer object.
	 */
	private void initPlayer() {
		/** set song should have been called */
		Assert.assertFalse(resid == 0);

		player = MediaPlayer.create(this.getBaseContext(), resid);
		player.setWakeMode(getApplicationContext(),
				PowerManager.PARTIAL_WAKE_LOCK);
		player.setLooping(true);
	}

	/**
	 * @param Sets
	 *            the raw song resource to use.
	 */
	public void setSong(int resid) {
		this.resid = resid;
		initPlayer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.AudioManager.OnAudioFocusChangeListener#onAudioFocusChange
	 * (int)
	 */
	public void onAudioFocusChange(int focusChange) {
		switch (focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN:
			/** resume playback **/
			playMusic();

			player.setVolume(1.0f, 1.0f);
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			/**
			 * Lost focus for an unbounded amount of time: stop playback and
			 * release media player
			 **/
			pauseMusic();
			cleanUpPlayer();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			/**
			 * Lost focus for a short time, but we have to stop playback. We
			 * don't release the media player because playback is likely to
			 * resume
			 **/
			pauseMusic();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			/**
			 * Lost focus for a short time, but it's ok to keep playing at a
			 * reduced level
			 **/
			if (isMusicPlaying()) {
				player.setVolume(0.5f, 0.5f);
			}
			break;
		}
	}

	/**
	 * onDestroy - Cleans up player resource when service is destroyed
	 */
	public void onDestroy() {
		cleanUpPlayer();
		super.onDestroy();
	}

	/**
	 * cleanUpPlayer - Frees resources used by MusicPlayer object
	 */
	protected void cleanUpPlayer() {
		if (player != null) {
			player.release();
			player = null;
		}
	}

	public void attach(MusicObserver observer) {
		observers.add(observer);
	}

	public void detach(MusicObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (MusicObserver o : observers) {
			o.update();
		}
	}

	public boolean isMusicPlaying() {
		return player != null ? player.isPlaying() : false;
	}

	public void playMusic() {
		if (player == null) {
			initPlayer();
		}

		if (!isMusicPlaying()) {
			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			int result = audioManager.requestAudioFocus(this,
					AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

			if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				player.start();
			}
		}
		notifyObservers();
	}

	public void pauseMusic() {
		// Assert.assertTrue(player != null);

		if (isMusicPlaying()) {
			player.pause();
		}
		notifyObservers();
	}

	public void setCurrentPos(int msec) {
		Assert.assertNotNull(player);

		player.seekTo(msec);
	}

	public int getCurrentPos() {
		return player != null ? player.getCurrentPosition() : 0;
	}
}