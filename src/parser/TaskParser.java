package parser;

import java.util.ArrayList;
import java.util.Arrays;
import application.Constants;

public class TaskParser {


	static String getEdit(String input) {
		return "";
	}

	public static String getTitle(String input) {
		String text = new String();
		text = splitInputWithDictionary(Constants.DICTIONARY_ADD, input);

		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_DELETE, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_EDIT, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SEARCH, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_UNDO, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_REDO, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_EXIT, input);
		}
		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_SETPATH, input);
		}

		return text;
	}

	public static String splitInputWithDictionary(String[] dictionary, String input) {
		int firstIndex = -1;
		int lastIndex = input.length();

		for (String regex : dictionary) {
			if (input.indexOf(regex + " ") > firstIndex) {
				firstIndex = input.indexOf(regex + " ");
			}
			// System.out.println(firstIndex);
		}

		if (firstIndex < 0) {
			return null;
		}

		ArrayList<String> taskKeywords = new ArrayList<String>();
		taskKeywords.addAll(Arrays.asList(Constants.TASK_END_DATE));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_START_DATE));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_START_TIME));
		taskKeywords.addAll(Arrays.asList(Constants.TASK_END_TIME));
		taskKeywords.removeAll(Arrays.asList(dictionary));

		// System.out.println(taskKeywords);

		for (String regex : taskKeywords) {
			if (input.indexOf(" " + regex) < lastIndex && input.indexOf(" " + regex) > 0) {
				lastIndex = input.indexOf(regex + " ");
			}
			// System.out.println(lastIndex);
		}
		if (lastIndex <= firstIndex) {
			lastIndex = input.length();
		}

		return Parser.excludeFirstWord(input.substring(firstIndex, lastIndex)).trim();
	}
	
	public static int getIndex(String input) {
		input = input.split(" ")[1];		//replace with constant
		
		return Integer.parseInt(input);
	}
	
	public static String getAttribute(String input){
		return input.split(" ")[2].toLowerCase();
	}
	
	public static String getEditTitle(String input){
		for (int i = 0; i < 3; i++){
			input = Parser.excludeFirstWord(input);
		}
		return input;
	}

}
