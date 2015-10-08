package parser;

import application.Constants;

public class Parser {

	/**
	 *
	 * @param input
	 * @return first word in a String
	 */
	static String extractFirstWord(String input) {
		return input.split(" ")[0].trim();
	}

	/**
	 *
	 * @param input
	 * @return the String, excluding the first word
	 */
	static String excludeFirstWord(String input) {
		input = input.substring(extractFirstWord(input).length());
		return input.trim();
	}

	public static int getIndex(String input) {
		input = input.split(" ")[1].trim();
		if (input.equalsIgnoreCase(Constants.SHOW_ALL)) {
			return -1;
		} else {
			return Integer.parseInt(input);
		}
	}
}
