//@@author A0124127R
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import parser.Parser;

public class ParserTest {

    Parser parser = new Parser();

    public static final String ADD_TEST_1 = new String("ADD SHOPPING from 10.12.15 to 14/12/15");
    public static final String ADD_TEST_2 = new String("create buy an apple from 21.5 1245 to 13:52 15-6");

    public static final String DELETE_TEST_1 = new String("deLEte 3");
    public static final String DELETE_TEST_2 = new String("del 6");

    public static final String EDIT_TEST_1 = new String("edit 1 title new Task Title");
    public static final String EDIT_TEST_2 = new String("modify 6 start 10.12.15");
    private static final String EDIT_TITLE_SPECIALCHAR_1 = "edit 1 title $#@ newT@skTitle";
    private static final String EDIT_TITLE_WHITESPACE_1 = "edit 1 title    newTaskTitle";
    private static final String EDIT_FIELD_SPECIALCHAR = "edit   1 # newTaskTitle";
    private static final String EDIT_WHITESPACE_INDEX_1 = "edit   1 title newTaskTitle";
    private static final String EDIT_WHITESPACE_INDEX_2 = "edit 2    start 10.11.15";

    public static final String MARK_TEST_1 = new String("mark 6");
    public static final String MARK_TEST_2 = new String("m 2");

    public static final String UNMARK_TEST_1 = new String("unmark 7");
    public static final String UNMARK_TEST_2 = new String("um 27");

    public static final String SEARCH_TEST_1 = new String("search apple");

    public static final String ADD_INCONSISTENT_CASE_TEST_1 = new String(
	    "cReAtE Buy An apPle frOm 21.5 1245 to 13:52 15-6");
    public static final String ADD_INCONSISTENT_CASE_TEST_2 = new String("AdD SHOPPING from 10.12.15 to 14/5/12");

    public static final String ADD_WHITESPACE_CASE_TEST_1 = new String(
	    "     add task title 123 from 10 nov 15 to 12 nov 15");
    public static final String ADD_WHITESPACE_CASE_TEST_2 = new String(
	    "add    task title456 from 10 nov 14 to 11 nov 14");
    public static final String ADD_WHITESPACE_CASE_TEST_3 = new String(
	    "add task title456 from     10 nov 14 to    11 nov 14");
    public static final String ADD_WHITESPACE_CASE_TEST_4 = new String(
	    "add task title456 from 10     nov 14 to 11 nov     14");

    public static final String ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_1 = new String(
	    "add print pages 12,13,24,28 & 30<42 !");
    public static final String ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_2 = new String(
	    "add add !@#$%^&*() to buddy list");

    public static final String INVALID_DATE = "Wrong Date Regex";
    public static final String INVALID_FIELD = "Wrong Field";
    public static final String INCORRECT_DAY = new String("Day of month is incorrect");
    public static final String INCORRECT_MONTH = new String("Month of year is incorrect");
    public static final String INCORRECT_YEAR = new String("Year is incorrect");

    /*
     * Test cases that returns the command keyword
     */

    @Test
    public void testGetCommand_Add() {
	assertEquals("add", parser.getCommand(ADD_TEST_1));
	assertEquals("add", parser.getCommand(ADD_TEST_2));
    }

    @Test
    public void testGetCommand_Delete() {
	assertEquals("delete", parser.getCommand(DELETE_TEST_1));
	assertEquals("delete", parser.getCommand(DELETE_TEST_2));
    }

    @Test
    public void testGetCommand_Edit() {
	assertEquals("edit", parser.getCommand(EDIT_TEST_1));
	assertEquals("edit", parser.getCommand(EDIT_TEST_2));
    }

    @Test
    public void testGetCommand_Mark() {
	assertEquals("mark", parser.getCommand(MARK_TEST_1));
	assertEquals("mark", parser.getCommand(MARK_TEST_2));
    }

    @Test
    public void testGetCommand_Unmark() {
	assertEquals("unmark", parser.getCommand(UNMARK_TEST_1));
	assertEquals("unmark", parser.getCommand(UNMARK_TEST_2));
    }

    @Test
    public void testGetCommand_AddInconsistentCase() {
	assertEquals("add", parser.getCommand(ADD_INCONSISTENT_CASE_TEST_1));
	assertEquals("add", parser.getCommand(ADD_INCONSISTENT_CASE_TEST_2));
    }

    @Test
    public void testGetCommand_AddWhiteSpace() {
	assertEquals("add", parser.getCommand(ADD_WHITESPACE_CASE_TEST_1));
	assertEquals("add", parser.getCommand(ADD_WHITESPACE_CASE_TEST_2));
	assertEquals("add", parser.getCommand(ADD_WHITESPACE_CASE_TEST_3));
	assertEquals("add", parser.getCommand(ADD_WHITESPACE_CASE_TEST_4));
    }

    /*
     * Test Cases to return the task title only
     */

    @Test
    public void testGetTitle_AlphabetString() {
	assertEquals("SHOPPING", parser.getTitle(ADD_TEST_1));
	assertEquals("buy an apple", parser.getTitle(ADD_TEST_2));
	assertEquals("apple", parser.getTitle(SEARCH_TEST_1));
    }

    @Test
    public void testGetTitle_AlphanumericAndSpecialCharacterString() {
	assertEquals("print pages 12,13,24,28 & 30<42 !", parser.getTitle(ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_1));
	assertEquals("add !@#$%^&*() to buddy list", parser.getTitle(ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_2));

    }

    @Test
    public void testGetTitle_WhiteSpace() {
	assertEquals("task title 123", parser.getTitle(ADD_WHITESPACE_CASE_TEST_1));
	assertEquals("task title456", parser.getTitle(ADD_WHITESPACE_CASE_TEST_2));
	assertEquals("task title456", parser.getTitle(ADD_WHITESPACE_CASE_TEST_4));

	// Invalid Date Regex after "from"
	assertFalse(INVALID_DATE, parser.getTitle(ADD_WHITESPACE_CASE_TEST_3).equals("task title456"));
    }

    @Test
    public void testGetIndex() {

	int delResult1 = parser.getIndex(DELETE_TEST_1);
	int delResult2 = parser.getIndex(UNMARK_TEST_2);

	assertEquals(3, delResult1);
	assertEquals(27, delResult2);
    }

    /*
     * Test cases to extract Date and Time fields
     */

    @Test
    public void testGetStartDate_AddNormal() {
	DateTime startDateTime = new DateTime();
	startDateTime = parser.getStartDateTime(ADD_TEST_1);

	DateTime startDateResult = new DateTime().withYear(2015).withMonthOfYear(12).withDayOfMonth(10);

	assertTrue(INCORRECT_DAY, startDateTime.getDayOfMonth() == startDateResult.getDayOfMonth());
	assertTrue(INCORRECT_MONTH, startDateTime.getMonthOfYear() == startDateResult.getMonthOfYear());
	assertTrue(INCORRECT_YEAR, startDateTime.getYear() == startDateResult.getYear());
    }

    @Test
    public void testGetEndDate_AddNormal() {
	DateTime endDateTime = new DateTime();
	endDateTime = parser.getEndDateTime(ADD_TEST_1);

	DateTime endDateResult = new DateTime().withYear(2015).withMonthOfYear(12).withDayOfMonth(14);

	assertTrue(INCORRECT_DAY, endDateTime.getDayOfMonth() == endDateResult.getDayOfMonth());
	assertTrue(INCORRECT_MONTH, endDateTime.getMonthOfYear() == endDateResult.getMonthOfYear());
	assertTrue(INCORRECT_YEAR, endDateTime.getYear() == endDateResult.getYear());

    }

    @Test
    public void testGetStartDate_WhiteSpace() {
	DateTime startDateTime = new DateTime();
	startDateTime = parser.getStartDateTime(ADD_WHITESPACE_CASE_TEST_4);

	DateTime startDateResult = new DateTime().withYear(2014).withMonthOfYear(11).withDayOfMonth(10);

	assertTrue(INCORRECT_DAY, startDateTime.getDayOfMonth() == startDateResult.getDayOfMonth());
	assertTrue(INCORRECT_MONTH, startDateTime.getMonthOfYear() == startDateResult.getMonthOfYear());
	assertTrue(INCORRECT_YEAR, startDateTime.getYear() == startDateResult.getYear());
    }

    @Test
    public void testGetEndDate_WhiteSpace() {
	DateTime endDateTime = new DateTime();
	endDateTime = parser.getEndDateTime(ADD_WHITESPACE_CASE_TEST_4);

	DateTime endDateResult = new DateTime().withYear(2014).withMonthOfYear(11).withDayOfMonth(11);

	assertTrue(INCORRECT_DAY, endDateTime.getDayOfMonth() == endDateResult.getDayOfMonth());
	assertTrue(INCORRECT_MONTH, endDateTime.getMonthOfYear() == endDateResult.getMonthOfYear());
	assertTrue(INCORRECT_YEAR, endDateTime.getYear() == endDateResult.getYear());

    }

    // Fails due to incorrect regex after "from/to"
    // expects a digit/"today"/"tomorrow"
    @Test
    public void testGetStartDate_SpecialChar() {
	DateTime startDateTime = new DateTime();
	startDateTime = parser.getStartDateTime("add TaskTitle from $10 oct 15 to ##11 oct 15");

	DateTime startDateResult = new DateTime().withYear(2015).withMonthOfYear(10).withDayOfMonth(10);

	assertFalse(INCORRECT_DAY, startDateTime.getDayOfMonth() == startDateResult.getDayOfMonth());
	assertFalse(INCORRECT_MONTH, startDateTime.getMonthOfYear() == startDateResult.getMonthOfYear());
	assertFalse(INCORRECT_YEAR, startDateTime.getYear() == startDateResult.getYear());
    }

    /*
     * Tests if Start field is before End field
     */

    @Test
    public void testIsValidEndingTime_AddNormal() {
	DateTime endDateTime = new DateTime();
	DateTime startDateTime = new DateTime();

	startDateTime = parser.getStartDateTime(ADD_TEST_1);
	endDateTime = parser.getEndDateTime(ADD_TEST_1);

	// True if Start is before End, and False if otherwise
	assertTrue(INVALID_DATE, parser.isValidEndingTime(startDateTime, endDateTime));
	assertFalse(INVALID_DATE, parser.isValidEndingTime(endDateTime, startDateTime));
    }

    @Test
    public void testIsValidEndingTime_AddEqual() {
	DateTime endDateTime = new DateTime();
	DateTime startDateTime = new DateTime();

	startDateTime = parser.getStartDateTime(ADD_TEST_1);
	endDateTime = startDateTime;

	// False if end field is equal to start field
	assertFalse(INVALID_DATE, parser.isValidEndingTime(startDateTime, endDateTime));
    }

    /*
     * Returns a string with the field label for edit function
     */
    @Test
    public void testGetField_EditNormal() {

	assertTrue(INVALID_FIELD, parser.getField(EDIT_TEST_1).equals("title"));
	assertTrue(INVALID_FIELD, parser.getField(EDIT_TEST_2).equals("start"));
    }

    @Test
    public void testGetField_EditWhiteSpace() {

	assertTrue(INVALID_FIELD, parser.getField(EDIT_WHITESPACE_INDEX_1).equals("title"));
	assertTrue(INVALID_FIELD, parser.getField(EDIT_WHITESPACE_INDEX_2).equals("start"));
    }

    @Test
    public void testGetField_EditSpecialChar() {

	assertFalse(INVALID_FIELD, parser.getField(EDIT_FIELD_SPECIALCHAR).equals("title"));
    }

    /*
     * Returns a string with the new field values
     */
    @Test
    public void testGetEditTitle_EditNormal() {

	assertTrue(INVALID_FIELD, parser.getEditTitle(EDIT_TEST_1).equals("new Task Title"));
	assertTrue(INVALID_FIELD, parser.getEditTitle(EDIT_TEST_2).equals("10.12.15"));
    }

    @Test
    public void testGetEditTitle_EditWhiteSpace() {

	assertTrue(INVALID_FIELD, parser.getEditTitle(EDIT_TITLE_WHITESPACE_1).equals("newTaskTitle"));
    }

    @Test
    public void testGetEditTitle_EditSpecialChar() {

	assertTrue(INVALID_FIELD, parser.getEditTitle(EDIT_TITLE_SPECIALCHAR_1).equals("$#@ newT@skTitle"));
    }
}
