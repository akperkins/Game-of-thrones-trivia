package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;
import java.util.ArrayList;

import com.GameOfThrones.Trivia.Exceptions.OutOfQuestionsException;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 */
public class QuestionCollection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1869523227810548866L;

	ArrayList<Question> allQuestions;
	public int lastUsedIndex;

	public QuestionCollection(String[] array) {
			allQuestions = new ArrayList<Question>();
		for (int i = 0; i < array.length; i = i + 6) {
			allQuestions.add(new Question(array[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(array,
							i + 1, i + 4)), Integer.parseInt(array[i + 5])));
		}
		lastUsedIndex = -1;
		shuffle();
	}

	public int getSize() {
		return allQuestions.size();
	}

	public String[] getCurrentQuestionStrings() throws OutOfQuestionsException {
		if (unusedLeft() == 0) {
			System.err
					.println("All triviaQuestion are used! Program terminating.....");
			throw new OutOfQuestionsException();
		}
		Question curr = allQuestions.get(++lastUsedIndex);
		return curr.getStrings();
	}

	public void shuffle() {
		Object[] temp = GeneralAlgorithms.shuffleArray(allQuestions.toArray());
		allQuestions.clear();
		for (Object o : temp) {
			allQuestions.add((Question) o);
		}
		lastUsedIndex = -1;
	}

	public int unusedLeft() {
		return (allQuestions.size() - 1) - lastUsedIndex;
	}
}