package com.Spartacus.Trivia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.Spartacus.Trivia.util.SessionManager;
import com.Spartaucs.Trivia.Data.QuestionManager;
import com.Spartaucs.Trivia.Data.TriviaQuestion;

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
	QuestionManager qManager;

	/** Where the data for the trivia data for the triviaQuestion is stored */
	String[] easyQuestions, hardQuestions;

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

	/**
	 * onCreate() - initializes the instance data for the activity
	 */
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.trivia);
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
		amountCorrect = 0;
		questionsAnswered = 0;
		correctChoice = 0;
		easyQuestions = getResources().getStringArray(R.array.easyQuestions);
		hardQuestions = getResources().getStringArray(R.array.hardQuestions);
		TriviaQuestion[] temp = new TriviaQuestion[2];
		temp[0] = new TriviaQuestion(easyQuestions);
		temp[1] = new TriviaQuestion(hardQuestions);
		qManager = new QuestionManager(temp);
	}

	/**
	 * onStart() - Creates a new music player
	 */
	public void onStart() {
		super.onStart();
		music = new MusicPlayer(this, R.raw.spartacus_cool_mix);
	}

	/**
	 * onResume() - maps text and start music when for resumed state
	 */
	public void onResume() {
		super.onResume();
		music.start();
		mapText();
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
		music.kill();
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
		easyQuestions = null;
		hardQuestions = null;
		qManager = null;
	}

	/**
	 * mapText() - Retrieves a trivia question data. If successful, populates
	 * the various button and Textfields appropriately. Starts a new timer. If
	 * not successful, the game is ended.
	 * 
	 */
	public void mapText() {
		String[] array;
		try {
			array = qManager.getQuestion();
			title.setText(array[0]);
			b1.setText(array[1]);
			b2.setText(array[2]);
			b3.setText(array[3]);
			b4.setText(array[4]);
			correctChoice = Integer.parseInt(array[5]);
			correctAnswer = array[correctChoice];

			counter = new MyCount();
			counter.start();
			updateStats();
		} catch (OutOfQuestionsException e) {
			// Display to user that the application is out of questions at the
			// momentS
			// TODO end the game and go on to the endGameActivity
			Log.e("Game Activity - mapText", "User ran out of questions", e);
			endGame();
		}
	}

	/**
	 * endGame() - This function is called to wrap up the game. The current
	 * session is properly filled with the correct data the next activity is
	 * called and this activity is finished
	 */
	public void endGame() {
		Intent intent = new Intent(this, ResultsActivity.class);
		SessionManager.getCurrent().store("correct",
				String.valueOf(amountCorrect));
		SessionManager.getCurrent().store("total",
				String.valueOf(TOTAL_QUESTIONS));
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
			buttonPressed(1);

			break;
		case R.id.button2:
			buttonPressed(2);
			break;
		case R.id.button3:
			buttonPressed(3);

			break;
		case R.id.button4:
			buttonPressed(4);

			break;
		case R.id.musicButton:
			musicButtonPressed();
		}
	}

	/**
	 * musicButtonPressed() - Plays music if user presses play music and vice
	 * versa. The text for the button is set appropriately.
	 */
	public void musicButtonPressed() {
		if (!music.isPlaying()) {
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
	public void buttonPressed(int button) {
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
					mapText();
				}
			}
		}, 1000);
		counter.cancel();
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
			builder.setMessage("Wrong! The correct answer is: " + correctAnswer)
					.setCancelable(false)
					.setNeutralButton("Dismiss",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});

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

		public MyCount() {
			super(QUESTION_TIME, TICK_INTERVAL);
		}

		@Override
		/**
		 * 
		 * the user gets the answer wrong if they wait to long to answer.
		 */
		public void onFinish() {
			buttonPressed((correctChoice + 1) % 4);
		}

		@Override
		/**
		 * Updates every second, the amount of seconds the user has left to make a decision
		 */
		public void onTick(long millisUntilFinished) {
			timer.setText("Left: " + millisUntilFinished / 1000);
		}
	}
}