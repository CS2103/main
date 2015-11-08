//@@author A0124127R
package parser;

import java.util.Arrays;

import application.Constants;

public class CommandParser {

	public static String getCommand(String input) {
		String command = extractFirstWord(input.trim()).toLowerCase();
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
		} else if (checkForWordInDictionary(Constants.DICTIONARY_MARK, command)) {
			return Constants.DICTIONARY_MARK[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_UNMARK, command)) {
			return Constants.DICTIONARY_UNMARK[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_ENQUIREPATH, command)) {
			return Constants.DICTIONARY_ENQUIREPATH[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SHOW, command)) {
			return Constants.DICTIONARY_SHOW[0];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_HELP, command)) {
			return Constants.DICTIONARY_HELP[0];
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

	static String extractFirstWord(String input) {
		return input.split(Constants.SPACE)[0].trim();
	}

	static String excludeFirstWord(String input) {
		return input.substring(extractFirstWord(input).length()).trim();
	}
}
