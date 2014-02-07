package com.GameOfThrones.Trivia.core;

import java.io.Serializable;
import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class QuestionCollection implements SimpleIterator<Question>,
		Serializable {

	ArrayList<Question> questions;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8666055296634378061L;

	int currentPos;

	public QuestionCollection(ArrayList<Question> questions) {
		this.questions = questions;
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
	 * Keeps questions only whose ids match any in the keepIds
	 * QuestionCollection.
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
