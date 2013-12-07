package com.Spartaucs.Trivia.Data;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;

public class QuestionManager {
	Question[] questions;
	int turn;

	public QuestionManager(Question[] questions) {
		questions = this.questions;
		turn = 0;
	}

	public void turnIncrement() {
		turn = (turn++) % questions.length;
	}

	//will not work if one questions object runs out of questions first
	//must fix this
	public String[] getQuestion() throws OutOfQuestionsException {
		String[] question = questions[turn].getQuestion();
		turnIncrement();
		return question;
	}
}