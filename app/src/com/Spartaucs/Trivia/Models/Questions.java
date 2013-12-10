package com.Spartaucs.Trivia.Models;

import java.util.ArrayList;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;

public abstract class Questions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1752627876266039009L;
	
	protected String[] allQuestions;
	protected ArrayList<Integer> used;
	protected int questionIndex;
	protected Integer[] questionOrder;

	abstract public String[] getQuestion() throws OutOfQuestionsException;

	abstract public int unusedLeft();

	public String[] getALLQuestions() {
		return allQuestions;
	}

	public void setALLQuestions(String[] array) {
		this.allQuestions = array;
	}

	public ArrayList<Integer> getUsed() {
		return used;
	}

	public void setUsed(ArrayList<Integer> used) {
		this.used = used;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public Integer[] getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(Integer[] questionOrder) {
		this.questionOrder = questionOrder;
	}

	public Object[][] getQuestionState() {
		Object[] questions = getALLQuestions();
		Object[] used = getUsed().toArray();
		Object[] questionIndex = new Integer[] { getQuestionIndex() };
		Object[] questionOrder = getQuestionOrder();

		return new Object[][] { questions, used, questionIndex, questionOrder };
	}
}