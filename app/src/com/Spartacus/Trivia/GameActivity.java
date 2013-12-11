package com.Spartacus.Trivia;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;
import com.Spartacus.Trivia.Media.MusicPlayer;
import com.Spartacus.Trivia.Models.QuestionsManager;

/**
 * The trivia game loop executes here.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class GameActivity extends Activity implements OnClickListener {
	/** number of trivia question per game */
	final int TOTAL_QUESTIONS = 13;

	/** number that represents the wrong answer dialog */
	final int DIALOG_WRONG_ID = 0;

	/** References to layout views */
	TextView title, timer, stats;
	Button b1, b2, b3, b4, bMusic;

	/** Used to obtain the question trivia data */
	QuestionsManager qManager;

	/** Used to play the music during trivia game */
	MusicPlayer music;

	/** Keep track of current game stats */
	int amountCorrect, questionsAnswered, correctChoice;

	/**
	 * Stores the correct answer for current trivia question. This is displayed
	 * to the user in the dialog if the wrong answer is selected
	 */
	String correctAnswer;

	/**
	 * Used as a timer to limit the amount of time the user has to answer the
	 * question.
	 */
	MyCount counter;

	boolean playMusicOnLaunch;

	boolean readyForTriviaSelection;

	/**
	 * onCreate() - initializes the instance data for the activity
	 */
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.game);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(this);
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(this);
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(this);
		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(this);
		title = (TextView) findViewById(R.id.textView1);
		title.setVisibility(2);
		stats = (TextView) findViewById(R.id.textView2);
		stats.setVisibility(2);
		bMusic = (Button) findViewById(R.id.musicButton);
		bMusic.setOnClickListener(this);
		timer = (TextView) findViewById(R.id.textTimer);
		timer.setVisibility(2);
		readyForTriviaSelection = false;
		playMusicOnLaunch = true;
		music = new MusicPlayer(this, R.raw.spartacus_cool_mix, true);
		if (state != null) {
			restoreCreate(state);
		} else {
			amountCorrect = 0;
			questionsAnswered = 0;
			correctChoice = 0;
			ArrayList<String[]> temp = new ArrayList<String[]>();
			temp.add(getResources().getStringArray(R.array.easyQuestions));
			temp.add(getResources().getStringArray(R.array.hardQuestions));
			qManager = new QuestionsManager(temp);
			counter = new MyCount();

			try {
				qManager.nextQuestion();
			} catch (OutOfQuestionsException e) {
				Log.e(this.toString(), "Out of questions", e);
			}
		}
	}

	/**
	 * onStart() - Creates a new music player
	 */
	public void onStart() {
		super.onStart();

	}

	/**
	 * onResume() - maps text and start music when for resumed state
	 */
	public void onResume() {
		super.onResume();
		music.start();
		if (playMusicOnLaunch) {
			music.start();
		} else {
			musicButtonPressed();
		}
		playMusicOnLaunch = true;
		mapText(qManager.getCurrentQuestion());
	}

	/**
	 * onPause() - pauses the music in onPause stage
	 */
	public void onPause() {
		super.onPause();
		music.pause();
	}

	/**
	 * onStop() - frees cancel and counter resources when activity is no longer
	 * visible
	 */
	public void onStop() {
		super.onStop();
		counter.cancel();

	}

	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt("amountCorrect", amountCorrect);
		savedInstanceState.putInt("questionsAnswered", questionsAnswered);
		savedInstanceState.putInt("correctChoice", correctChoice);
		savedInstanceState.putString("correctAnswer", correctAnswer);
		savedInstanceState.putInt("counter_left", (int) counter.left);
		savedInstanceState.putBoolean("music_isPlaying", music.isPlaying());
		savedInstanceState.putInt("music_currentPosition",
				music.getCurrentPosition());
		savedInstanceState.putSerializable("qManager", qManager);
	}

	public void restoreCreate(Bundle savedInstanceState) {
		amountCorrect = savedInstanceState.getInt("amountCorrect");
		questionsAnswered = savedInstanceState.getInt("questionsAnswered");
		correctChoice = savedInstanceState.getInt("correctChoice");
		correctAnswer = savedInstanceState.getString("correctAnswer");
		playMusicOnLaunch = savedInstanceState.getBoolean("music_isPlaying");
		music.setCurrentPosition(savedInstanceState
				.getInt("music_currentPosition"));
		counter = new MyCount(savedInstanceState.getInt("counter_left"));
		qManager = (QuestionsManager) savedInstanceState
				.getSerializable("qManager");
	}

	/**
	 * onDestroy() - cleans up all instance variables
	 */
	public void onDestroy() {
		super.onDestroy();
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
		title = null;
		stats = null;
		bMusic = null;
		timer = null;
		qManager = null;
		counter = null;
		music.kill();
		music = null;
	}

	/**
	 * mapText() - Retrieves a trivia question data. If successful, populates
	 * the various button and Textfields appropriately. Starts a new timer. If
	 * not successful, the game is ended.
	 * 
	 */
	public void mapText(String[] array) {
		title.setText(array[0]);
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
	 * endGame() - This function is called to wrap up the game. The current
	 * session is properly filled with the correct data the next activity is
	 * called and this activity is finished
	 */
	public void endGame() {
		Intent intent = new Intent(this, ResultsActivity.class);
		intent.putExtra("correct", amountCorrect);
		intent.putExtra("total", TOTAL_QUESTIONS);
		setResult(RESULT_OK);
		startActivity(intent);
		finish();
	}

	/**
	 * onClick() - Calls the proper handling code for the associated button
	 * click.
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
	 * musicButtonPressed() - Plays music if user presses play music and vice
	 * versa. The text for the button is set appropriately.
	 */
	public void musicButtonPressed() {
		if (music.isPlaying()) {
			music.pause();
			bMusic.setText("Start Music");
		} else {
			music.start();
			bMusic.setText("Stop Music");
		}
	}

	/**
	 * buttonPressed() - If the choice was correct, a correct toast is shown. If
	 * an incorrect button was selected, a DialogFragment is shown displaying
	 * the correct choice. Will try to determine if the user has answered the
	 * total amount of trivia question, if they have endGame() is called,
	 * otherwise mapText() is called and the game goes on to the next question
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
			Toast.makeText(getApplicationContext(), "Correct!",
					Toast.LENGTH_SHORT).show();
		} else {
			showDialog(DIALOG_WRONG_ID);

			myRemoveDialog();
		}
		questionsAnswered++;
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (questionsAnswered == TOTAL_QUESTIONS) {
					endGame();
				} else {
					try {
						qManager.nextQuestion();
					} catch (OutOfQuestionsException e) {
						Log.e(this.toString(), "Ran out of trivia questions", e);
					}
					String[] nextQuestion = qManager.getCurrentQuestion();
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
					.setCancelable(false);

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
		stats.setText("You are " + amountCorrect + "/" + questionsAnswered
				+ ". Out of " + TOTAL_QUESTIONS + ".");
	}

	/**
	 * 
	 * Used to time the amount given to a user's trivia question.
	 * 
	 * @author Andre Perkins - akperkins1@gmail.com
	 * 
	 */
	protected class MyCount extends CountDownTimer {
		/** Gives the user 21 seconds to answer each question */
		final static long QUESTION_TIME = 21000;

		/** Sets one second in between every tick */
		final static long TICK_INTERVAL = 1000;

		/** Keeps track of the time left on timer */
		long left;

		long startQuestionTime;

		public MyCount() {
			super(QUESTION_TIME, TICK_INTERVAL);
			left = QUESTION_TIME;
			startQuestionTime = QUESTION_TIME;
		}

		public MyCount(long timeRemaining) {
			super(timeRemaining, TICK_INTERVAL);
			left = timeRemaining;
		}

		@Override
		/**
		 * 
		 * the user gets the answer wrong if they wait to long to answer.
		 */
		public void onFinish() {
			choiceSelected((correctChoice + 1) % 4);
		}

		@Override
		/**
		 * Updates every second, the amount of seconds the user has left to make a decision
		 */
		public void onTick(long millisUntilFinished) {
			timer.setText("Left: " + millisUntilFinished / 1000);
			left = millisUntilFinished;
		}

	}
}