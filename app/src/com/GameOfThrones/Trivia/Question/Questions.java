package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;
import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 */
public class Questions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1869523227810548866L;

	ArrayList<Question> allQuestions;
	public ArrayList<Integer> used;
	public int questionIndex;
	public Integer[] questionOrder;

	public Questions(String[] array) {
		int numQuestions = array.length / 6;
		for (int i = 0; i < array.length / 6; i = i + 6) {
			allQuestions.add(new Question(array[i],
					(String[]) GeneralAlgorithms
							.sliceArray(array, i + 1, i + 4), Integer
							.parseInt(array[i + 5])));
		}

		used = new ArrayList<Integer>();
		questionIndex = 0;
		prepareOrder(questionIndex);
	}

	public int getSize() {
		return allQuestions.size();
	}

	public String[] getQuestionStrings() throws OutOfQuestionsException {
		if (used.size() == allQuestions.size()) {
			System.err
					.println("All triviaQuestion are used! Program terminating.....");
			throw new OutOfQuestionsException();
		}

		String[] str = new String[6];

		int tempPos = questionOrder[questionIndex];
		used.add(questionIndex++);

		for (Question q : allQuestions) {
			str[0] = q.getQuestion();

			String[] tempQuestions = q.getAnswers();
			str[1] = tempQuestions[1];
			str[2] = tempQuestions[2];
			str[3] = tempQuestions[3];
			str[4] = tempQuestions[4];

			str[5] = String.valueOf(q.getCorrectAnswer());
		}

		return str;
	}

	public boolean isUsed(int input) {
		return used.contains(input);
	}

	/**
	 * Each allQuestions position in the array is the ID this is used if an
	 * activity needs to be restarted in the middle a game. Adds each question
	 * in the position to the used array
	 * 
	 * @param usedPos
	 */
	public void setUsedQuestions(int[] usedPos) {
		for (int pos : usedPos) {
			used.add(pos);
		}
		// These used allQuestions have to get placed in the back of the array
		questionIndex = used.size();
		prepareOrder(questionIndex);
	}

	public void prepareOrder(int start) {
		Integer[] temp = new Integer[allQuestions.size()];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = i;
		}
		Integer[] tempPos = (Integer[]) GeneralAlgorithms.shuffleArray(temp);
		questionOrder = tempPos;
	}

	public int unusedLeft() {
		return questionOrder.length - questionIndex;
	}
}