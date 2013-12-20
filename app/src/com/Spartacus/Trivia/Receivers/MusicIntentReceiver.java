package com.Spartacus.Trivia.Receivers;

import android.content.Context;
import android.content.Intent;

import com.Spartacus.Trivia.Services.MusicService;

public class MusicIntentReceiver extends android.content.BroadcastReceiver {
	@Override
	public void onReceive(Context ctx, Intent intent) {
		if (intent.getAction().equals(
				android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
			// signal your service to stop playback
			// (via an Intent, for instance)
			//TODO implement this
		}
	}
}