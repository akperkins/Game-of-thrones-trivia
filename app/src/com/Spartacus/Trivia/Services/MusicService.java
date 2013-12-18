package com.Spartacus.Trivia.Services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MusicService extends IntentService {
	MediaPlayer player;
	static MusicHandler mHandler;

	public static final int PLAYER_TOGGLE_MUSIC = 1;
	public static final int PLAYER_KILL_THREAD = 2;
	public static final int PLAYER_START_PLAYER = 3;
	public static final int PLAYER_PAUSE_PLAYER = 4;
	public static final int PLAYER_STOP_PLAYER = 5;
	public static final int PLAYER_RELEASE_PLAYER = 6;

	public MusicService(String name) {
		super(name);
	}

	public MusicService() {
		super(null);
	}

	public void onDestroy() {
		// todo look into releasing when the player is paused
		player.release();
		player = null;
		super.onDestroy();
	}

	public static Handler getHandler() {
		return mHandler;
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		mHandler = new MusicHandler();

		int resId = intent.getExtras().getInt("resId");
		this.player = MediaPlayer.create(this.getBaseContext(), resId);
		player.start();
		try {
			Log.i("worker thread", "Entered loop");
			Thread.sleep(250);
			Looper.loop();
			Log.i("worker thread", "exiting loop");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mHandler = null;
		}
	}

	public class MusicHandler extends Handler {
		public void handleMessage(Message msg) {
			if (msg.what == PLAYER_TOGGLE_MUSIC) { // play back
				if (player.isPlaying()) {
					player.pause();
				} else {
					player.start();
				}
				Log.i("worker thread", "msg received in music worker, what = 1");
			} else if (msg.what == PLAYER_KILL_THREAD) {
				Looper.myLooper().quit();
				Log.i("worker thread", "msg received in music worker, what = 2");
			} else if (msg.what == PLAYER_RELEASE_PLAYER) {
				player.release();
			} else if (msg.what == PLAYER_PAUSE_PLAYER) {
				player.pause();
			} else if (msg.what == PLAYER_START_PLAYER) {
				player.start();
			} else if (msg.what == PLAYER_STOP_PLAYER) {
				player.stop();
			}
		}
	}

	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

}