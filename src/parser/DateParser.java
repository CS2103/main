package parser;

/**
 * Accepted date formats
 * 10/12
 *
 * 10 dec
 * 10 decem
 * 10 octob
 * 10 december
 * 10 december 92
 * 10-12-1992
 * 10/12/1992
 * 10.12.1992
 * 10.12
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import application.Constants;

public class DateParser {

	public static DateTime getDateTime(String input) {
		DateTime dateTime;
		String dateString = "";
		String timeString = "";
		Pattern dp;
		Matcher dm;

		for (String regex: Constants.dateRegex) {
			try{
				dp = Pattern.compile(regex);
				dm = dp.matcher(input);
				if (dm.find()) {
					dateString = dm.group().trim();
					System.out.println("[DATEPARSER] Date Match: " + dm.group());
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}

		Pattern tp;
		Matcher tm;
		for (String regex: Constants.timeRegex) {
			try{
				tp = Pattern.compile(regex);
				tm = tp.matcher(input.replace(dateString.trim(), "").trim());
				if (tm.find()) {
					timeString = tm.group().trim();
					//System.out.println("[DATEPARSER] Time Match: " + tm.group());
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}

		//System.err.println("Date: " + dateString + "\nTime: " + timeString);

		if (dateString.length() == 0 && timeString.length() == 0) {
			return DateTime.parse("00000");
		} else if (dateString.length() < 6 && timeString.length() == 0) {
			timeString = dateString;
			dateString = "";
		}

		//System.err.println("Date: " + dateString + "\nTime: " + timeString);

		dateTime = parseDate(dateString);
		//System.out.println(dateTime);
		dateTime = dateTime.withHourOfDay(parseTime(timeString).getHourOfDay());
		dateTime = dateTime.withMinuteOfHour(parseTime(timeString).getMinuteOfHour());
		return completeFields(dateTime);
	}

	private static DateTime parseDate(String dateString) {
		for (String formatString : Constants.dateFormats) {
			try {
				return DateTimeFormat.forPattern(formatString).parseDateTime(dateString);
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		return DateTime.parse("00000");
	}

	private static DateTime parseTime(String timeString) {

		for (String formatString : Constants.timeFormats) {
			try {
				return DateTimeFormat.forPattern(formatString).parseDateTime(timeString);
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		return DateTime.parse("00000");
	}

	private static DateTime completeFields(DateTime dateTime) {
		if ((dateTime.getYear()==0 && dateTime.getDayOfMonth()==1 && dateTime.getMonthOfYear()==1)) {
			dateTime = dateTime.withYear(DateTime.now().getYear()).withMonthOfYear(DateTime.now().getMonthOfYear()).withDayOfMonth(DateTime.now().getDayOfMonth());
		}
		if (dateTime.getYear()==2000) {
			dateTime = dateTime.withYear(DateTime.now().getYear());
		}
		return dateTime;
	}
}
