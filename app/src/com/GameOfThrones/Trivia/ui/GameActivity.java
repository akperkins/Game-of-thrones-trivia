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

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.core.GameCharacter;
import com.GameOfThrones.Trivia.core.Question;
import com.GameOfThrones.Trivia.core.QuestionCollection;
import com.GameOfThrones.Trivia.core.TriviaGame;
import com.GameOfThrones.Trivia.data.HighScorePrefs;
import com.GameOfThrones.Trivia.data.StringsXMLFileData;
import com.GameOfThrones.Trivia.ui.music.MusicButton;
import com.GameOfThrones.Trivia.ui.music.MusicService;

/**
 * The trivia game loop executes here.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class GameActivity extends DynamicBackgroundActivity implements
		OnClickListener {
	private static final int TIME_BETWEEN_QUESTIONS = 3000;

	static final String HIGHSCORE_FILENAME = "high_score";

	/** number that represents the wrong answer dialog */
	static final int DIALOG_WRONG_ID = 0;

	/** number of trivia trivia per game */
	public static final int MAX_QUESTIONS = 10;

	/** References to layout views */
	TextView questionView, timerView, scoreView, statsView;
	Button b1View, b2View, b3View, b4View;

	MusicButton musicButton;

	/** Used to obtain the trivia trivia data */
	TriviaGame game;

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
	 * Keeps track if the musicPublisher is bound
	 */
	boolean mBound;

	/**
	 * The service connection class that is used to handle the binding and
	 * disconnecting of the musicPublisher
	 */
	private ServiceConnection myConnection = new MyServiceConnection();

	int musicStartPos;

	/**
	 * Initializes the instance data for the activity and sets the appropriate
	 * game state
	 */
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.game);
		initViewObjects();
		readyForTriviaSelection = false;
		musicStartPos = 0;
		Intent intent = new Intent(this, MusicService.class);
		this.bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
		if (state == null) {
			createNewState();
		} else {
			restoreState(state);
		}
	}

	/**
	 * Sets up activity state data for a new game
	 */
	public void createNewState() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String chosenCharacter = extras.getString("gameCharacter");
			QuestionCollection qList = StringsXMLFileData
					.getQuestionList(getResources());
			if (chosenCharacter == null) {
				game = new TriviaGame(qList, MAX_QUESTIONS, 20);
			} else {
				game = new TriviaGame(qList, MAX_QUESTIONS, 20,
						new GameCharacter(chosenCharacter, null));
			}
		}
		counter = new MyCount();
		playMusicOnLaunch = false;
		game.nextQuestion();
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
		musicButton = (MusicButton) findViewById(R.id.musicButton);
		musicButton.setOnClickListener(this);
		timerView = (TextView) findViewById(R.id.textTimer);
	}

	/**
	 * onStart() - Creates a new music player
	 */
	public void onStart() {
		super.onStart();
		displayQuestion();
	}

	/**
	 * Frees cancel and counter resources when activity is no longer visible
	 */
	public void onStop() {
		super.onStop();
		if (mBound) {
			this.unbindService(myConnection);
		}
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
				mBound ? musicService.isMusicPlaying() : false);
		savedInstanceState.putInt("music_pos",
				mBound ? musicService.getCurrentPos() : 0);

		savedInstanceState.putSerializable("game", game);
	}

	/**
	 * Restores state data for activity from a previously saved Game.
	 * 
	 * @param savedInstanceState
	 *            - bundle that contains game
	 */
	public void restoreState(Bundle savedInstanceState) {
		playMusicOnLaunch = savedInstanceState.getBoolean("music_isPlaying");
		counter = new MyCount(savedInstanceState.getInt("counter_left"));
		game = (TriviaGame) savedInstanceState.getSerializable("game");
		musicStartPos = savedInstanceState.getInt("music_pos");
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
		musicButton = null;
		timerView = null;
		game = null;
		counter = null;
	}

	/**
	 * Retrieves a trivia trivia data. If successful, populates the various
	 * button and Textfields appropriately. Starts a new timerView. If not
	 * successful, the game is ended.
	 * 
	 */
	private void displayQuestion() {
		Question q = game.getCurrentQuestion();
		questionView.setText(q.getTrivia());
		b1View.setText(q.getAnswers()[0]);
		b2View.setText(q.getAnswers()[1]);
		b3View.setText(q.getAnswers()[2]);
		b4View.setText(q.getAnswers()[3]);
		counter.start();
		updateStats();
		readyForTriviaSelection = true;
	}

	/**
	 * Wraps up the game. The current session is properly filled with the
	 * correct data the next activity is called and this activity is finished
	 */
	private void endGame() {
		if (mBound) {
			this.unbindService(myConnection);
		}
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("correct", game.getAmountCorrect());
		intent.putExtra("total", game.getNumberOfQuestions());
		intent.putExtra("scoreView", game.getGameScore());
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
		if (arg0.getId() == R.id.musicButton) {
			musicButton.buttonPressed();
		} else {
			if (!readyForTriviaSelection) {
				return;
			} else {
				readyForTriviaSelection = false;
			}
			boolean isCorrect = false;
			switch (arg0.getId()) {
			case R.id.button1:
				isCorrect = game.choiceSelected(1, counter.left);
				break;
			case R.id.button2:
				isCorrect = game.choiceSelected(2, counter.left);
				break;
			case R.id.button3:
				isCorrect = game.choiceSelected(3, counter.left);
				break;
			case R.id.button4:
				isCorrect = game.choiceSelected(4, counter.left);
				break;
			}
			isAnswerCorrect(isCorrect);
		}
	}

	private void isAnswerCorrect(boolean correct) {
		if (correct) {
			Toast.makeText(this.getBaseContext(), "Correct!",
					Toast.LENGTH_SHORT).show();
		} else {
			showDialog(DIALOG_WRONG_ID);

			myRemoveDialog();
		}
		game.nextQuestion();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (game.isGameOver()) {
					endGame();
				} else {
					displayQuestion();
				}
			}
		}, 1000);
		counter.cancel();
		if (counter.startQuestionTime < TriviaGame.QUESTION_TIME) {
			counter = new MyCount();
		}
	}

	/**
	 * Saves the scoreView the user obtained in the userPrefs
	 */
	private void saveHighScore() {
		HighScorePrefs prefs = new HighScorePrefs(this.getBaseContext());
		prefs.addNewHighScore(new Date().toString(), game.getGameScore());
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
							+ game.getCurrentQuestion().getAnswers()[game
									.getCorrectChoice() - 1]).setCancelable(
					true);
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
	private void myRemoveDialog() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				removeDialog(DIALOG_WRONG_ID);
			}
		}, TIME_BETWEEN_QUESTIONS);
	}

	/**
	 * getCorrectButton() - Returns a reference to the correct button choice.
	 * 
	 * @return Button - correct Button
	 */
	private Button getCorrectButton() {
		switch (game.getCorrectChoice()) {
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
	private void updateStats() {
		scoreView.setText(String.valueOf(game.getGameScore()));
		statsView.setText(game.getQuestionsAnswered() + " / "
				+ game.getNumberOfQuestions());
	}

	public class MyServiceConnection implements ServiceConnection {
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
			musicService.setSong(R.raw.game_of_thrones_theme_song);
			musicService.setCurrentPos(musicStartPos);
			if (playMusicOnLaunch) {
				musicService.playMusic();
			}
			mBound = true;
			musicButton.setMusicPublisher(musicService);
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
	}

	/**
	 * 
	 * Used to time the amount given to a user's trivia trivia.
	 * 
	 * @author Andre Perkins - akperkins1@gmail.com
	 * 
	 */
	protected class MyCount extends CountDownTimer {

		/** Sets one second in between every tick */
		private static final int TICK_TIME = 1000;

		/** Keeps track of the time left on timerView */
		private long left;

		private long startQuestionTime;

		/**
		 * Constructor that starts a timerView with a default value.
		 */
		public MyCount() {
			super(game.QUESTION_TIME, TICK_TIME);
			left = game.QUESTION_TIME;
			startQuestionTime = game.QUESTION_TIME;
		}

		/**
		 * Constructor that creates a timerView with a custom time remaining
		 * values.
		 * 
		 * @param timeRemaining
		 *            - custom time
		 */
		public MyCount(long timeRemaining) {
			super(timeRemaining, TICK_TIME);
			left = timeRemaining;
		}

		@Override
		/**
		 * The user gets the answer wrong if the time expires.
		 */
		public void onFinish() {
			isAnswerCorrect(false);
		}

		@Override
		/**
		 * Updates every second, the amount of seconds the user has left to make a decision.
		 */
		public void onTick(long millisUntilFinished) {
			timerView.setText("Left: " + millisUntilFinished / TICK_TIME);
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