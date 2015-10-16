package parser;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;

import application.Constants;

public class Parser implements ParserInterface {

	@Override
	public String getCommand(String input) {
		return CommandParser.getCommand(input);
	}

	@Override
	public String getStart(String input) {
		return splitInputWithDictionary(Constants.TASK_START_TIME, input);
	}

	@Override
	public String getEnd(String input) {
		return splitInputWithDictionary(Constants.TASK_END_TIME, input);
	}

	@Override
	public String getField(String input) {
		return input.split(Constants.SPACE)[2].trim().toLowerCase();
	}

	@Override
	public DateTime getStartTime(String input) {
		return DateParser.getDateTime(getStart(input));
	}

	@Override
	public DateTime getEndTime(String input) {
		return DateParser.getDateTime(getEnd(input));
	}

	@Override
	public int getIndex(String input) {
		input = input.split(Constants.SPACE)[1].trim();
		if (input.equalsIgnoreCase(Constants.SHOW_ALL)) {
			return -1;
		} else {
			return Integer.parseInt(input);
		}
	}

	@Override
	public String getTitle(String input) {
		String text = new String();
		text = splitInputWithDictionary(Constants.DICTIONARY_ADD, input);
		System.err.println("Te 1"+text);
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_DELETE, input);
			System.err.println("Te 2"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_EDIT, input);
			System.err.println("Te 3"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SEARCH, input);
			System.err.println("Te 4"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_UNDO, input);
			System.err.println("Te 5"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_REDO, input);
			System.err.println("Te 6"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_EXIT, input);
			System.err.println("Te 7"+text);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SETPATH, input);
			System.err.println("Te "+text);
		}
		return text;
	}

	public static String getEditTitle(String input){
		for (int i = 0; i < 3; i++){
			input = excludeFirstWord(input);
		}
		return input;
	}

	public static String splitInputWithDictionary(String[] dictionary, String input) {
		int firstIndex = -1;
		int lastIndex = input.length();

		for (String regex : dictionary) {
			if (input.indexOf(regex + " ") > firstIndex) {
				firstIndex = input.indexOf(regex + " ");
			}
		}

		if (firstIndex < 0) {
			return null;
		}

		ArrayList<String> taskKeywords = new ArrayList<String>();
		//taskKeywords.addAll(Arrays.asList(Constants.TASK_END_DATE));
		//taskKeywords.addAll(Arrays.asList(Constants.TASK_START_DATE));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_START_TIME));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_END_TIME));
		taskKeywords.removeAll(Arrays.asList(dictionary));

		for (String regex : taskKeywords) {
			if (input.indexOf(" " + regex) < lastIndex && input.indexOf(" " + regex) > 0) {
				lastIndex = input.indexOf(regex + " ");
			}
		}
		if (lastIndex <= firstIndex) {
			lastIndex = input.length();
		}

		return excludeFirstWord(input.substring(firstIndex, lastIndex)).trim();
	}

	static String extractFirstWord(String input) {
		return input.split(" ")[0].trim();
	}

	static String excludeFirstWord(String input) {
		return input.substring(extractFirstWord(input).length()).trim();
	}
}