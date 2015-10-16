package parser;

public class TimeParserTest {
	/*
	private static final String TIME_AM = "AM";
	private static final String TIME_PM = "PM";
	private static final String TIME_12_HOUR_2359 = "11:59 PM";
	private static final String TIME_24_HOUR_2359 = "2359 hrs";
	private static final String TIME_FORMAT_2359 = "java.util.GregorianCalendar[time=59340000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id=\"Asia/Singapore\",offset=28800000,dstSavings=0,useDaylight=false,transitions=9,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1970,MONTH=0,WEEK_OF_YEAR=1,WEEK_OF_MONTH=1,DAY_OF_MONTH=1,DAY_OF_YEAR=1,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=11,HOUR_OF_DAY=23,MINUTE=59,SECOND=0,MILLISECOND=0,ZONE_OFFSET=27000000,DST_OFFSET=0]";


	@Test
	public void testGetHourOfDay() {
		testGetHourOfDayCommand("should return hour of day", 23, "2359");
	}

	@Test
	public void testGetHour() {
		testGetHourCommand("should return hour", 11, "2359");
	}

	@Test
	public void testGetMinute() {
		testGetMinuteCommand("should return the minute of the hour", 59, "2359");
	}

	@Test
	public void testGetAmPm() {
		testGetAmPmCommand("should return am or pm", TIME_PM, "2359");
		testGetAmPmCommand("should return am or pm", TIME_AM, "1159");
	}

	@Test
	public void testFormatTime() {
		testFormatTimeCommand("parse a time", TIME_FORMAT_2359, "2359");
	}

	@Test
	public void testDisplayFullTime() {
		testDisplayFullTimeCommand("should return time in 24 hour format", TIME_24_HOUR_2359, "2359");
	}

	@Test
	public void testDisplayTime() {
		testDisplayTimeCommand("should return time in 12 hour format", TIME_12_HOUR_2359, "2359");
	}

	@Test
	public void testGetEndTime() {
		testGetEndTimeCommand(String description, int expected, String input)
	}



	private void testGetHourOfDayCommand(String description, int expected, String input) {
		assertEquals(description, expected, TimeParser.getHourOfDay(input));
	}
	private void testGetAmPmCommand(String description, String expected, String input) {
		assertEquals(description, expected, TimeParser.getAmPm(input));
	}

	private void testDisplayTimeCommand(String description, String expected, String input) {
		assertEquals(description, expected, TimeParser.display12Time(input));
	}
	private void testDisplayFullTimeCommand(String description, String expected, String input) {
		assertEquals(description, expected, TimeParser.display24Time(input));
	}
	private void testGetMinuteCommand(String description, int expected, String input) {
		assertEquals(description, expected, TimeParser.getStartTime(input));
	}
	private void testGetHourCommand(String description, int expected, String input) {
		assertEquals(description, expected, TimeParser.getEndTime(input));
	}

	private void testGetEndTimeCommand(String description, int expected, String input) {
		assertEquals(description, expected, TimeParser.getEndTime(input));
	}

	private void testFormatTimeCommand(String description, String expected, String input) {
		assertEquals(description, expected, TimeParser.formatTime(input).toString());
	}

	 */
}
