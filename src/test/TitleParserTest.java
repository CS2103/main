package test;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.TitleParser;

public class TitleParserTest {

	
	private static final String INVALID_TITLE = "Invalid Title";
	private static final String VALID_TITLE = "Valid Title";
	private static final String NULL_RETURN = null;
	
	@Test
	public void testGetTitle_TitleBoundary1() { // Title has no interference with regex for dates
		
		String input1 = new String("add title123 from 10 nov to 11 nov");
		String input2 = new String("find 321title");
		
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input1).equals("title123"));
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input2).equals("321title"));

	}
	
	@Test
	public void testGetTitle_TitleBoundary2() { // Title contains keyword "from/to", but no interference with regex for dates
		
		String input1 = new String("add take train from orchard to city hall from 10 nov to 11 nov");
		
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input1).equals("take train from orchard to city hall"));
	}

	/*
	 * Invalid as "from 7" is valid under Constants.TASK_START_DATETIME
	 */
	
	@Test
	public void testGetTitle_TitleBoundary3() { // Title contains keyword "from/to", WITH interference with regex for dates
		
		String input1 = new String("add take bus from 7-11 from 10 nov to 11 nov");
		
		assertFalse(VALID_TITLE, TitleParser.getTitle(input1).equals("take bus from 7-11"));
	}
	
	@Test
	public void testGetTitle_TitleBoundary4() { // Title contains no date fields
		
		String input1 = new String("add take bus");
		
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input1).equals("take bus"));
	}

	@Test
	public void testGetTitle_TitleBoundary5() { // Title is an empty string
		
		String input1 = new String("add");
		
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input1).isEmpty());
	}
	
	/*
	 * Returns null as command is invalid to run.
	 */
	
	@Test
	public void testGetTitle_InvalidCommand() { // Command is an invalid input
		
		String input1 = new String("produce title456 from 10 nov to 12 nov");
		
		assertTrue(NULL_RETURN, TitleParser.getTitle(input1) == null);
	}
	
	@Test
	public void testGetTitle_SpecialCharacters() { // Title has special characters
		
		String input1 = new String("add +^$[]+! \\s from 10 nov to 12 nov");
		
		assertTrue(INVALID_TITLE, TitleParser.getTitle(input1).equals("+^$[]+! \\s"));
	}
	
	@Test
	public void testGetEditTitle_NormalText() { // Title is valid for edit command
		
		String input1 = new String("edit 1 title title3245");
		
		assertTrue(INVALID_TITLE, TitleParser.getEditTitle(input1).equals("title3245"));
	}
	
	@Test
	public void testGetEditTitle_NormalText() { // Title is valid for edit command
		
		String input1 = new String("edit 1 title title3245");
		
		assertTrue(INVALID_TITLE, TitleParser.getEditTitle(input1).equals("title3245"));
	}
}
