package com.Spartacus.Trivia.utilTest;

import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.GameOfThrones.Trivia.util.GeneralAlgorithms;

public class GeneralAlgorithmsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shuffleArrayTestSuccessful() {
		Integer[] temp = new Integer[9];
		temp[0] = -1;
		temp[1] = 0;
		temp[2] = 1;
		temp[3] = 2;
		temp[4] = 3;
		temp[5] = 4;
		temp[6] = -7;
		temp[5] = -100;
		temp[7] = 1001;
		temp[8] = 1002;
		Integer[] shuffledTemp = (Integer[]) GeneralAlgorithms
				.shuffleArray(temp);
		boolean onePosChanged = false;
		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i] + " != " + shuffledTemp[i]);
			if (temp[i] != shuffledTemp[i]) {
				onePosChanged = true;
			}
		}
		System.out.println("Alot of the positions should have changed\n");
		assertTrue(
				"At least one of the position changed after a shuffle. Probabilstic function"
						+ " therefore there is an extremly small change of false negative.",
				onePosChanged);
	}

	@Test
	public void getWeightedProbabilityNoData() {
		Assert.assertEquals("No data", -1,
				GeneralAlgorithms.getWeightedProbability(new int[] {}));

	}

	@Test
	public void getWeightedProbabilityNegativeData() {
		Assert.assertEquals("Negative data", -2,
				GeneralAlgorithms.getWeightedProbability(new int[] { 9, -9 }));
	}

	@Test
	public void getWeightedProbabilityAllZeroData() {
		Assert.assertEquals("Zero data", -3,
				GeneralAlgorithms.getWeightedProbability(new int[] { 0, 0 }));

	}

	@Test
	public void getWeightedProbabilityOneEntry() {
		Assert.assertEquals(0,
				GeneralAlgorithms.getWeightedProbability(new int[] { 6 }));
	}

	@Test
	public void getWeightedProbabilitySuccessful() {
		int[] temp = new int[4];
		temp[0] = 8;
		temp[1] = 3;
		temp[2] = 1000;
		temp[3] = 1000;
		int selected = GeneralAlgorithms.getWeightedProbability(temp);
		boolean validAnswer = selected > 0 && selected < 4;
		System.out
				.println("selected (should be 3 or 2 very often)=" + selected);
		Assert.assertTrue("Value in acceptable range", validAnswer);
	}

}
