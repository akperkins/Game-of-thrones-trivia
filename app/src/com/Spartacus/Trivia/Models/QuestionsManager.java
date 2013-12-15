package com.Spartacus.Trivia.Models;

import java.util.ArrayList;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;
import com.Spartacus.Trivia.util.GeneralAlgorithms;

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
		questionsCollection = new TriviaQuestions[strQuestions.size()];
		for (int i = 0; i < strQuestions.size(); i++) {
			questionsCollection[i] = new TriviaQuestions(strQuestions.get(i));
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

	public ArrayList<Object[][]> getQuestionsStates() {
		ArrayList<Object[][]> states = new ArrayList<Object[][]>();

		for (Questions q : questionsCollection) {
			Object[] questions = q.getALLQuestions();
			Object[] used = q.getUsed().toArray();
			Object[] questionIndex = new Integer[] { q.getQuestionIndex() };
			Object[] questionOrder = q.getQuestionOrder();

			states.add(new Object[][] { questions, used, questionIndex,
					questionOrder });
		}

		return states;
	}

	public Questions[] getQuestionsCollection() {
		return questionsCollection;
	}

	public void setQuestionsCollection(Questions[] questionsCollection) {
		this.questionsCollection = questionsCollection;
	}

}