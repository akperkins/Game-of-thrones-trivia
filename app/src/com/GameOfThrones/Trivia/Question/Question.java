package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;
import java.util.Arrays;
/**
 * Represents a trivia question
 * @author andre
 *
 */
public class Question implements Serializable {
	/**
	 * Used to identify object during Serialization processes
	 */
	private static final long serialVersionUID = 7306464553446020989L;

	static int classId;
	String trivia;
	String[] answers = new String[4];
	int correctAnswer;
	int id;

	public Question(String trivia, String[] answers, int correctAnswer) {
		this.trivia = trivia;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		id = classId++;
	}

	/**
	 * @return the classId
	 */
	public static int getClassId() {
		return classId;
	}

	/**
	 * @param classId
	 *            the classId to set
	 */
	public static void setClassId(int classId) {
		Question.classId = classId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return trivia getter
	 */
	public String getTrivia() {
		return trivia;
	}

	/***
	 * 
	 * @param trivia
	 *            trivia setter
	 */
	public void setTrivia(String question) {
		this.trivia = question;
	}

	/**
	 * 
	 * @return answers setter
	 */
	public String[] getAnswer() {
		return answers;
	}

	/**
	 * 
	 * @param answers
	 *            setter
	 */
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	/**
	 * 
	 * @return correctAnswer getter
	 */
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * 
	 * @param correctAnswer
	 *            setter
	 */
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * Returns the instance variable all in String format.
	 * @return question data
	 */
	public String[] getStrings() {
		String[] str = new String[6];
		str[0] = trivia;
		str[1] = answers[0];
		str[2] = answers[1];
		str[3] = answers[2];
		str[4] = answers[3];
		str[5] = String.valueOf(correctAnswer);
		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question [trivia=" + trivia + ", answers="
				+ Arrays.toString(answers) + ", correctAnswer=" + correctAnswer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}
}