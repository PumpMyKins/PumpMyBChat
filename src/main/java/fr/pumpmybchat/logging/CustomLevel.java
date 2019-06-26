package fr.pumpmybchat.logging;

import java.util.logging.Level;

public class CustomLevel extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5503045242273249038L;

	protected CustomLevel(String name, int value) {
		super(name, value);
	}

	public static Level CHAT(String serverName) {		
		return new CustomLevel("CHAT[\"" + serverName + "\"]", 12345);
	}

}
