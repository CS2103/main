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
			text = splitInputWithDictionary(Constants.DICTIONARY_DELETE, input);
		}
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
		for (int i = 0; i < 3; i++) {
			input = excludeFirstWord(input);
		}
		return input;
	}

	public static String splitInputWithDictionary(String[] dictionary, String input) {
		int firstIndex = -1;
		int lastIndex = input.length();

		Pattern datePattern;
		Matcher dateMatcher;

		for (String regex : dictionary) {

			datePattern = Pattern.compile(regex);
			dateMatcher = datePattern.matcher(input.toLowerCase());

			if (dateMatcher.find()) {
				firstIndex = dateMatcher.start();
			}

		}

		if (firstIndex < 0) {
			return null;
		}

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
		if (lastIndex <= firstIndex) {
			lastIndex = input.length();
		}

		return excludeFirstWord(input.substring(firstIndex, lastIndex)).trim();
	}

	static String extractFirstWord(String input) {
		return input.split(Constants.SPACE)[0].trim();
	}

	static String excludeFirstWord(String input) {
		return input.substring(extractFirstWord(input).length()).trim();
	}

}
