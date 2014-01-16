package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {
	/**
	 * Used to identify object during Serialization processes
	 */
	private static final long serialVersionUID = 7306464553446020989L;
	
	static int classId;
	String question;
	String[] answers = new String[4];
	int correctAnswer;
	String[] characters;
	int id;

	public Question(String question, String[] answers, int correctAnswer) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.characters = new String[0];
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

	public Question(String question, String[] answers, int correctAnswer,
			String[] characters) {
		this(question, answers, correctAnswer);
		this.characters = characters;
	}

	public String[] getCharacters() {
		return characters;
	}

	public void setCharacters(String[] characters) {
		this.characters = characters;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswer() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String[] getStrings() {
		String[] str = new String[6];
		str[0] = question;
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
		return "Question [question=" + question + ", answers="
				+ Arrays.toString(answers) + ", correctAnswer=" + correctAnswer
				+ ", gameCharacters=" + Arrays.toString(characters) + "]";
	}

	/* (non-Javadoc)
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