package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to store algorithms that can be re-used in multiple instances.
 * This class will be moved to a Android Library project. This class is not
 * meant to be instantiated.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class GeneralAlgorithms {

	private GeneralAlgorithms() {
	}

	/**
	 * shufffleArray - Returns an array with the same contents as the array
	 * passed except the contents are shuffled.
	 * 
	 * @param array
	 *            - Object array to be shuffled
	 * @return Object[] - shuffled array
	 */
	public static Object[] shuffleArray(Object[] array) {
		Object[] temp = array.clone();

		Random rnd = new Random();
		for (int i = temp.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			/** Simple swap */
			Object a = temp[index];
			temp[index] = temp[i];
			temp[i] = a;
		}
		return temp;
	}

	/**
	 * getWeightedProbability() - selects a position in the array. The position
	 * chances are multiplied by the int value of the value in that array
	 * position. This function will return -1 for an empty array, -2 if any of
	 * the values is less than 0 or -3 if all the values of every position is 0.
	 * 
	 * @param probs
	 *            - int[] that contains weighted values
	 * @return select probs position
	 */
	public static int getWeightedProbability(int[] probs) {
		if (probs.length == 0) {
			return -1;
		}
		for (int temp : probs) {
			if (temp < 0) {
				return -2;
			}
		}
		ArrayList<Integer> store = new ArrayList<Integer>();
		for (int i = 0; i < probs.length; i++) {
			for (int j = 0; j < probs[i]; j++) {
				store.add(i);
			}
		}
		if (store.size() == 0) {
			return -3;
		}
		int randomIndex = new Random().nextInt(store.size());
		return store.get(randomIndex);
	}

	public static Object[] sliceArray(Object[] objArray, int startIndex,
			int endIndex) {
		if (startIndex < 0 || endIndex > objArray.length || objArray == null) {
			return null;
		}
		Object[] temp = new Object[(endIndex + 1) - startIndex];
		for (int i = startIndex; i <= endIndex; i++) {
			temp[i - startIndex] = objArray[i];
		}
		return temp;
	}

	public static String[] converToStrArray(Object[] strs) {
		String[] temp = new String[strs.length];

		for (int i = 0; i < strs.length; i++) {
			if (strs[i] instanceof String) {
				temp[i] = (String) strs[i];
			} else {
				throw new ClassCastException("the " + i + " object in array"
						+ " failed instanceof String check");
			}
		}

		return temp;
	}

	public static boolean containsString(String searched, ArrayList<String> searchEle) {
		for (String s : searchEle) {
			if (searched.contains(s)) {
				return true;
			}
		}
		return false;
	}
}