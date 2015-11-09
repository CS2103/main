package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.InvalidTimeException;
import logic.Logic;
import logic.TaskBin;

public class IntegrationTest {

	private Logic logic = new Logic();
	private TaskBin taskBin = new TaskBin();

	@Before
	public void setUpEnvironment() {

	}

	@After
	public void restoreEnvironment() throws ParseException, InvalidTimeException {
		logic.inputHandler("undo");
	}

	@Test
	public void testInputHandler_showAllTasks() {
		try {
			assertTrue("", logic.inputHandler("show").size() > 1);
		} catch (ParseException | InvalidTimeException e) {
			fail("");
		}
	}

	@Test
	public void testInputHandler_addFloatingTask() {
		int currentArrayListSize = logic.displayHome().size();
		try {
			assertEquals("", currentArrayListSize + 1, logic.inputHandler("add fLoAtInGtAsK").size());
		} catch (ParseException | InvalidTimeException e) {
			fail("");
		}
	}

	@Test
	public void testInputHandler_deleteTask() throws ParseException, InvalidTimeException {
		int currentArrayListSize = logic.displayHome().size();
		if (currentArrayListSize == 0) {
			logic.inputHandler("add fLoAtInGtAsK");
			assertEquals("", 0, logic.inputHandler("delete 1").size());
		} else {
			assertEquals("", currentArrayListSize - 1, logic.inputHandler("delete 1").size());
		}
	}

}
