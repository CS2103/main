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



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import application.Constants;

public class DateParser {

	static Calendar calendar = Calendar.getInstance();

	public static int getMonth(String date) {
		return formatDate(date).get(Calendar.MONTH) + 1;
	}
	public static int getDay(String date) {
		return formatDate(date).get(Calendar.DAY_OF_MONTH);
	}
	public static int getYear(String date) {
		return formatDate(date).get(Calendar.YEAR);
	}
	public static String getDayOfTheWeek(String date) {
		return formatDate(date).getTime().toString().split(" ")[0].trim();
	}
	public static String getMonthOfTheYear(String date) {
		return formatDate(date).getTime().toString().split(" ")[1].trim();
	}

/*	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		String dateString = sc.nextLine();
		System.err.println(displayDate(dateString));
		sc.close();
	}
*/


	public static Calendar formatDate(String date) {
		for (String formatString : Constants.dateFormats) {
			try {
				calendar.setTime(new SimpleDateFormat(formatString).parse(date));
				modifyDefaultYear(calendar);
				return calendar;
			} catch (ParseException e) {

			}
		}
		return null;
	}

	private static Calendar modifyDefaultYear(Calendar calendar) {
		if (calendar.get(Calendar.YEAR) == 1970) {
			calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		}
		return calendar;
	}
	public static String displayDate(String date) {
		return getDayOfTheWeek(date) + ", " + getDay(date) + " " + getMonthOfTheYear(date) + " " + getYear(date);
	}
	
	public static DateTime getStartDate(String input) {

		String startDateText = TaskParser.splitInputWithDictionary(Constants.TASK_START_DATE, input);
		DateTime startDate = new DateTime();

		for (String formatString : Constants.dateFormats) {
			try {
				startDate = DateTimeFormat.forPattern(formatString).parseDateTime(startDateText);
				return startDate;
			} catch (NullPointerException e) {

			} catch (IllegalArgumentException e){
				
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

			} catch (IllegalArgumentException e){
				
			}
		}
		return DateTime.now();

	}
}