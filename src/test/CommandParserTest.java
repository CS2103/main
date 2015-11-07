//@@author A0124127R
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import application.Constants;
import parser.Parser;

public class CommandParserTest {
	
	Parser parser = new Parser();
	
	public static final String INVALID_COMMAND = "Invalid command";
	
	/*
	 * Tests command inputs and their variations(if any),
	 * and checks if the command given is accurate
	 */
	
	@Test
	public void testGetCommand_Add() { 			// "add" and command variations
		
		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
		
	}
	@Test
	public void testGetCommand_Delete() {		// "delete" and command variations

		String input1 = new String("delete task1");
		String input2 = new String("del task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_DELETE);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_DELETE);
		
	}
	@Test
	public void testGetCommand_Edit() {			// "edit" and command variations

		String input1 = new String("edit task1");
		String input2 = new String("e task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_EDIT);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_EDIT);
		
	}
	@Test
	public void testGetCommand_Undo() {			// "undo"

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
		
	}
	@Test
	public void testGetCommand_Redo() {			// "redo"

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}
	@Test
	public void testGetCommand_Mark() {			// "mark" and command variations

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}
	@Test
	public void testGetCommand_Unmark() {		// "unmark" and command variations

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}
	@Test
	public void testGetCommand_EnquirePath() {	// "enquirepath" and command variations

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}
	@Test
	public void testGetCommand_Help() {			// "help"

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}
	@Test
	public void testGetCommand_Show() {			// "show" and command variations

		String input1 = new String("add task1");
		String input2 = new String("create task2");
		
		assertTrue(INVALID_COMMAND, parser.getCommand(input1) == Constants.COMMAND_ADD);
		assertTrue(INVALID_COMMAND, parser.getCommand(input2) == Constants.COMMAND_ADD);
	
	}


}
