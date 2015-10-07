package parser;



public class Parser {

	/**
	 *
	 * @param input
	 * @return first word in a String
	 */
	static String extractFirstWord(String input) {
		input = input.split(" ")[0];
		return input.trim();
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
		int index;
		input = input.split(" ")[1].trim();
		index = Integer.parseInt(input);
		return index;
	}
}
