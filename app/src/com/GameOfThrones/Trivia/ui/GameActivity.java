package com.GameOfThrones.Trivia.ui;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.GameOfThrones.Trivia.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.Core.CharacterTriviaGame;
import com.GameOfThrones.Trivia.Core.GameCharacter;
import com.GameOfThrones.Trivia.Core.OutOfQuestionsException;
import com.GameOfThrones.Trivia.Core.Question;
import com.GameOfThrones.Trivia.Core.TriviaGame;
import com.GameOfThrones.Trivia.Data.HighScorePrefs;
import com.GameOfThrones.Trivia.util.media.MusicService;

/**
 * The trivia game loop executes here.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class GameActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	static final String HIGHSCORE_FILENAME = "high_score";

	/** number of trivia trivia per game */
	static final int MAX_QUESTIONS = 7;

	/** number that represents the wrong answer dialog */
	static final int DIALOG_WRONG_ID = 0;

	/** References to layout views */
	TextView questionView, timerView, scoreView, statsView;
	Button b1View, b2View, b3View, b4View, bMusic;

	/** Used to obtain the trivia trivia data */
	TriviaGame qManager;

	/** Plays music that interacts with Android AudioFocus Manager */
	MusicService musicService;
	/**
	 * Used as a timerView to limit the amount of time the user has to answer
	 * the trivia.
	 */
	MyCount counter;
	/**
	 * Music is not played on launch if it was off before activity was
	 * re-created
	 */
	boolean playMusicOnLaunch;
	/**
	 * Used to keep track if the buttons have been update before allowing the
	 * user to answer another trivia
	 */
	boolean readyForTriviaSelection;
	/**
	 * keeps track if the musicService has been initialized
	 */
	boolean initServiceSong;
	/**
	 * Keeps track if the musicService is bound
	 */
	boolean mBound;

	/**
	 * Initializes the instance data for the activity and sets the appropriate
	 * game state
	 */
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.game);
		initViewObjects();
		readyForTriviaSelection = false;
		initServiceSong = false;
		Intent intent = new Intent(this, MusicService.class);
		this.getApplication().bindService(intent, mConnection,
				Context.BIND_AUTO_CREATE);
		if (state != null) {
			restoreState(state);
		} else {
			createNewState();
		}
	}

	/**
	 * Sets up activity state data for a new game
	 */
	public void createNewState() {
			Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int chosenCharacter = extras.getInt("gameCharacters");
			if (chosenCharacter != 0) {
				qManager = new TriviaGame(getResources().getStringArray(
						R.array.questions), MAX_QUESTIONS);	
			}else{
				qManager = new CharacterTriviaGame(getResources().getStringArray(
						R.array.questions), MAX_QUESTIONS, new GameCharacter());
			}
		}
		counter = new MyCount();
		playMusicOnLaunch = false;
		try {
			qManager.nextQuestion();
		} catch (OutOfQuestionsException e) {
			endGame();
		}
	}

	/**
	 * Initializes layout buttons used
	 */
	private void initViewObjects() {
		b1View = (Button) findViewById(R.id.button1);
		b1View.setOnClickListener(this);
		b2View = (Button) findViewById(R.id.button2);
		b2View.setOnClickListener(this);
		b3View = (Button) findViewById(R.id.button3);
		b3View.setOnClickListener(this);
		b4View = (Button) findViewById(R.id.button4);
		b4View.setOnClickListener(this);
		questionView = (TextView) findViewById(R.id.question);
		scoreView = (TextView) findViewById(R.id.Score);
		statsView = (TextView) findViewById(R.id.Stats);
		bMusic = (Button) findViewById(R.id.musicButton);
		bMusic.setOnClickListener(this);
		timerView = (TextView) findViewById(R.id.textTimer);
	}

	/**
	 * The service connection class that is used to handle the binding and
	 * disconnecting of the musicService
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.ServiceConnection#onServiceConnected(android.content
		 * .ComponentName, android.os.IBinder)
		 */
		public void onServiceConnected(ComponentName className, IBinder service) {
			MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
			musicService = binder.getService();
			if (!initServiceSong) {
				musicService.setSong(R.raw.game_of_thrones_theme_song);
				musicService.initPlayer();
				initServiceSong = true;
			} else {
				if (playMusicOnLaunch) {
					bMusic.setText("Stop Music");
					musicService.playerStart();
				} else {
					bMusic.setText("Start Music");
				}
			}
			mBound = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.ServiceConnection#onServiceDisconnected(android.content
		 * .ComponentName)
		 */
		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
			musicService = null;
		}
	};

	/**
	 * onStart() - Creates a new music player
	 */
	public void onStart() {
		super.onStart();
		mapText(qManager.getCurrentQuestion());
	}

	/**
	 * Frees cancel and counter resources when activity is no longer visible
	 */
	public void onStop() {
		super.onStop();
		musicService.playerPause();
		counter.cancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt("counter_left", (int) counter.left);
		savedInstanceState.putBoolean("music_isPlaying",
				musicService.playerIsPlaying());
		savedInstanceState.putBoolean("initServiceSong", initServiceSong);
		savedInstanceState.putSerializable("qManager", qManager);
	}

	/**
	 * Restores state data for activity from a previously saved Game.
	 * 
	 * @param savedInstanceState
	 *            - bundle that contains game
	 */
	public void restoreState(Bundle savedInstanceState) {
		playMusicOnLaunch = savedInstanceState.getBoolean("music_isPlaying");
		initServiceSong = savedInstanceState.getBoolean("initServiceSong");
		counter = new MyCount(savedInstanceState.getInt("counter_left"));
		qManager = (TriviaGame) savedInstanceState.getSerializable("qManager");
	}

	/**
	 * Cleans up all instance variables
	 */
	public void onDestroy() {
		super.onDestroy();
		b1View = null;
		b2View = null;
		b3View = null;
		b4View = null;
		questionView = null;
		scoreView = null;
		bMusic = null;
		timerView = null;
		qManager = null;
		counter = null;
	}

	/**
	 * Retrieves a trivia trivia data. If successful, populates the various
	 * button and Textfields appropriately. Starts a new timerView. If not
	 * successful, the game is ended.
	 * 
	 */
	public void mapText(Question q) {
		questionView.setText(q.getTrivia());
		b1View.setText(q.getAnswer()[0]);
		b2View.setText(q.getAnswer()[1]);
		b3View.setText(q.getAnswer()[2]);
		b4View.setText(q.getAnswer()[3]);
		counter.start();
		updateStats();
		readyForTriviaSelection = true;
	}

	/**
	 * Wraps up the game. The current session is properly filled with the
	 * correct data the next activity is called and this activity is finished
	 */
	public void endGame() {
		this.getApplication().unbindService(mConnection);
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("correct", qManager.getAmountCorrect());
		intent.putExtra("total", qManager.getTotal_questions());
		intent.putExtra("scoreView", qManager.getGameScore());
		saveHighScore();
		setResult(RESULT_OK);
		startActivity(intent);
		finish();
	}

	/**
	 * Calls the proper handling code for the associated button click.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View arg0) {
		if (!readyForTriviaSelection) {
			return;
		} else {
			readyForTriviaSelection = false;
		}
		switch (arg0.getId()) {
		case R.id.button1:
			qManager.choiceSelected(1, counter.left);
			break;
		case R.id.button2:
			qManager.choiceSelected(2, counter.left);
			break;
		case R.id.button3:
			qManager.choiceSelected(3, counter.left);
			break;
		case R.id.button4:
			qManager.choiceSelected(4, counter.left);
			break;
		case R.id.musicButton:
			musicButtonPressed();
			break;
		}
	}

	public void isAnswerCorrect(boolean correct) {
		if (correct) {
			Toast.makeText(this.getBaseContext(), "Correct!",
					Toast.LENGTH_SHORT).show();
		} else {
			showDialog(DIALOG_WRONG_ID);

			myRemoveDialog();
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (qManager.isGameOver()) {
					endGame();
				} else {
					mapText(qManager.getCurrentQuestion());
				}
			}
		}, 1000);
		counter.cancel();
		if (counter.startQuestionTime < MyCount.QUESTION_TIME) {
			counter = new MyCount();
		}
	}

	/**
	 * Plays music if user presses play music and vice versa. The text for the
	 * button is set appropriately.
	 */
	public void musicButtonPressed() {
		if (musicService.playerIsPlaying()) {
			musicService.playerPause();
			bMusic.setText("Start Music");
		} else {
			musicService.playerStart();
			bMusic.setText("Stop Music");
		}
	}

	/**
	 * Saves the scoreView the user obtained in the userPrefs
	 */
	public void saveHighScore() {
		HighScorePrefs prefs = new HighScorePrefs(this.getBaseContext());
		prefs.addNewHighScore(new Date().toString(), qManager.getGameScore());
	}

	/**
	 * onCreateDialog() - creates the dialog fragment that will display the
	 * error message.
	 */
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_WRONG_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Wrong! The correct answer is: \n"
							+ qManager.getCurrentQuestion().getAnswer()[qManager
									.getCorrectChoice()]).setCancelable(true);
			AlertDialog alert = builder.create();
			dialog = alert;
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	/**
	 * myRemoveDialog() - In forms the current thread to remove the incorrect
	 * answer dialogFramgement
	 * 
	 * @TODO - look into saving and loading the DialogFragment instead
	 * */
	public void myRemoveDialog() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				removeDialog(DIALOG_WRONG_ID);
			}
		}, 3000);
	}

	/**
	 * getCorrectButton() - Returns a reference to the correct button choice.
	 * 
	 * @return Button - correct Button
	 */
	public Button getCorrectButton() {
		switch (qManager.getCorrectChoice()) {
		case 1:
			return b1View;
		case 2:
			return b2View;
		case 3:
			return b3View;
		default:
			return b4View;
		}
	}

	/**
	 * updateStatus() - updates the current game statsView textview
	 * 
	 * */
	public void updateStats() {
		scoreView.setText(String.valueOf(qManager.getGameScore()));
		statsView.setText(qManager.getQuestionsAnswered() + " / "
				+ qManager.getTotal_questions());
	}

	/**
	 * 
	 * Used to time the amount given to a user's trivia trivia.
	 * 
	 * @author Andre Perkins - akperkins1@gmail.com
	 * 
	 */
	protected class MyCount extends CountDownTimer {
		/** Gives the user 21 seconds to answer each trivia */
		final static long QUESTION_TIME = 21000;

		/** Sets one second in between every tick */
		final static long TICK_INTERVAL = 1000;

		/** Keeps track of the time left on timerView */
		private long left;

		private long startQuestionTime;

		/**
		 * Constructor that starts a timerView with a default value.
		 */
		public MyCount() {
			super(QUESTION_TIME, TICK_INTERVAL);
			left = QUESTION_TIME;
			startQuestionTime = QUESTION_TIME;
		}

		/**
		 * Constructor that creates a timerView with a custom time remaining
		 * values.
		 * 
		 * @param timeRemaining
		 *            - custom time
		 */
		public MyCount(long timeRemaining) {
			super(timeRemaining, TICK_INTERVAL);
			left = timeRemaining;
		}

		@Override
		/**
		 * The user gets the answer wrong if the time expires.
		 */
		public void onFinish() {
			qManager.choiceSelected((qManager.getCorrectChoice() + 1) % 4, 0);
		}

		@Override
		/**
		 * Updates every second, the amount of seconds the user has left to make a decision.
		 */
		public void onTick(long millisUntilFinished) {
			timerView.setText("Left: " + millisUntilFinished / 1000);
			left = millisUntilFinished;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.GameOfThrones.Trivia.SuperActivities.DynamicBackgroundActivity#
	 * getBackgroundLayout()
	 */
	@Override
	protected int getBackgroundLayout() {
		// TODO Auto-generated method stub
		return R.id.gameActivity;
	}
}