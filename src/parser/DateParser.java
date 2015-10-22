package parser;

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

public class DateParser {

	public static DateTime getDateTime(String input) {
		DateTime dateTime;
		String dateString = new String();
		String timeString = new String();
		
		Pattern dp;
		Matcher dm;

		for (String regex: Constants.dateRegex) {
			try{
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

		Pattern tp;
		Matcher tm;
		for (String regex: Constants.timeRegex) {
			try{
				tp = Pattern.compile(regex);
				tm = tp.matcher(input.replace(dateString.trim(), "").trim());
				if (tm.find()) {
					timeString = tm.group().trim();
					System.out.println("[DATEPARSER] Time Match: " + timeString);
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}

		dateTime = parseDate(dateString);
		
		if (!timeString.isEmpty() && dateTime.getYear() == 0){
			dateTime = DateTime.now();
		}
		
		dateTime = dateTime.withHourOfDay(parseTime(timeString).getHourOfDay());
		dateTime = dateTime.withMinuteOfHour(parseTime(timeString).getMinuteOfHour());
		
		return dateTime;
	}

	private static DateTime parseDate(String dateString) {
		
		DateTime date = DateTime.now().withYear(0);
		
		for (String formatString : Constants.dateFormats) {
			try {
				date = DateTimeFormat.forPattern(formatString).parseDateTime(dateString);
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		if (date.getYear() == 2000){
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
}