package parser;

/**
 * Accepted date formats
 * 10/12
 * 10/12/1992
 * 10 dec
 * 10 decem
 * 10 octob
 * 10 december
 * 10 december 92
 * 10-12-1992
 * 10.12.1992
 * 10.12
 */

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import application.Constants;

public class DateParser {


	public static String displayDate(String input) {
		DateTime date = null;
		
		for (String formatString : Constants.dateFormats) {
			try {
				date = DateTimeFormat.forPattern(formatString).parseDateTime(input);
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}
		
		date = (date == null)?DateTime.now():date;
		
		return date.toLocalDate().toString("dd.MM.yy");
	}

	public static DateTime getStartDate(String input) {

		String startDateText = TaskParser.splitInputWithDictionary(Constants.TASK_START_DATE, input);
		DateTime startDate = new DateTime();

		for (String formatString : Constants.dateFormats) {
			try {
				startDate = DateTimeFormat.forPattern(formatString).parseDateTime(startDateText);
				return startDate;
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}
		return DateTime.now();
	}

	public static DateTime getEndDate(String input) {
		String endDateText = TaskParser.splitInputWithDictionary(Constants.TASK_END_DATE, input);
		DateTime endDate = new DateTime();

		for (String formatString : Constants.dateFormats) {
			try {
				endDate = DateTimeFormat.forPattern(formatString).parseDateTime(endDateText);
				return endDate;
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}
		return DateTime.now();

	}
}