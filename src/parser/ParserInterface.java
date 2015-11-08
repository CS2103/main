package parser;

import org.joda.time.DateTime;

public interface ParserInterface {

	String getCommand(String input);
	
	/*
	 * Checks if input has a valid Date and Time format with 
	 * the stored Regex in Constants
	 * 
	 * Returns a DateTime object with a given date.
	 */
	
	DateTime getDateTime(String input);
	
	/*
	 * Use getDateTime to return a DateTime object with the given start field
	 */
	
	DateTime getStartDateTime(String input);
	
	/*
	 * Uses getDateTime() to return a DateTime object with the given end field.
	 * and if time field is not given, default time will be set to the end of day (2359)
	 */
	
	DateTime getEndDateTime(String input);
	
	boolean isValidEndingTime(DateTime startTime, DateTime endTime);
	
	/*
	 * Extracts the keyword from the input given,
	 * and returns the following portion after the keyword
	 */
	
	String getTitle(String input);

	int getIndex(String input);
	
	String getField(String input);
	
	String getEditTitle(String input);
	
	String getRecurValue(String input);
	
}
