package com.Spartaucs.Trivia.Data;

import java.util.ArrayList;
import java.util.HashMap;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;

/**
 * 
 * @author andre
 * 
 */
public class TriviaQuestion extends Question {
	int numQ;
	private String[] array;
	private ArrayList<Integer> used;
	private HashMap<Integer, Integer> qOrder;
	int qIndex;

	public TriviaQuestion(String[] array) {
		numQ = array.length / 6;
		this.array = array;
		used = new ArrayList<Integer>();
		qIndex = 0;
	}

	public int getSize() {
		return numQ;
	}

	public String[] getQuestion() throws OutOfQuestionsException {
		int z;

		if (used.size() == numQ) {
			System.err
					.println("All triviaQuestion are used! Program terminating.....");
			throw new OutOfQuestionsException();
		}

		int temp;
		String[] str = new String[6];

		temp = qOrder.get(qIndex);

		used.add(temp);
		str[0] = array[temp * 6 + 1];
		str[1] = array[temp * 6 + 2];
		str[2] = array[temp * 6 + 3];
		str[3] = array[temp * 6 + 4];
		str[4] = array[temp * 6 + 5];
		str[5] = array[temp * 6 + 6];

		return str;
	}

	public boolean isUsed(int input) {
		return used.contains(input);
	}

	/**
	 * Each questions position in the array is the ID this is used if an
	 * activity needs to be restarted in the middle a game. Adds each question
	 * in the position to the used array
	 * 
	 * @param usedPos
	 */
	public void setUsedQuestions(int[] usedPos) {
		for (int pos : usedPos) {
			used.add(pos);
		}
		// These used questions have to get placed in the back of the array
		qIndex = used.size();
		qOrder = randomSort();
	}
}
