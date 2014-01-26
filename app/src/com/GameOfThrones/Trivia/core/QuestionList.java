package com.GameOfThrones.Trivia.core;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class QuestionList implements SimpleIterator<Question> {

	ArrayList<Question> questions;

	/**
	 * @param index
	 * @return
	 * @see java.util.ArrayList#get(int)
	 */
	public Question get(int index) {
		return questions.get(index);
	}

	/**
	 * @param object
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(Question object) {
		return questions.add(object);
	}

	/**
	 * @return
	 * @see java.util.ArrayList#size()
	 */
	public int size() {
		return questions.size();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8666055296634378061L;

	int currentPos;

	public QuestionList() {
		super();
		questions = new ArrayList<Question>();
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
