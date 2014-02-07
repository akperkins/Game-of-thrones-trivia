package com.GameOfThrones.Trivia.data;

import java.util.ArrayList;

import android.content.res.Resources;

import com.GameOfThrones.Trivia.R;
import com.GameOfThrones.Trivia.core.GameCharacter;
import com.GameOfThrones.Trivia.core.Question;
import com.GameOfThrones.Trivia.core.QuestionCollection;
import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

/**
 * Used to parse the character data in the strings.xml file
 * 
 * @author andre
 * 
 */
public class StringsXMLFileData {
	/**
	 * Returns a string[][] that obtains character data from the array in the
	 * string.xml file.
	 * 
	 * @param res
	 * @return
	 */
	private StringsXMLFileData() {
	}

	public ArrayList<GameCharacter> getCharacters(Resources res) {
		String[][] charactersData = null;
		String[] data = res.getStringArray(R.array.characters);
		charactersData = new String[data.length][];
		for (int i = 0; i < data.length; i++) {
			charactersData[i] = data[i].split("_");
		}

		ArrayList<GameCharacter> characters = new ArrayList<GameCharacter>();
		for (String[] characterInfo : charactersData) {
			ArrayList<String> aliases = new ArrayList<String>();
			for (int i = 1; i < characterInfo.length; i++) {
				aliases.add(characterInfo[i]);
			}
			characters.add(new GameCharacter(characterInfo[0], aliases));
		}
		return characters;
	}

	public static QuestionCollection getQuestionList(Resources res) {
		String[] questionData = res.getStringArray(R.array.questions);
		ArrayList<Question> questions = new ArrayList<Question>();
		for (int i = 0; i < questionData.length; i = i + 6) {
			questions.add(new Question(questionData[i], GeneralAlgorithms
					.converToStrArray(GeneralAlgorithms.sliceArray(
							questionData, i + 1, i + 4)), Integer
					.parseInt(questionData[i + 5])));
		}
		return new QuestionCollection(questions);
	}
}