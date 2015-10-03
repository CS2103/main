
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class TaskParser {

	static String[] dateFormats = { "d MMM yyyy", "d/M/y", "d/M", "d-M-y", "d MMM", "d MMMM" };
	static Calendar cal = Calendar.getInstance();
	static int currentYear = cal.get(Calendar.YEAR);

	static String getEdit(String input) {
		return "";
	}

	static String getTitle(String input) {
		String text = new String();
		text = splitInputWithDictionary(Constants.DICTIONARY_ADD, input);

		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_DELETE, input);
		}

		if (text == null) {
			text = splitInputWithDictionary(Constants.DICTIONARY_EDIT, input);
		}

		return text;
	}

	static Date getStartDate(String input) {
		
		String startDateText = splitInputWithDictionary(Constants.TASK_START_DATE, input);
		LocalDate startDate = LocalDate.now();
		Date newDate;

		for (String formatString : dateFormats) {
			try {
				startDate = DateTimeFormat.forPattern(formatString).parseLocalDate(startDateText);
				
				if (startDate.getYear() == 2000){
					startDate = startDate.withYear(currentYear);
				}
				
				newDate = startDate.toDate();
				return newDate;
			} catch (IllegalArgumentException e) {

			}
		}
		return null;
	}

	static Date getEndDate(String input) {
		String endDateText = splitInputWithDictionary(Constants.TASK_END_DATE, input);
		LocalDate endDate = LocalDate.now();
		Date newDate;

		for (String formatString : dateFormats) {
			try {
				endDate = DateTimeFormat.forPattern(formatString).parseLocalDate(endDateText);
				
				if (endDate.getYear() == 2000){
					endDate = endDate.withYear(currentYear);
				}
				
				newDate = endDate.toDate();
				return newDate;
			} catch (IllegalArgumentException e) {

			}
		}
		return null;

	}

	static String splitInputWithDictionary(String[] dictionary, String input) {
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
		taskKeywords.addAll(Arrays.asList(Constants.DICTIONARY_ADD));
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

}
