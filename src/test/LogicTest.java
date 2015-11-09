//@@author A0129708X
package test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Test;

import logic.InvalidTimeException;
import logic.Logic;
import logic.Task;

public class LogicTest {

    private static String[] command1 = { "add have dinner with James from 1200 to 1300", "add buy flower for Sherry",
	    "add write report by 10/11", "add hand in CS2013 report by tmr",
	    "add CS2103 tutorial from 1000 to 1100 weekly", "add take MRT to Jurong by 1800", "mark 1", "del 2" };
    private static String[] command2 = { "edit 1 time from 1200 tmr to 1300 tmr", "show tmr", "m 1", "undo" };

    @Test
    public void testInputHandler() throws ParseException, InvalidTimeException {
	Logic testLogic = new Logic();
	testLogic.clear();
	ArrayList<Task> output = new ArrayList<Task>();
	String outputString, refString;
	for (String s : command1) {
	    output = testLogic.inputHandler(s);
	}
	outputString = output.toString();
	refString = new String(
		"[Title: hand in CS2013 report\nStatus: false\n, Title: take MRT to Jurong\nStatus: false\n, Title: CS2103 tutorial\nStatus: false\n, Title: buy flower for Sherry\nStatus: false\n]");
	System.out.println(refString);
	System.out.println(outputString);
	assertTrue(refString.equals(outputString));
	for (String s : command2) {
	    output = testLogic.inputHandler(s);
	}
	outputString = output.toString();
	refString = new String("[Title: hand in CS2013 report\nStatus: false\n]");
	System.out.println(refString);
	System.out.println(outputString);
	assertTrue(refString.equals(outputString));
    }

}
