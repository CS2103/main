package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Constants;

public class Parser {

	public Parser() {

	}

	public static String getCommandName(String input) {
		String command = getFirstWord(input);

		if (hasWordInDictionary(Constants.DICTIONARY_ADD, command)) {
			return Constants.COMMAND_ADD;
		} else if (hasWordInDictionary(Constants.DICTIONARY_DELETE, command)) {
			return Constants.COMMAND_DELETE;
		} else if (hasWordInDictionary(Constants.DICTIONARY_SHOW, command)) {
			return Constants.COMMAND_SHOW;
		} else if (hasWordInDictionary(Constants.DICTIONARY_SEARCH, command)) {
			return Constants.COMMAND_SEARCH;
		} else if (hasWordInDictionary(Constants.DICTIONARY_EDIT, command)) {
			return Constants.COMMAND_EDIT;
		} else if (hasWordInDictionary(Constants.DICTIONARY_DONE, command)) {
			return Constants.COMMAND_DONE;
		} else if (hasWordInDictionary(Constants.DICTIONARY_NOT_DONE, command)) {
			return Constants.COMMAND_NOT_DONE;
		} else if (hasWordInDictionary(Constants.DICTIONARY_UNDO, command)) {
			return Constants.COMMAND_UNDO;
		} else if (hasWordInDictionary(Constants.DICTIONARY_REDO, command)) {
			return Constants.COMMAND_REDO;
		} else if (hasWordInDictionary(Constants.DICTIONARY_VIEW_HOMESCREEN,
				command)) {
			return Constants.COMMAND_VIEW_HOMESCREEN;
		} else if (hasWordInDictionary(Constants.DICTIONARY_EXIT, command)) {
			return Constants.COMMAND_EXIT;
		} else if (hasWordInDictionary(Constants.DICTIONARY_HELP, command)) {
			return Constants.COMMAND_HELP;
		} else {
			return Constants.COMMAND_INVALID;
		}
	}

	public static String getTaskName(String input) {
		String[] words = input.split(Constants.REGEX_ONE_OR_MORE_SPACES);
		String taskName = "";
		if (hasWordInDictionary(Constants.DICTIONARY_ADD, words[0].trim())) {
			for (int i = 1; i < words.length; i++) {
				if (!hasWordInDictionary(Constants.DICTIONARY_PARAMETERS, words[i])) {
					taskName += " " + words[i];
				} else {
					break;
				}
			}
		}
		return taskName.trim();
	}

	public static String getKeywordsForSearchCommand(String input) {
		try {
			input = cleanUp(input);
			if (countWords(input) <= 1) {
				return null;
			} else {
				return removeFirstWord(input);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static int countWords(String input) {
		input = cleanUp(input);
		return input.split(Constants.SPACE).length;
	}

	public static String cleanUp(String input) {
		input = input.trim();
		input = input.replaceAll(Constants.REGEX_ONE_OR_MORE_SPACES,
				Constants.SPACE);
		return input;
	}

	public static String removeFirstWord(String input) {
		try {
			return input.split(Constants.SPACE, 2)[Constants.INDEX_2ND_PART];
		} catch (Exception e) {
			return null;
		}
	}

	public static String removeFirst2Words(String input) {
		try {
			return input.split(Constants.SPACE, 3)[Constants.INDEX_3RD_PART];
		} catch (Exception e) {
			return input;
		}
	}

	public static String getFirstWord(String input) {
		try {
			return input.split(Constants.SPACE, 2)[Constants.INDEX_BEGIN];
		} catch (Exception e) {
			return input;
		}
	}

	public static boolean hasWordInDictionary(String[] dictionary, String word) {
		word = word.trim();
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsCommand(String[] words,
			String[] commandDictionary) {
		for (int i = 0; i < words.length; i++) {
			if (hasWordInDictionary(commandDictionary, words[i])) {
				return true;
			}
		}
		return false;
	}

	public static String removeDeleteParameters(String input) {
		// Delete the 'delete' keywords
		for (int i = 0; i < Constants.DICTIONARY_DELETE.length; i++) {
			String word = Constants.DICTIONARY_DELETE[i];
			input = cleanUp(input.replaceAll(Constants.REGEX_WORD_START + word
					+ Constants.REGEX_WORD_END, Constants.EMPTY_STRING));
		}

		// Delete parameters
		for (int i = 0; i < Constants.DICTIONARY_PARAMETERS.length; i++) {
			String word = Constants.DICTIONARY_PARAMETERS[i];
			input = cleanUp(input.replaceAll(Constants.REGEX_WORD_START + word
					+ Constants.REGEX_WORD_END, Constants.EMPTY_STRING));
		}
		return input;
	}

	public static String getParameterInOriginalCase(String originalInput,
			String lowercaseParameter) {
		Pattern p = Pattern.compile(lowercaseParameter,
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(originalInput);

		int start = 0, end = 0;
		if (m.find()) {
			start = m.start();
			end = m.end();
		}

		return originalInput.substring(start, end);
	}

	public static String removeWordsWithinQuotes(String input) {
		input = cleanUp(input);

		int countDoubleQuotes = countNumberOfDoubleQuotes(input);
		Pattern p = Pattern.compile(Constants.DOUBLE_QUOTE);
		Matcher m = p.matcher(input);

		if (countDoubleQuotes == 0) {
			return input;
		} else if (countDoubleQuotes == 2) {
			int startQuoteIndex = 0, endQuoteIndex = 0, count = 0;
			while (m.find()) {
				count++;
				if (count == 1) {
					startQuoteIndex = m.start();
				} else {
					assert count == 2;
					endQuoteIndex = m.start();
				}
			}
			String wordsWithinQuotes = input.substring(startQuoteIndex + 1,
					endQuoteIndex);
			String stringToRemove = Constants.DOUBLE_QUOTE + wordsWithinQuotes
					+ Constants.DOUBLE_QUOTE;

			if (getWordBeforeQuote(startQuoteIndex, input).equalsIgnoreCase(
					Constants.KEYWORDS[Constants.INDEX_KEYWORD_AT])) {
				stringToRemove = Constants.REGEX_AT_WITH_SPACES
						+ stringToRemove;
			}
			return cleanUp(input.replaceAll(stringToRemove,
					Constants.EMPTY_STRING));
		} else {
			assert countDoubleQuotes == 4;
			int firstQuoteStart = 0, firstQuoteEnd = 0, secondQuoteStart = 0, secondQuoteEnd = 0, count = 0;
			while (m.find()) {
				count++;
				if (count == 1) {
					firstQuoteStart = m.start();
				} else if (count == 2) {
					firstQuoteEnd = m.start();
				} else if (count == 3) {
					secondQuoteStart = m.start();
				} else {
					assert count == 4;
					secondQuoteEnd = m.start();
				}
			}

			// first 2 double quotes
			String wordsWithinFirstQuotes = input.substring(
					firstQuoteStart + 1, firstQuoteEnd);
			String stringToRemoveFromFirstQuotes = Constants.DOUBLE_QUOTE
					+ wordsWithinFirstQuotes + Constants.DOUBLE_QUOTE;

			if (getWordBeforeQuote(firstQuoteStart, input).equalsIgnoreCase(
					Constants.KEYWORDS[Constants.INDEX_KEYWORD_AT])) {
				stringToRemoveFromFirstQuotes = Constants.REGEX_AT_WITH_SPACES
						+ stringToRemoveFromFirstQuotes;
			}

			// next 2 double quotes
			String wordsWithinSecondQuotes = input.substring(
					secondQuoteStart + 1, secondQuoteEnd);
			String stringToRemoveFromSecondQuotes = Constants.DOUBLE_QUOTE
					+ wordsWithinSecondQuotes + Constants.DOUBLE_QUOTE;

			if (getWordBeforeQuote(secondQuoteStart, input).equalsIgnoreCase(
					Constants.KEYWORDS[Constants.INDEX_KEYWORD_AT])) {
				stringToRemoveFromSecondQuotes = Constants.REGEX_AT_WITH_SPACES
						+ stringToRemoveFromSecondQuotes;
			}

			String temp = cleanUp(input.replaceAll(
					stringToRemoveFromFirstQuotes, Constants.EMPTY_STRING));
			temp = cleanUp(temp.replaceAll(stringToRemoveFromSecondQuotes,
					Constants.EMPTY_STRING));
			return temp;
		}
	}

	public static int countNumberOfDoubleQuotes(String input) {
		Pattern p = Pattern.compile(Constants.DOUBLE_QUOTE);
		Matcher m = p.matcher(input);

		int count = 0;
		while (m.find()) {
			count++;
		}
		return count;
	}

	public static String getWordBeforeQuote(int indexQuote, String input) {
		String inputUntilQuote = cleanUp(input.substring(Constants.INDEX_BEGIN,
				indexQuote));
		return getLastWord(inputUntilQuote);
	}

	public static String getLastWord(String str) {
		try {
			str = cleanUp(str);
			int indexOfLastSpace = str.lastIndexOf(Constants.SPACE_CHAR);

			if (indexOfLastSpace >= Constants.LIMIT_ZERO) {
				return str.substring(indexOfLastSpace).trim();
			} else {
				assert indexOfLastSpace == Constants.NOT_FOUND;
				return str;
			}
		} catch (Exception e) {
			return Constants.EMPTY_STRING;
		}
	}
}
