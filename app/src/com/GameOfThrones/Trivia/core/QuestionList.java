package com.GameOfThrones.Trivia.core;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class QuestionList implements SimpleIterator<Question> {

	ArrayList<Question> questions;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8666055296634378061L;

	int currentPos;

	public QuestionList(String[] array) {
		questions = new ArrayList<Question>();
		for (int i = 0; i < array.length; i = i + 6) {
			questions.add(new Question(array[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(array,
							i + 1, i + 4)), Integer.parseInt(array[i + 5])));
		}
		beforeFirst();
	}

	/**
	 * Shuffles the order questionView order
	 */
	public void shuffle() {
		Object[] temp = GeneralAlgorithms.shuffleArray(questions.toArray());
		questions.clear();
		for (Object o : temp) {
			questions.add((Question) o);
		}
		beforeFirst();
	}

	public void beforeFirst() {
		currentPos = -1;
	}

	public void next() {
		currentPos++;
	}

	public Question current() {
		return questions.get(currentPos);
	}

	public boolean isDone() {
		return currentPos >= questions.size();
	}

	/**
	 * Keeps questions only whose ids match any in the keepIds QuestionList.
	 * 
	 * @param keepsIds
	 *            - list of questions ids
	 */
	public void keepOnly(ArrayList<Integer> keepsIds) {
		ArrayList<Question> temp = new ArrayList<Question>();
		for (Question question : questions) {
			if (keepsIds.contains(question.getId())) {
				temp.add(question);
			}
		}
		questions = temp;
	}
}
