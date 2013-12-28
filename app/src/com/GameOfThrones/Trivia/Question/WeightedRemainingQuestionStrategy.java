package com.GameOfThrones.Trivia.Question;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class WeightedRemainingQuestionStrategy extends QuestionMangeStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -193278486549755445L;

	@Override
	QuestionCollection getNextQuestion(ArrayList<QuestionCollection> questions)
			throws OutOfQuestionsException {

		int[] questionsLeft = new int[questions.size()];

		for (int i = 0; i < questionsLeft.length; i++) {
			questionsLeft[i] = questions.get(i).unusedLeft();
		}

		int turn = GeneralAlgorithms.getWeightedProbability(questionsLeft);

		/** There weren't anymore questions */
		if (turn == -3) {
			throw new OutOfQuestionsException();
		}

		return questions.get(turn);
	}

}
