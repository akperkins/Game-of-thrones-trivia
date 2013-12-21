package com.GameOfThrones.Trivia.Models;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class QuestionsManager implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -408390483770836593L;
	Questions[] questionsCollection;
	String[] currentQuestion;
	QuestionMangeStrategy questionStrategy;

	public QuestionsManager(ArrayList<String[]> strQuestions,
			QuestionMangeStrategy questionStrategy) {
		questionsCollection = new Questions[strQuestions.size()];
		for (int i = 0; i < strQuestions.size(); i++) {
			questionsCollection[i] = new Questions(strQuestions.get(i));
		}
		this.questionStrategy = questionStrategy;
	}

	public void nextQuestion() throws OutOfQuestionsException {
		Questions questions = questionStrategy
				.getNextQuestion(questionsCollection);

		String[] questionStrings = questions.getQuestion();

		currentQuestion = questionStrings;
	}

	public String[] getCurrentQuestion() {
		return currentQuestion;
	}

	public Questions[] getQuestionsCollection() {
		return questionsCollection;
	}

	public void setQuestionsCollection(Questions[] questionsCollection) {
		this.questionsCollection = questionsCollection;
	}

}