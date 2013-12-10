package com.Spartacus.Trivia.util;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Contains information about current user session.
 * 
 * @author Andre Perkins - akperins1@gmail.com
 * 
 */
public class SessionManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SessionManager instance;
	private static Hashtable<String, String> transDetails;

	private SessionManager() {
		transDetails = new Hashtable<String, String>();
	}

	public static SessionManager getInstance() {
		if (transDetails != null) {
			startSession();
		}
		return instance;
	}

	public static void startSession() {
		instance = new SessionManager();
	}

	public static void saveSession(SessionManager session) {
		instance = session;
	}

	public static void killSession() {
		instance = null;
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
