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
	int[] questionsLeft;
	String[] currentQuestion;

	public QuestionsManager(ArrayList<String[]> strQuestions) {
		questionsCollection = new TriviaQuestions[strQuestions.size()];
		for (int i = 0; i < strQuestions.size(); i++) {
			questionsCollection[i] = new TriviaQuestions(strQuestions.get(i));
		}
		questionsLeft = new int[questionsCollection.length];
	}

	public void nextQuestion() throws OutOfQuestionsException {
		updateQuestionsLeft();

		int turn = GeneralAlgorithms.getWeightedProbability(questionsLeft);

		String[] question = questionsCollection[turn].getQuestion();

		currentQuestion = question;
	}

	public String[] getCurrentQuestion() {
		return currentQuestion;
	}

	public void updateQuestionsLeft() {
		for (int i = 0; i < questionsLeft.length; i++) {
			questionsLeft[i] = questionsCollection[i].unusedLeft();
		}
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