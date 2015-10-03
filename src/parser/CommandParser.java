package parser;

import java.util.Arrays;

public class CommandParser {

	static final String[] DICTIONARY_ADD = {"add", "create"};
	static final String[] DICTIONARY_EDIT = {"edit", "modify"};
	static final String[] DICTIONARY_DELETE = {"delete", "del", "rm"};
	static final String[] DICTIONARY_UNDO = {"undo", "un"};
	static final String[] DICTIONARY_REDO = {"redo", "re"};
	static final String[] DICTIONARY_EXIT = {"exit", "quit"};
	static final String COMMAND_INVALID = "invalid";


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
		if (checkForWordInDictionary(DICTIONARY_ADD, command)) {
			return DICTIONARY_ADD[0];
		} else if (checkForWordInDictionary(DICTIONARY_DELETE, command)) {
			return DICTIONARY_DELETE[0];
		} else if (checkForWordInDictionary(DICTIONARY_EDIT, command)) {
			return DICTIONARY_EDIT[0];
		} else if (checkForWordInDictionary(DICTIONARY_UNDO, command)) {
			return DICTIONARY_UNDO[0];
		} else if (checkForWordInDictionary(DICTIONARY_REDO, command)) {
			return DICTIONARY_REDO[0];
		} else if (checkForWordInDictionary(DICTIONARY_EXIT, command)) {
			return DICTIONARY_EXIT[0];
		} else {
			return COMMAND_INVALID;
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
