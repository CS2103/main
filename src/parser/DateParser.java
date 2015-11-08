//@@author A0124127R
package parser;

import java.util.logging.Level;

/**
 * Accepted date formats
 * 10/12
 * 10 dec
 * 10 decem
 * 10 octob
 * 10 december
 * 10 december 92
 * 10-12-1992
 * 10/12/1992
 * 10.12.1992
 * 10.12
 * 10-12
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import application.Constants;
import application.LogHandler;

public class DateParser {


	public static DateTime getDateTime(String input) {
		DateTime dateTime;
		if(input != null){
			input = input.replaceAll("\\s+", Constants.SPACE);
		}
		
		String dateString = new String();
		String timeString = new String();

		dateString = getDateString(input);

		timeString = getTimeString(input, dateString);

		if (!dateString.isEmpty()) {
			dateTime = parseDate(dateString);
		} else {
			dateTime = getDayDate(input);
		}

		if (!timeString.isEmpty() && dateTime.getYear() == 0) {
			dateTime = DateTime.now();
		}

		dateTime = dateTime.withHourOfDay(parseTime(timeString).getHourOfDay());
		dateTime = dateTime.withMinuteOfHour(parseTime(timeString).getMinuteOfHour());

		return dateTime;
	}

	private static DateTime getDayDate(String input) {
		DateTime dateTime = DateTime.now().withYear(0);

		for (String regex : Constants.DICTIONARY_TODAY) {
			if (input != null && input.contains(regex)) {
				dateTime = DateTime.now();
			}
		}

		for (String regex : Constants.DICTIONARY_TOMORROW) {
			if (input != null && input.contains(regex)) {
				dateTime = DateTime.now().plusDays(1);
			}
		}
		return dateTime;
	}

	private static String getTimeString(String input, String dateString) {
		Pattern tp;
		Matcher tm;
		String timeString = new String();
		for (String regex : Constants.timeRegex) {
			try {
				tp = Pattern.compile(regex);
				tm = tp.matcher(input.replace(dateString.trim(), "").trim());
				if (tm.find()) {
					timeString = tm.group().trim();
					System.out.println("[DATEPARSER] Time Match: " + timeString);
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
				LogHandler.log(Level.SEVERE, Constants.ERROR_INPUT_DATE);
			}
		}
		return timeString;
	}

	private static String getDateString(String input) {
		Pattern dp;
		Matcher dm;
		String dateString = new String();

		for (String regex : Constants.dateRegex) {
			try {
				dp = Pattern.compile(regex);
				dm = dp.matcher(input);
				if (dm.find()) {
					dateString = dm.group().trim();
					System.out.println("[DATEPARSER] Date Match: " + dateString);
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		return dateString;
	}

	private static DateTime parseDate(String dateString) {

		DateTime date = DateTime.now().withYear(0);

		for (String formatString : Constants.dateFormats) {
			try {
				date = DateTimeFormat.forPattern(formatString).parseDateTime(dateString);
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
				LogHandler.log(Level.SEVERE, Constants.ERROR_INPUT_TIME);
			}
		}
		if (date.getYear() == 2000) {
			date = date.withYear(DateTime.now().getYear());
		}

		return date;
	}

	private static DateTime parseTime(String timeString) {

		for (String formatString : Constants.timeFormats) {
			try {
				return DateTimeFormat.forPattern(formatString).parseDateTime(timeString);
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		return DateTime.now().withHourOfDay(0).withMinuteOfHour(0);
	}
	
	public static String getRecurValue(String input) {

		String recurValue = new String();

		for (int i = 0; i < Constants.TASK_RECURRING.length; i++) {
			if (input.toLowerCase().contains(Constants.TASK_RECURRING[i])) {
				recurValue = Constants.TASK_RECURRING[i];
			}
		}
		return recurValue;
	}
}