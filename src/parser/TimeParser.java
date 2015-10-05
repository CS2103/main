package parser;

/**
 * Accepted time formats
 * 2359
 * 23:45
 * 23
 * 23.45
 * 3.45
 * 3:45
 * 3 45
 * 3
 */

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import application.Constants;

public class TimeParser {

	public static String display24Time(String input) {
		DateTime time = null;

		for (String formatStr : Constants.timeFormats) {
			try {
				time = DateTimeFormat.forPattern(formatStr).parseDateTime(input);
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}

		if (time == null)
			time = DateTime.now();

		return time.toLocalTime().toString("HHmm");
	}

	public static String display12Time(String input) {
		DateTime time = null;

		for (String formatStr : Constants.timeFormats) {
			try {
				time = DateTimeFormat.forPattern(formatStr).parseDateTime(input);
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}

		if (time == null)
			time = DateTime.now();

		return time.toLocalTime().toString("hh:mm a");
	}

	public static DateTime getStartTime(String input) {
		DateTime startTime = DateTime.now();
		String startTimeText = TaskParser.splitInputWithDictionary(Constants.TASK_START_TIME, input);

		for (String formatStr : Constants.timeFormats) {
			try {
				startTime = DateTimeFormat.forPattern(formatStr).parseDateTime(startTimeText);
				return startTime;
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}

		return startTime.withTimeAtStartOfDay();
	}

	public static DateTime getEndTime(String input) {
		DateTime endTime = DateTime.now();
		String endTimeText = TaskParser.splitInputWithDictionary(Constants.TASK_END_TIME, input);

		for (String formatStr : Constants.timeFormats) {
			try {
				endTime = DateTimeFormat.forPattern(formatStr).parseDateTime(endTimeText);
				return endTime;
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e) {

			}
		}

		return endTime.withTime(23, 59, 0, 0);
	}
}