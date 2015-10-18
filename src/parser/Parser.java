package parser;

import org.joda.time.DateTime;

import application.Constants;

public class Parser implements ParserInterface {

	@Override
	public String getCommand(String input) {
		return CommandParser.getCommand(input);
	}

	@Override
	public String getStart(String input) {
		return TitleParser.splitInputWithDictionary(Constants.TASK_START_DATETIME, input);
	}

	@Override
	public String getEnd(String input) {
		return TitleParser.splitInputWithDictionary(Constants.TASK_END_DATETIME, input);
	}

	@Override
	public String getField(String input) {
		return input.split(Constants.SPACE)[2].trim().toLowerCase();
	}

	@Override
	public DateTime getStartDateTime(String input) {
		return DateParser.getDateTime(getStart(input));
	}

	@Override
	public DateTime getEndDateTime(String input) {
		DateTime endDate = DateParser.getDateTime(getEnd(input));
		
		if (endDate.getHourOfDay() == 0 && endDate.getMinuteOfHour() == 0)
			endDate = endDate.withHourOfDay(23).withMinuteOfHour(59);
		
		return endDate;
		
	}

	@Override
	public String getTitle(String input) {
		return TitleParser.getTitle(input);
	}
	
	@Override
	public int getIndex(String input) {
		input = input.split(Constants.SPACE)[1].trim();
		if (input.equalsIgnoreCase(Constants.SHOW_ALL)) {
			return -1;
		} else {
			return Integer.parseInt(input);
		}
	}

	@Override
	public Boolean isValidEndingTime(DateTime startTime, DateTime endTime) {
		return startTime.isBefore(endTime);
	}
	
}