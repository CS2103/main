//@@author A0124127R
package parser;

import java.util.Arrays;

import application.Constants;

public class CommandParser {

	public static String getCommand(String input) {
		assert input != null : Constants.ERROR_NULL_INPUT;
		String command = extractFirstWord(input.trim()).toLowerCase();
		if (checkForWordInDictionary(Constants.DICTIONARY_ADD, command)) {
			return Constants.DICTIONARY_ADD[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_DELETE, command)) {
			return Constants.DICTIONARY_DELETE[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_EDIT, command)) {
			return Constants.DICTIONARY_EDIT[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_UNDO, command)) {
			return Constants.DICTIONARY_UNDO[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_REDO, command)) {
			return Constants.DICTIONARY_REDO[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SEARCH, command)) {
			return Constants.DICTIONARY_SEARCH[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_EXIT, command)) {
			return Constants.DICTIONARY_EXIT[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SETPATH, command)) {
			return Constants.DICTIONARY_SETPATH[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_MARK, command)) {
			return Constants.DICTIONARY_MARK[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_UNMARK, command)) {
			return Constants.DICTIONARY_UNMARK[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_ENQUIREPATH, command)) {
			return Constants.DICTIONARY_ENQUIREPATH[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_SHOW, command)) {
			return Constants.DICTIONARY_SHOW[Constants.BEGINNING_OF_INDEX];
		} else if (checkForWordInDictionary(Constants.DICTIONARY_HELP, command)) {
			return Constants.DICTIONARY_HELP[Constants.BEGINNING_OF_INDEX];
		} else {
			return Constants.COMMAND_INVALID;
		}
	}

	private static boolean checkForWordInDictionary(String[] dictionary, String word) {
		assert word != null : Constants.ERROR_NULL_INPUT;
		if (Arrays.asList(dictionary).contains(word.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	static String extractFirstWord(String input) {
		return input.split(Constants.SPACE)[Constants.BEGINNING_OF_INDEX].trim();
	}

}
