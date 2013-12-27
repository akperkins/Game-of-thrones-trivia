package com.GameOfThrones.Trivia.Question;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;

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

		String[] questionStrings = questions.getQuestionStrings();

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