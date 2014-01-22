package com.GameOfThrones.Trivia.Core;

import java.util.ArrayList;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * Manages group of questionsCollections
 * 
 * @author andre
 * 
 */
public class QuestionsCollectionManager implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -408390483770836593L;
	protected String[] currentQuestion;

	protected ArrayList<Question> allQuestions;
	protected int lastUsedIndex;

	public QuestionsCollectionManager(String[] array) {
		allQuestions = new ArrayList<Question>();
		for (int i = 0; i < array.length; i = i + 6) {
			allQuestions.add(new Question(array[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(array,
							i + 1, i + 4)), Integer.parseInt(array[i + 5])));
		}
		lastUsedIndex = -1;
		shuffle();
	}

	/**
	 * Shuffles the order question order
	 */
	public void shuffle() {
		Object[] temp = GeneralAlgorithms.shuffleArray(allQuestions.toArray());
		allQuestions.clear();
		for (Object o : temp) {
			allQuestions.add((Question) o);
		}
		lastUsedIndex = -1;
	}

	public void nextQuestion() throws OutOfQuestionsException {
		String[] questionStrings = getCurrentQuestionStrings2();

		currentQuestion = questionStrings;
	}

	/**
	 * Obtains question information for current question
	 * 
	 * @return
	 * @throws OutOfQuestionsException
	 */
	public String[] getCurrentQuestionStrings2() throws OutOfQuestionsException {
		if (unusedLeft() == 0) {
			System.err
					.println("All triviaQuestion are used! Program terminating.....");
			throw new OutOfQuestionsException();
		}
		Question curr = allQuestions.get(++lastUsedIndex);
		return curr.getStrings();
	}

	/**
	 * 
	 * @return Number of questions left to use
	 */
	public int unusedLeft() {
		return (allQuestions.size() - 1) - lastUsedIndex;
	}

	public String[] getCurrentQuestionStrings() {
		return currentQuestion;
	}

	/**
	 * Keeps questions only whose ids match any in the keepIds List.
	 * 
	 * @param keepsIds
	 *            - list of questions ids
	 */
	public void keepOnly(ArrayList<Integer> keepsIds) {
		ArrayList<Question> temp = new ArrayList<Question>();
		for (Question question : allQuestions) {
			if (keepsIds.contains(question.getId())) {
				temp.add(question);
			}
		}
		allQuestions = temp;
	}

	public ArrayList<Question> getAllQuestions() {
		return allQuestions;
	}
}