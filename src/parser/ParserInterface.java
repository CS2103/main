package parser;

import org.joda.time.DateTime;

public interface ParserInterface {
	String getCommand(String input);
	String getTitle(String input);
	String getStart(String input);
	String getEnd(String input);
	String getField(String input);
	DateTime getDateTime(String input);
	DateTime getStartDateTime(String input);
	DateTime getEndDateTime(String input);
	int getIndex(String input);
	boolean isValidEndingTime(DateTime startTime, DateTime endTime);
	String getRecurValue(String input);
}
