//@@author A0124127R
package parser;

import java.util.ArrayList;

import org.joda.time.DateTime;

import application.Constants;

public class Parser implements ParserInterface {

	@Override
	public String getCommand(String input) {
		return CommandParser.getCommand(input);
	}

	@Override
	public DateTime getDateTime(String input) {
		return DateParser.getDateTime(input);
	}

	@Override
	public DateTime getStartDateTime(String input) {
		return DateParser.getDateTime(getStart(input));
	}

	@Override
	public DateTime getEndDateTime(String input) {
		DateTime endDate = DateParser.getDateTime(getEnd(input));

		if (endDate.getHourOfDay() == 0 && endDate.getMinuteOfHour() == 0) {
			endDate = endDate.withHourOfDay(23).withMinuteOfHour(59);
		}

		return endDate;

	}
	
	@Override
	public boolean isValidEndingTime(DateTime startTime, DateTime endTime) {
		return startTime.isBefore(endTime);
	}

	@Override
	public String getTitle(String input) {
		return TitleParser.getTitle(input);
	}

	// Returns the second non-space character of an EDIT input == the index of
	// tasks
	@Override
	public int getIndex(String input) {

		input = TitleParser.extractFirstWord(TitleParser.excludeFirstWord(input.trim()));
		return Integer.parseInt(input);
	}

	// Returns the third non-space String of an EDIT input == field to change
	@Override
	public String getField(String input) {
		for (int i = 0; i < 2; i++) {
			input = TitleParser.excludeFirstWord(input.trim());
		}
		return TitleParser.extractFirstWord(input);
	}
	
	@Override
	public String getEditTitle(String input) {
		return TitleParser.getEditTitle(input);
	}
	
	public String getRecurValue(String input) {
		return DateParser.getRecurValue(input);
	}

	public ArrayList<Integer> getIndexes(String input) {
		ArrayList<Integer> indexArray = new ArrayList<Integer>();
		input = input.split(Constants.SPACE)[1].trim();
		if (input.length() == 1) {
			indexArray.add(Integer.parseInt(input));
			return indexArray;
		} else {
			String[] indexStringArray = input.split(Constants.COMMA);
			for (String indexString : indexStringArray) {
				indexArray.add(Integer.parseInt(indexString));
			}
			return indexArray;
		}
	}

	private String getStart(String input) {
		return TitleParser.splitInputWithDictionary(Constants.TASK_START_DATETIME, input);
	}

	private String getEnd(String input) {
		return TitleParser.splitInputWithDictionary(Constants.TASK_END_DATETIME, input);
	}
	

}