package com.GameOfThrones.Trivia.Question;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class WeightedRemainingQuestionStrategy extends QuestionMangeStrategy {

	@Override
	Questions getNextQuestion(Questions[] questions)
			throws OutOfQuestionsException {
		int[] questionsLeft = new int[questions.length];
		for (int i = 0; i < questions.length; i++) {
			questionsLeft[i] = questions[i].unusedLeft();
		}
		int turn = GeneralAlgorithms.getWeightedProbability(questionsLeft);

		/** There weren't anymore questions */
		if (turn == -3) {
			throw new OutOfQuestionsException();
		}

		return questions[turn];
	}

}
