package com.GameOfThrones.Trivia.Music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

public class MusicService extends Service implements
		AudioManager.OnAudioFocusChangeListener {
	MediaPlayer player;
	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	boolean initialized = false;

	public static final String ACTION_PLAY = "com.example.action.PLAY";
	public static final String STOP_PLAY = "com.example.action.STOP";

	int resid;

	/**
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public static final int PLAYER_TOGGLE_MUSIC = 1;
	public static final int PLAYER_KILL_THREAD = 2;
	public static final int PLAYER_START_PLAYER = 3;
	public static final int PLAYER_PAUSE_PLAYER = 4;
	public static final int PLAYER_STOP_PLAYER = 5;
	public static final int PLAYER_RELEASE_PLAYER = 6;

	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getAction().equals(ACTION_PLAY)) {
			initPlayer();
		} else if (intent.getAction().equals(STOP_PLAY)) {

		}
		return 0;
	}

	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	public void initPlayer() {
		if (!initialized) {
			player = MediaPlayer.create(this.getBaseContext(), resid);
			player.setWakeMode(getApplicationContext(),
					PowerManager.PARTIAL_WAKE_LOCK);
			initialized = true;
		}
	}

	/**
	 * Sets the raw song resource to use.
	 * 
	 * @param resid
	 *            - resource Id
	 */
	public void setSong(int resid) {
		this.resid = resid;
	}

	public void onAudioFocusChange(int focusChange) {
		switch (focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN:
			/** resume playback **/
			if (player == null) {
				initPlayer();
				playerStart();
			} else if (!player.isPlaying()) {
				playerStart();
			}
			player.setVolume(1.0f, 1.0f);
			break;

		case AudioManager.AUDIOFOCUS_LOSS:
			/**
			 * Lost focus for an unbounded amount of time: stop playback and
			 * release media player
			 **/
			if (player.isPlaying()) {
				player.stop();
			}
			cleanUpPlayer();
			break;

		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			/**
			 * Lost focus for a short time, but we have to stop playback. We
			 * don't release the media player because playback is likely to
			 * resume
			 **/
			if (player.isPlaying()) {
				playerPause();
			}
			break;

		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			/**
			 * Lost focus for a short time, but it's ok to keep playing at a
			 * reduced level
			 **/
			if (player.isPlaying())
				player.setVolume(0.1f, 0.1f);
			break;
		}
	}

	/**
	 * playerPause - Pause playback of player
	 */
	public void playerPause() {
		if (player != null && player.isPlaying()) {
			player.pause();
		}
	}

	/**
	 * playerStart - starts the music player only if we are able to gain Audio
	 * focus
	 */
	public void playerStart() {
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int result = audioManager.requestAudioFocus(this,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			player.start();
		}

	}

	/**
	 * playerIsPlaying - Determine if the music player is currently playing
	 * 
	 * @return boolean indicating is the player is playing
	 */
	public boolean playerIsPlaying() {
		return player.isPlaying();
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
	public void cleanUpPlayer() {
		if (player != null) {
			player.release();
			player = null;
		}
	}
}