package com.GameOfThrones.Trivia.Data;

import android.content.res.Resources;

import com.GameOfThrones.Trivia.R;

/**
 * Class used to read the character data in the strings.xml file
 * 
 * @author andre
 * 
 */
public class TriviaCharactersInfo {
	/**
	 * Returns a string[][] that obtains charcter data from the array in the
	 * string.xml file.
	 * 
	 * @param res
	 * @return
	 */
	public static String[][] getInfo(Resources res) {
		String[][] characters = null;
		String[] data = res.getStringArray(R.array.characters);
		characters = new String[data.length][];
		for (int i = 0; i < data.length; i++) {
			characters[i] = data[i].split("_");
		}
		return characters;
	}
}