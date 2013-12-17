package com.Spartacus.Trivia.Services;

import android.annotation.SuppressLint;
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
	boolean endThread;
	static Handler mHandler;

	public MusicService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public MusicService() {
		super(null);
	}

	public void stopThread() {
		endThread = true;
	}

	public class LocalBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
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

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public MediaPlayer getplayer() {
		return player;
	}

	public void onDestroy() {
		player.release();
		player = null;
		super.onDestroy();
	}

	public static Handler getHandler() {
		return mHandler;
	}

	@SuppressLint("HandlerLeak")
	@Override
	protected void onHandleIntent(Intent intent) {

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) { // play back
					if (player.isPlaying()) {
						player.pause();
					} else {
						player.start();
					}
					Log.d("worker thread", "meg received, what = 1");
				} else if (msg.what == 2) {
					endThread = true;
					Looper.myLooper().quit();
					Log.d("worker thread", "meg received, what = 2");
				}
			}
		};

		int resId = intent.getExtras().getInt("resId");
		this.player = MediaPlayer.create(this.getBaseContext(), resId);
		player.start();
		try {
			while (!endThread) {
				Log.d("worker thread", "Entered loop");
				Thread.sleep(250);
				Looper.loop();
				Log.d("worker thread", "exiting loop");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
