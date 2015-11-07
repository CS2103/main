package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest {

	Parser parser = new Parser();

	public static final String ADD_TEST_1 = new String("ADD SHOPPING from 10.12.15 to 14/5/12");
	public static final String ADD_TEST_2 = new String("create buy an apple from 21.5 1245 to 13:52 15-6");

	public static final String ADD_INCONSISTENT_CASE_TEST_1 = new String(
			"cReAtE Buy An apPle frOm 21.5 1245 to 13:52 15-6");
	public static final String ADD_INCONSISTENT_CASE_TEST_2 = new String("AdD SHOPPING from 10.12.15 to 14/5/12");

	public static final String DELETE_TEST_1 = new String("deLEte 3");
	public static final String DELETE_TEST_2 = new String("del 6");

	public static final String EDIT_TEST_1 = new String("edit 1");
	public static final String EDIT_TEST_2 = new String("edt 6");

	public static final String MARK_TEST_1 = new String("mark 6");
	public static final String MARK_TEST_2 = new String("m 2");

	public static final String UNMARK_TEST_1 = new String("unmark 7");
	public static final String UNMARK_TEST_2 = new String("um 27");

	public static final String SEARCH_TEST_1 = new String("search apple");
	public static final String ALPHABET_STRING = new String("");

	public static final String ALPHANUMERIC_STRING_1 = new String("add watch 50 shades of grey");
	public static final String ALPHANUMERIC_STRING_2 = new String("add read pages 12 and 13");

	public static final String ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_1 = new String(
			"add print pages 12,13,24,28 & 30<42 !");
	public static final String ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_2 = new String(
			"add add !@#$%^&*() to buddy list");

	@Test
	public void testGetCommand_Add() {
		assertEquals("add", parser.getCommand(ADD_TEST_1));
		assertEquals("add", parser.getCommand(ADD_TEST_2));
	}

	@Test
	public void testGetCommand_AddInconsistentCase() {
		assertEquals("add", parser.getCommand(ADD_INCONSISTENT_CASE_TEST_1));
		assertEquals("add", parser.getCommand(ADD_INCONSISTENT_CASE_TEST_2));
	}

	@Test
	public void testGetCommand_Delete() {
		assertEquals("delete", parser.getCommand(DELETE_TEST_1));
	}

	@Test
	public void testGetCommand_Edit() {
		assertEquals("edit", parser.getCommand(EDIT_TEST_1));
	}

	@Test
	public void testGetCommand_Mark() {
		assertEquals("mark", parser.getCommand(MARK_TEST_1));
	}

	@Test
	public void testGetCommand_Unmark() {
		assertEquals("unmark", parser.getCommand(UNMARK_TEST_1));
	}

	@Test
	public void testGetTitle_AlphabetString() {
		assertEquals("SHOPPING", parser.getTitle(ADD_TEST_1));
		assertEquals("buy an apple", parser.getTitle(ADD_TEST_2));
		assertEquals("apple", parser.getTitle(SEARCH_TEST_1));
	}

	@Test
	public void testGetTitle_AlphanumericString() {
		assertEquals("watch 50 shades of grey", parser.getTitle(ALPHANUMERIC_STRING_1));
		assertEquals("read pages 12 and 13", parser.getTitle(ALPHANUMERIC_STRING_2));

	}

	@Test
	public void testGetTitle_AlphanumericAndSpecialCharacterString() {
		assertEquals("print pages 12,13,24,28 & 30<42 !", parser.getTitle(ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_1));
		assertEquals("add !@#$%^&*() to buddy list", parser.getTitle(ALPHANUMERIC_AND_SPECIAL_CHARACTER_STRING_2));

	}

	@Test
	public void getIndexTest() {

		int delResult1 = parser.getIndex(DELETE_TEST_1);
		int delResult2 = parser.getIndex(DELETE_TEST_2);

		assertEquals(3, delResult1);
		assertEquals(6, delResult2);
	}

	@Test
	public void getStartStringTest() {
		String startResult1 = parser.getStart(ADD_TEST_1);
		String endResult1 = parser.getEnd(ADD_TEST_1);

		assertEquals("from 10.12.15", startResult1);
		assertEquals("to 14/5/12", endResult1);
	}
}
