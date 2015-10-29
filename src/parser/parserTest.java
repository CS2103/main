package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class parserTest {
	
	Parser parser = new Parser();
	
	public static final String ADD_TEST_1 = new String("ADD SHOPPING from 10.12.15 to 14/5/12");
	public static final String ADD_TEST_2 = new String("create buy an apple from 21.5 1245 to 13:52 15-6");
	public static final String DELETE_TEST_1 = new String("deLEte 3");
	public static final String DELETE_TEST_2 = new String("del 6");
	public static final String SEARCH_TEST_1 = new String("search apple");

	@Test
	public void getCommandtest() {
		
		String addResult1 = parser.getCommand(ADD_TEST_1);
		String addResult2 = parser.getCommand(ADD_TEST_2);
		String delResult1 = parser.getCommand(DELETE_TEST_1);
		String delResult2 = parser.getCommand(DELETE_TEST_2);
		String searchResult1 = parser.getCommand(SEARCH_TEST_1);
		
		assertEquals("add", addResult1);
		assertEquals("add", addResult2);
		assertEquals("delete", delResult1);
		assertEquals("delete", delResult2);
		assertEquals("search", searchResult1);
		
	}
	
	@Test
	public void getTitleTest() {
		
		String addResult1 = parser.getTitle(ADD_TEST_1);
		String addResult2 = parser.getTitle(ADD_TEST_2);
		String searchResult1 = parser.getTitle(SEARCH_TEST_1);
		
		assertEquals("SHOPPING", addResult1);
		assertEquals("buy an apple", addResult2);
		assertEquals("apple", searchResult1);
		
	}
	
	@Test
	public void getIndexTest() {
		
		int delResult1 = parser.getIndex(DELETE_TEST_1);
		int delResult2 = parser.getIndex(DELETE_TEST_2);
		
		assertEquals(3, delResult1);
		assertEquals(6, delResult2);
		
	}
	
	@Test
	public void getStartEndStringTest() {
		String startResult1 = parser.getStart(ADD_TEST_1);
		String endResult1 = parser.getEnd(ADD_TEST_1);
		
		assertEquals("10.12.15", startResult1);
		assertEquals("14/5/12", endResult1);
		
	}

}
