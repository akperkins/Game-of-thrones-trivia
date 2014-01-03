package com.GameOfThrones.Trivia.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This data structure is similar in use to a hash table except that the key
 * maps to a list of elements. Each time an element is mapped to a key, that
 * element is appended to the list of elements mapped to that key.
 * 
 * @author Andre Perkins - akperkins1@gmail.com
 * 
 */
public class HashArray<K, V> {

	/***
	 * Used to Generic keys to ArrayList of generic types
	 */
	private Hashtable<K, ArrayList<V>> hashMatrix;

	/**
	 * Constructor. Initializes the hasMatrix instance variable.
	 */
	public HashArray() {
		hashMatrix = new Hashtable<K, ArrayList<V>>();
	}

	/**
	 * Use this operation to add a K and a element (this will be the first
	 * element in the list of elements) to the hashMatirx. if the key is already
	 * listed in the patch test suite. the element is just appended to that
	 * list.
	 * 
	 * @param key
	 *            Key to add to hashArray
	 * @param elements
	 *            element added to element list for the key
	 */
	public void add(K key, V elements) {
		ArrayList<V> temp = hashMatrix.get(key);
		if (temp == null) {
			/** create new arrayList<Double> and add that matrix to the key **/
			temp = new ArrayList<V>();
		}
		temp.add(elements);
		hashMatrix.put(key, temp);
	}

	@Override
	/**
	 * Equals() - Returns true if the two hashMatrixs are equal. Returns
	 * false otherwise.
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HashArray)) {
			return false;
		}
		HashArray other = (HashArray) obj;
		if (hashMatrix == null) {
			if (other.hashMatrix != null) {
				return false;
			}
		} else if (!hashMatrix.equals(other.hashMatrix)) {
			return false;
		}
		return true;
	}

	/**
	 * getElements() - Returns an Arraylist of Doubles that map to the key that
	 * is passed. Returns null if the key is not listed in the hashMatrix.
	 * 
	 * @param key
	 *            - Object representing the key
	 * @return ArrayList<V> that the passed object maps to; null if key not
	 *         listed
	 */
	public ArrayList<V> getElement(K key) {
		ArrayList<V> ele = hashMatrix.get(key);
		return ele;
	}

	/**
	 * keys() - Returns an Enumeration of type K for all the keys listed in the
	 * hashMatrix.
	 * 
	 * @return Enumerations<K> - Returns list of keys
	 */
	public Enumeration<K> keys() {
		return hashMatrix.keys();
	}

	/**
	 * printAllElements() - Prints each list of elements on a single line for
	 * each key.
	 */
	public void printAllElements() {
		Enumeration<K> keys = hashMatrix.keys();
		Enumeration<ArrayList<V>> elements = hashMatrix.elements();

		while (keys.hasMoreElements()) {
			String timersList = "";
			for (V d : elements.nextElement()) {
				timersList += (String.valueOf(d) + ", ");
			}

			System.out.println(keys.nextElement() + ": " + timersList);
		}
	}

	/**
	 * setHashMatrix() - Setter for the hashMatrix instance variable.
	 * 
	 * @param hashMatrix
	 *            - HashTable<K,ArrayList<V>> To set as instance variable
	 */
	public void setHashMatrix(Hashtable<K, ArrayList<V>> hashMatrix) {
		this.hashMatrix = hashMatrix;
	}

	/**
	 * size() - Returns the number of keys listed in the hashMatrix
	 * 
	 * @return integer - Represents the number of keys stored
	 */
	public int size() {
		return hashMatrix.size();
	}
}
