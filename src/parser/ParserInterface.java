package parser;

import org.joda.time.DateTime;

public interface ParserInterface {
	String getCommand(String input);
	String getTitle(String input);
	String getStart(String input);
	String getEnd(String input);
	String getField(String input);
	int getIndex(String input);
	DateTime getStartTime(String input);
	DateTime getEndTime(String input);
}
