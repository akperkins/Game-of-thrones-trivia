package com.GameOfThrones.Trivia.Question;

import java.io.Serializable;
import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;
import com.GameOfThrones.Trivia.util.OutOfQuestionsException;

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

	public void keepOnly(ArrayList<Integer> keepsIds) {
		ArrayList<Question> temp = new ArrayList<Question>();

		for (Question question : allQuestions) {
			if (keepsIds.contains(question.getId())) {
				temp.add(question);
			}
		}
		allQuestions = temp;
	}

	/**
	 * @return the allQuestions
	 */
	public ArrayList<Question> getAllQuestions() {
		return allQuestions;
	}

	/**
	 * @param allQuestions
	 *            the allQuestions to set
	 */
	public void setAllQuestions(ArrayList<Question> allQuestions) {
		this.allQuestions = allQuestions;
	}

	/**
	 * @return the lastUsedIndex
	 */
	public int getLastUsedIndex() {
		return lastUsedIndex;
	}

	/**
	 * @param lastUsedIndex
	 *            the lastUsedIndex to set
	 */
	public void setLastUsedIndex(int lastUsedIndex) {
		this.lastUsedIndex = lastUsedIndex;
	}

}