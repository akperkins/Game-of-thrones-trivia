package com.GameOfThrones.Trivia.Question;

public class Question {
	String question;
	String[] answers = new String[4];
	int correctAnswer;

	public Question(String question, String[] answers, int correctAnswer) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
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
}
