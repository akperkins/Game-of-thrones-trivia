package com.Spartacus.Trivia.util;

import java.util.HashMap;

public class Session {
	private HashMap<String, String> transDetails;

	public Session(HashMap<String, String> transDetails) {
		if (transDetails != null) {
			this.transDetails = transDetails;
		} else {
			this.transDetails = new HashMap<String, String>();
		}
	}

	public void store(String name, String value) {
		transDetails.put(name, value);
	}

	public void remove(String name) {
		transDetails.remove(name);
	}

	public String get(String name) {
		return transDetails.get(name);
	}

}
