package com.Spartacus.Trivia.util;

import java.util.ArrayList;
import java.util.Random;

public class GeneralAlgorithms {

	private GeneralAlgorithms() {
	}

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
		int randomIndex = new Random().nextInt(store.size());
		return store.get(randomIndex);
	}
}