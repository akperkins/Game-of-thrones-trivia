package com.Spartacus.Trivia.Models;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;

public abstract class QuestionMangeStrategy {

	abstract Questions getNextQuestion(Questions[] questions)
			throws OutOfQuestionsException;

}
