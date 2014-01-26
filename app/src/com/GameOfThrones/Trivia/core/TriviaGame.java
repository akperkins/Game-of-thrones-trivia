package com.GameOfThrones.Trivia.core;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * Manages group of questionsCollections
 * 
 * @author andre
 * 
 */
public class TriviaGame implements java.io.Serializable {
	private int total_questions;

	/** Gives the user 21 seconds to answer each trivia */
	public final static long QUESTION_TIME = 21000;

	/**
	 * Score the user accumulates
	 */
	private int gameScore;

	/** Keep track of current game statsView */
	private int amountCorrect, questionsAnswered;

	private static final long serialVersionUID = -408390483770836593L;

	private String[] currentQuestion;

	private int triviaTime;

	private QuestionList allQuestions;
	private int lastUsedIndex;

	public TriviaGame(QuestionList allquestions, int numberOfQuestions, int triviaTime) {
		allQuestions = new QuestionList();
		for (int i = 0; i < array.length; i = i + 6) {
			allQuestions.add(new Question(array[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(array,
							i + 1, i + 4)), Integer.parseInt(array[i + 5])));
		}
		this.allQuestions = allQuestions;
		lastUsedIndex = -1;
		allQuestions.shuffle();
		amountCorrect = 0;
		questionsAnswered = 0;
		if (allQuestions.size() < numberOfQuestions) {
			total_questions = allQuestions.size();
		}
		triviaTime = this.triviaTime;
	}

	public void nextQuestion() throws OutOfQuestionsException {
		if (allQuestions.isDone()) {
			throw new OutOfQuestionsException();
		} else {
			allQuestions.next();
		}
	}

	/**
	 * Keeps questions only whose ids match any in the keepIds QuestionList.
	 * 
	 * @param keepsIds
	 *            - list of questions ids
	 */
	public void keepOnly(ArrayList<Integer> keepsIds) {
		allQuestions.keepOnly(keepsIds);
	}

	/**
	 * @return the total_questions
	 */
	public int getTotal_questions() {
		return total_questions;
	}

	/**
	 * @param total_questions
	 *            the total_questions to set
	 */
	public void setTotal_questions(int total_questions) {
		this.total_questions = total_questions;
	}

	/**
	 * @return the gameScore
	 */
	public int getGameScore() {
		return gameScore;
	}

	/**
	 * @param gameScore
	 *            the gameScore to set
	 */
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * @return the amountCorrect
	 */
	public int getAmountCorrect() {
		return amountCorrect;
	}

	/**
	 * @param amountCorrect
	 *            the amountCorrect to set
	 */
	public void setAmountCorrect(int amountCorrect) {
		this.amountCorrect = amountCorrect;
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
	 * @throws OutOfQuestionsException
	 */
	public boolean choiceSelected(int button, long timeLeft) {
		int choice = getCorrectChoice();
		questionsAnswered++;
		return choice == button;
	}

	public int getCorrectChoice() {
		return allQuestions.get(lastUsedIndex).getCorrectAnswer();
	}

	public int getQuestionsAnswered() {
		return questionsAnswered;
	}

	public boolean isGameOver() {
		return questionsAnswered >= total_questions;
	}

	public Question getCurrentQuestion() {
		return allQuestions.get(lastUsedIndex);
	}
}