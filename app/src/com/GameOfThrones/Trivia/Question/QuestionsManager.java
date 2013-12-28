package com.GameOfThrones.Trivia.Question;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;

public class QuestionsManager implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -408390483770836593L;
	ArrayList<QuestionCollection> questionsCollections;
	String[] currentQuestion;
	QuestionMangeStrategy questionStrategy;

	public QuestionsManager(ArrayList<String[]> strQuestions,
			QuestionMangeStrategy questionStrategy) {
		questionsCollections = new ArrayList<QuestionCollection>();
		for (int i = 0; i < strQuestions.size(); i++) {
			questionsCollections
					.add(new QuestionCollection(strQuestions.get(i)));
		}
		this.questionStrategy = questionStrategy;
	}

	public void nextQuestion() throws OutOfQuestionsException {
		QuestionCollection chosen = questionStrategy
				.getNextQuestion(questionsCollections);

		String[] questionStrings = chosen.getCurrentQuestionStrings();

		currentQuestion = questionStrings;
	}

	public String[] getCurrentQuestionStrings() {
		return currentQuestion;
	}

	/**
	 * @return the questionsCollections
	 */
	public ArrayList<QuestionCollection> getQuestionsCollections() {
		return questionsCollections;
	}

	/**
	 * @param questionsCollections
	 *            the questionsCollections to set
	 */
	public void setQuestionsCollections(
			ArrayList<QuestionCollection> questionsCollections) {
		this.questionsCollections = questionsCollections;
	}

}