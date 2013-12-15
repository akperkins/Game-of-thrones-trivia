package com.Spartacus.Trivia.Models;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;
import com.Spartacus.Trivia.util.GeneralAlgorithms;

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
