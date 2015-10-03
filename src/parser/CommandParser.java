package parser;

import java.util.Arrays;
import application.Constants;

public class CommandParser {


	/*	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String rawInput = sc.nextLine();
		System.err.println(rawInput);
		System.err.println(extractFirstWord(rawInput));
		System.err.println(excludeFirstWord(rawInput));
		System.err.println(getCommand(rawInput));
		sc.close();
	}
	 */

	public static String getCommand(String input) {
		String command;
		command = Parser.extractFirstWord(input);
		if (checkForWordInDictionary(Constants.DICTIONARY_ADD, command)) {
			return Constants.DICTIONARY_ADD[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_DELETE, command)) {
			return Constants.DICTIONARY_DELETE[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_EDIT, command)) {
			return Constants.DICTIONARY_EDIT[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_UNDO, command)) {
			return Constants.DICTIONARY_UNDO[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_REDO, command)) {
			return Constants.DICTIONARY_REDO[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SEARCH, command)) {
			return Constants.DICTIONARY_SEARCH[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_EXIT, command)) {
			return Constants.DICTIONARY_EXIT[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SETPATH, command)) {
			return Constants.DICTIONARY_SETPATH[0];
		} else {
			return Constants.COMMAND_INVALID;
		}
	}

	private static boolean checkForWordInDictionary(String[] dictionary, String word) {
		if (Arrays.asList(dictionary).contains(word.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
}
