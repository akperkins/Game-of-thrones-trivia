package com.GameOfThrones.Trivia.Core;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * Manages group of questionsCollections
 * 
 * @author andre
 * 
 */
public class TriviaGame implements java.io.Serializable {
	/**
	 * 
	 */

	int total_questions;

	/**
	 * Score the user accumulates
	 */
	int gameScore;

	/** Keep track of current game statsView */
	int amountCorrect, questionsAnswered;

	private static final long serialVersionUID = -408390483770836593L;
	protected String[] currentQuestion;

	protected ArrayList<Question> allQuestions;
	protected int lastUsedIndex;

	public TriviaGame(String[] array, int questionAmount) {

		allQuestions = new ArrayList<Question>();
		for (int i = 0; i < array.length; i = i + 6) {
			allQuestions.add(new Question(array[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(array,
							i + 1, i + 4)), Integer.parseInt(array[i + 5])));
		}
		lastUsedIndex = -1;
		shuffle();
		amountCorrect = 0;
		questionsAnswered = 0;
		if (allQuestions.size() < questionAmount) {
			total_questions = allQuestions.size();
		}

	}

	/**
	 * Shuffles the order questionView order
	 */
	public void shuffle() {
		Object[] temp = GeneralAlgorithms.shuffleArray(allQuestions.toArray());
		allQuestions.clear();
		for (Object o : temp) {
			allQuestions.add((Question) o);
		}
		lastUsedIndex = -1;
	}

	public void nextQuestion() throws OutOfQuestionsException {
		if (lastUsedIndex == allQuestions.size()) {
			throw new OutOfQuestionsException();
		} else {
			lastUsedIndex++;
		}
	}

	/**
	 * Keeps questions only whose ids match any in the keepIds List.
	 * 
	 * @param keepsIds
	 *            - list of questions ids
	 */
	public void keepOnly(ArrayList<Integer> keepsIds) {
		ArrayList<Question> temp = new ArrayList<Question>();
		for (Question question : allQuestions) {
			if (keepsIds.contains(question.getId())) {
				temp.add(question);
			}
		}
		allQuestions = temp;
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