package com.Spartaucs.Trivia.Data;

import com.Spartacus.Trivia.Exceptions.OutOfQuestionsException;

public abstract class Question {

	abstract public String[] getQuestion() throws OutOfQuestionsException;

}