package com.Spartacus.Trivia.util;

import java.util.HashMap;

public class SessionManager {
	private static Session current;

	public static void startSession(HashMap<String, String> transDetails) {
		current = new Session(transDetails);
	}

	public static Session getCurrent() {
		return current;
	}

	public static void killSession() {
		current = null;
	}
}
