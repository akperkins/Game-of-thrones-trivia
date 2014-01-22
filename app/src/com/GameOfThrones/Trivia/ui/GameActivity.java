package com.GameOfThrones.Trivia.ui;

import java.util.ArrayList;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.GameOfThrones.Trivia.DynamicBackgroundActivity;
import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.Core.CharacterToQuestionsMap;
import com.GameOfThrones.Trivia.Core.OutOfQuestionsException;
import com.GameOfThrones.Trivia.Core.Question;
import com.GameOfThrones.Trivia.Core.QuestionsCollectionManager;
import com.GameOfThrones.Trivia.Data.HighScorePrefs;
import com.GameOfThrones.Trivia.Music.MusicService;
import com.GameOfThrones.Trivia.R.array;
import com.GameOfThrones.Trivia.R.id;
import com.GameOfThrones.Trivia.R.layout;
import com.GameOfThrones.Trivia.R.raw;

/**
 * The trivia game loop executes here.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class GameActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	/** number of trivia trivia per game */
	static final int MAX_QUESTIONS = 7;

	int total_questions;

	static final String HIGHSCORE_FILENAME = "high_score";

	/** number that represents the wrong answer dialog */
	static final int DIALOG_WRONG_ID = 0;

	/** References to layout views */
	TextView question, timer, score, stats;
	Button b1, b2, b3, b4, bMusic;

	/** Used to obtain the trivia trivia data */
	QuestionsCollectionManager qManager;

	/** Keep track of current game stats */
	int amountCorrect, questionsAnswered, correctChoice;

	/**
	 * Stores the correct answer for current trivia trivia. This is displayed to
	 * the user in the dialog if the wrong answer is selected
	 */
	String correctAnswer;

	/** Plays music that interacts with Android AudioFocus Manager */
	MusicService musicService;
	/**
	 * Used as a timer to limit the amount of time the user has to answer the
	 * trivia.
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
	 * Score the user accumulates
	 */
	int gameScore;

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
		total_questions = MAX_QUESTIONS;
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
		amountCorrect = 0;
		questionsAnswered = 0;
		correctChoice = 0;
		ArrayList<String[]> temp = new ArrayList<String[]>();
		qManager = new QuestionsCollectionManager(getResources()
				.getStringArray(R.array.questions));

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int chosenCharacter = extras.getInt("gameCharacters");

			if (chosenCharacter != 0) {

				CharacterToQuestionsMap map = session.getMap();
				for (Question q : qManager.getAllQuestions()) {
					map.addMappings(q);
				}
				ArrayList<Integer> ids = map.get(chosenCharacter - 1);

				qManager.keepOnly(ids);
				if (qManager.getAllQuestions().size() < MAX_QUESTIONS) {
					total_questions = qManager.getAllQuestions().size();
				}
			}
		}
		counter = new MyCount();
		gameScore = 0;
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
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(this);
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(this);
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(this);
		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(this);
		question = (TextView) findViewById(R.id.question);
		score = (TextView) findViewById(R.id.Score);
		stats = (TextView) findViewById(R.id.Stats);
		bMusic = (Button) findViewById(R.id.musicButton);
		bMusic.setOnClickListener(this);
		timer = (TextView) findViewById(R.id.textTimer);
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
		mapText(qManager.getCurrentQuestionStrings());
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
		savedInstanceState.putInt("amountCorrect", amountCorrect);
		savedInstanceState.putInt("questionsAnswered", questionsAnswered);
		savedInstanceState.putInt("correctChoice", correctChoice);
		savedInstanceState.putString("correctAnswer", correctAnswer);
		savedInstanceState.putInt("counter_left", (int) counter.left);
		savedInstanceState.putInt("gameScore", gameScore);
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
		amountCorrect = savedInstanceState.getInt("amountCorrect");
		questionsAnswered = savedInstanceState.getInt("questionsAnswered");
		correctChoice = savedInstanceState.getInt("correctChoice");
		correctAnswer = savedInstanceState.getString("correctAnswer");
		playMusicOnLaunch = savedInstanceState.getBoolean("music_isPlaying");
		initServiceSong = savedInstanceState.getBoolean("initServiceSong");
		gameScore = savedInstanceState.getInt("gameScore");
		counter = new MyCount(savedInstanceState.getInt("counter_left"));
		qManager = (QuestionsCollectionManager) savedInstanceState
				.getSerializable("qManager");
	}

	/**
	 * Cleans up all instance variables
	 */
	public void onDestroy() {
		super.onDestroy();
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
		question = null;
		score = null;
		bMusic = null;
		timer = null;
		qManager = null;
		counter = null;
	}

	/**
	 * Retrieves a trivia trivia data. If successful, populates the various
	 * button and Textfields appropriately. Starts a new timer. If not
	 * successful, the game is ended.
	 * 
	 */
	public void mapText(String[] array) {
		question.setText(array[0]);
		b1.setText(array[1]);
		b2.setText(array[2]);
		b3.setText(array[3]);
		b4.setText(array[4]);
		correctChoice = Integer.parseInt(array[5]);
		correctAnswer = array[correctChoice];
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
		intent.putExtra("correct", amountCorrect);
		intent.putExtra("total", total_questions);
		intent.putExtra("score", gameScore);
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
		switch (arg0.getId()) {
		case R.id.button1:
			choiceSelected(1);
			break;
		case R.id.button2:
			choiceSelected(2);
			break;
		case R.id.button3:
			choiceSelected(3);
			break;
		case R.id.button4:
			choiceSelected(4);
			break;
		case R.id.musicButton:
			musicButtonPressed();
			break;
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
	 * If the choice was correct, a correct toast is shown. If an incorrect
	 * button was selected, a DialogFragment is shown displaying the correct
	 * choice. Will try to determine if the user has answered the total amount
	 * of trivia trivia, if they have endGame() is called, otherwise mapText()
	 * is called and the game goes on to the next trivia
	 * 
	 * @param button
	 *            - int representing the user's trivia choice
	 */
	public void choiceSelected(int button) {
		if (!readyForTriviaSelection) {
			return;
		} else {
			readyForTriviaSelection = false;
		}
		if (correctChoice == button) {
			amountCorrect++;
			gameScore += (counter.left / 100);
			Toast.makeText(this.getBaseContext(), "Correct!",
					Toast.LENGTH_SHORT).show();
		} else {
			showDialog(DIALOG_WRONG_ID);

			myRemoveDialog();
		}
		questionsAnswered++;
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (questionsAnswered == total_questions) {
					endGame();
				} else {
					try {
						qManager.nextQuestion();
					} catch (OutOfQuestionsException e) {
						Log.e(this.toString(), "Ran out of trivia questions", e);
					}
					String[] nextQuestion = qManager
							.getCurrentQuestionStrings();
					mapText(nextQuestion);
				}
			}
		}, 1000);
		counter.cancel();
		if (counter.startQuestionTime < MyCount.QUESTION_TIME) {
			counter = new MyCount();
		}
	}

	/**
	 * Saves the score the user obtained in the userPrefs
	 */
	public void saveHighScore() {
		HighScorePrefs prefs = new HighScorePrefs(this.getBaseContext());
		prefs.addNewHighScore(new Date().toString(), gameScore);
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
					"Wrong! The correct answer is: \n" + correctAnswer)
					.setCancelable(true);

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
		switch (correctChoice) {
		case 1:
			return b1;
		case 2:
			return b2;
		case 3:
			return b3;
		default:
			return b4;
		}
	}

	/**
	 * updateStatus() - updates the current game stats textview
	 * 
	 * */
	public void updateStats() {
		score.setText(String.valueOf(gameScore));
		stats.setText(questionsAnswered + " / " + total_questions);
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

		/** Keeps track of the time left on timer */
		private long left;

		private long startQuestionTime;

		/**
		 * Constructor that starts a timer with a default value.
		 */
		public MyCount() {
			super(QUESTION_TIME, TICK_INTERVAL);
			left = QUESTION_TIME;
			startQuestionTime = QUESTION_TIME;
		}

		/**
		 * Constructor that creates a timer with a custom time remaining values.
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
			choiceSelected((correctChoice + 1) % 4);
		}

		@Override
		/**
		 * Updates every second, the amount of seconds the user has left to make a decision.
		 */
		public void onTick(long millisUntilFinished) {
			timer.setText("Left: " + millisUntilFinished / 1000);
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