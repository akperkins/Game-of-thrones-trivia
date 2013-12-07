package com.Spartacus.Trivia.util;

import java.util.HashMap;

/**
 * Contains information about current user session.
 * 
 * @author Andre Perkins - akperins1@gmail.com
 * 
 */
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
