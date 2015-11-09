//@@author A0124127R
package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Constants;

public class TitleParser {

	public static String getTitle(String input) {
		String text = new String();
		text = splitInputWithDictionary(Constants.DICTIONARY_ADD, input);
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SEARCH, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SETPATH, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SHOW, input);
		}
		return text;
	}

	public static String getEditTitle(String input) {
		for (int i = Constants.BEGINNING_OF_INDEX; i < Constants.EDIT_INDEX_TITLE; i++) {
			input = excludeFirstWord(input.trim());
		}
		return input;
	}

	public static String splitInputWithDictionary(String[] dictionary, String input) {
		
		int firstIndex = getFirstIndex(dictionary, input);
		int lastIndex = getLastIndex(dictionary, input);
		
		if (firstIndex < Constants.BEGINNING_OF_INDEX) {
			return null;
		} else if (lastIndex <= firstIndex) {
			lastIndex = input.length();
		}

		return excludeFirstWord(input.substring(firstIndex, lastIndex)).trim();
	}

	private static int getLastIndex(String[] dictionary, String input) {
		Pattern datePattern;
		Matcher dateMatcher;
		int lastIndex = input.length();
		
		ArrayList<String> taskKeywords = new ArrayList<String>();
		taskKeywords.addAll(Arrays.asList(Constants.TASK_START_DATETIME));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_END_DATETIME));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_RECURRING));
		taskKeywords.removeAll(Arrays.asList(dictionary));

		for (String regex : taskKeywords) {

			datePattern = Pattern.compile(regex);
			dateMatcher = datePattern.matcher(input);

			if (dateMatcher.find()) {
				if (dateMatcher.start() < lastIndex) {
					lastIndex = dateMatcher.start();
				}
			}
		}
		return lastIndex;
	}

	private static int getFirstIndex(String[] dictionary, String input) {
		Pattern datePattern;
		Matcher dateMatcher;
		int firstIndex = Constants.INDEX_OUT_OF_LIST;

		for (String regex : dictionary) {

			datePattern = Pattern.compile(regex);
			dateMatcher = datePattern.matcher(input.toLowerCase());

			if (dateMatcher.find()) {
				firstIndex = dateMatcher.start();
			}

		}
		return firstIndex;
	}

	static String extractFirstWord(String input) {
		return input.split(Constants.SPACE)[Constants.BEGINNING_OF_INDEX].trim();
	}

	static String excludeFirstWord(String input) {
		return input.substring(extractFirstWord(input).length()).trim();
	}

}
