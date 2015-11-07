//@@author A0121442X
package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import parser.DateParser;

public class DateParserTest {

	public static final String INCORRECT_DAY = new String("Day of month is incorrect");
	public static final String INCORRECT_MONTH = new String("Month of year is incorrect");
	public static final String INCORRECT_YEAR = new String("Year is incorrect");

	@Test
	public void testGetDateTime_Dash1() { // dd-MM-yyyy
		DateTime dateTime = DateParser.getDateTime("10-12-1992");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 10);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 12);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 1992);
	}

	@Test
	public void testGetDateTime_Dash2() { // d-MM-yyyy
		DateTime dateTime = DateParser.getDateTime("1-12-1992");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 1);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 12);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 1992);
	}

	@Test
	public void testGetDateTime_Dash3() { // dd-MM
		DateTime dateTime = DateParser.getDateTime("09-11");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 9);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 11);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == DateTime.now().getYear());
	}

	@Test
	public void testGetDateTime_Dash4() { // d-M-yyyy
		DateTime dateTime = DateParser.getDateTime("2-3-2027");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 2);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 3);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2027);
	}

	@Test
	public void testGetDateTime_Dash5() { // d-M-yy
		DateTime dateTime = DateParser.getDateTime("6-9-27");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 6);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 9);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2027);
	}

	@Test
	public void testGetDateTime_Slash1() { // dd/MM/yyyy
		DateTime dateTime = DateParser.getDateTime("16/12/2018");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 16);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 12);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2018);
	}

	@Test
	public void testGetDateTime_Slash2() { // d/M/yyyy
		DateTime dateTime = DateParser.getDateTime("3/10/2021");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 3);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 10);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2021);
	}

	@Test
	public void testGetDateTime_Slash3() { // dd/M/yyyy
		DateTime dateTime = DateParser.getDateTime("16/7/2019");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 16);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 7);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2019);
	}

	@Test
	public void testGetDateTime_Slash4() { // dd/MM/yy
		DateTime dateTime = DateParser.getDateTime("12/11/22");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 12);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 11);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2022);
	}

	@Test
	public void testGetDateTime_Slash5() { // dd/MM
		DateTime dateTime = DateParser.getDateTime("03/03");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 3);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 3);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == DateTime.now().getYear());
	}

	@Test
	public void testGetDateTime_Space1() {// dd MMM yy
		DateTime dateTime = DateParser.getDateTime("26 nov 22");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 26);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 11);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2022);
	}

	@Test
	public void testGetDateTime_Space2() {// d MMM yy
		DateTime dateTime = DateParser.getDateTime("7 oct 22");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 7);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 10);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2022);
	}

	@Test
	public void testGetDateTime_Space3() { // dd MMM
		DateTime dateTime = DateParser.getDateTime("28 feb");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 28);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 2);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == DateTime.now().getYear());
	}

	@Test
	public void testGetDateTime_LeapYear1() { // 2016 is a leap year
		DateTime dateTime = DateParser.getDateTime("29 feb 16");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 29);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 2);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2016);
	}

	@Test
	public void testGetDateTime_LeapYear2() { // 2017 is NOT a leap year
		DateTime dateTime = DateParser.getDateTime("29 feb 17");
		assertFalse(INCORRECT_DAY, dateTime.getDayOfMonth() == 29);
		assertFalse(INCORRECT_MONTH, dateTime.getMonthOfYear() == 2);
		assertFalse(INCORRECT_YEAR, dateTime.getYear() == 2017);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 0);
	}

	@Test
	public void testGetDateTime_DayBoundary1() { // day is negative
		DateTime dateTime = DateParser.getDateTime(" -2 mar 17");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 2);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 3);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2017);
	}

	@Test
	public void testGetDateTime_DayBoundary2() { // day > 31
		DateTime dateTime = DateParser.getDateTime(" 32 jul 17");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 2);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 7);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 2017);
	}

	/*
	 * Will fail to parse expected DateTime
	 */
	@Test
	public void testGetDateTime_DayBoundary3() { // day < 1
		DateTime dateTime = DateParser.getDateTime(" 0/06/17");
		assertFalse(INCORRECT_DAY, dateTime.getDayOfMonth() == 0);
		assertFalse(INCORRECT_MONTH, dateTime.getMonthOfYear() == 6);
		assertFalse(INCORRECT_YEAR, dateTime.getYear() == 2017);
	}

	/*
	 * Ignore the second digit of the month as user error and parse
	 */
	@Test
	public void testGetDateTime_MonthBoundary1() { // month > 12
		DateTime dateTime = DateParser.getDateTime(" 06/27/17");
		assertTrue(INCORRECT_DAY, dateTime.getDayOfMonth() == 6);
		assertTrue(INCORRECT_MONTH, dateTime.getMonthOfYear() == 2);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == DateTime.now().getYear());
	}

	/*
	 * Will fail to parse expected DateTime
	 */
	@Test
	public void testGetDateTime_MonthBoundary2() { // month < 1
		DateTime dateTime = DateParser.getDateTime(" 06/0/17");
		System.out.println(dateTime.getDayOfMonth() + " " + dateTime.getMonthOfYear() + " " + dateTime.getYear());
		assertFalse(INCORRECT_DAY, dateTime.getDayOfMonth() == 6);
		assertFalse(INCORRECT_MONTH, dateTime.getMonthOfYear() == 3);
		assertTrue(INCORRECT_YEAR, dateTime.getYear() == 0);
	}

	@Test
	public void testGetDateTime_YearBoundary1() { // year

	}
}
