/**
 * This class:
 * Extracts important parameters from the user's raw input
 * Use flexi-commands
 *
 * int getStartingYear()
 * int getStartingMonth()
 * int getStartingDate()
 * int getEndingDate()
 * int getEndingMonth()
 * int getEndingYear()
 * Date getStartingDate()
 * Date getEndingDate()
 * Time getStartingTime()
 * Time getEndTime()
 * String getDescription()
 * String getTitle()
 * boolean getStatus()
 * String returnCommand()
 *
 * from a String input
 */

package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Constants;

public class Parser {


	public Parser(){

	}

	// Methods
	public String getCommandName(String input) {
		String[] words;
		words = input.split(" ");
		String command = new String(words[0].trim());

		return command;

	}

	public String getDescription(String input) {

		String command = new String(getCommandName(input));
		String parameter = input;

		// from
		Pattern patternFrom = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_FROM]);
		Matcher matchFrom = patternFrom.matcher(input);

		while (matchFrom.find()) {
			parameter = parameter.substring(0, matchFrom.end());
		}
		parameter = parameter.replaceFirst("from", "");

		// on
		Pattern patternOn = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_ON]);
		Matcher matchOn = patternOn.matcher(input);

		while (matchOn.find()) {
			parameter = parameter.substring(0, matchOn.end());
		}
		parameter = parameter.replaceFirst("on", "");

		// by
		Pattern patternBy = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_BY]);
		Matcher matchBy = patternBy.matcher(input);

		while (matchBy.find()) {
			parameter = parameter.substring(0, matchBy.end());
		}
		parameter = parameter.replaceFirst("by", "");

		// till
		Pattern patternTill = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_TILL]);
		Matcher matchTill = patternTill.matcher(input);

		while (matchTill.find()) {
			parameter = parameter.substring(0, matchTill.end());
		}
		parameter = parameter.replaceFirst("till", "");

		parameter = parameter.replaceFirst(command, "");
		parameter = cleanUp(parameter);

		return parameter;

	}

	// public String getKeywordsForSearchCommand(String input) {

	// }

	public static int countWords(String input) {
		input = cleanUp(input);
		return input.split(Constants.SPACE).length;
	}

	public static String cleanUp(String input) {
		input = input.trim();
		input = input.replaceAll(Constants.REGEX_ONE_OR_MORE_SPACES, Constants.SPACE);
		return input;
	}

	public String getStartDate(String input) throws ParseException {

		Date startDate;
		String parameter = input;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		// from
		Pattern patternFrom = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_FROM]);
		Matcher matchFrom = patternFrom.matcher(input);

		while (matchFrom.find()) {
			parameter = parameter.substring(matchFrom.end());
		}

		startDate = sdf.parse(parameter.trim());

		return sdf.format(startDate);

	}

	public String getEndDate(String input) throws ParseException {

		Date endDate;
		String parameter = input;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		// on
		Pattern patternOn = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_ON]);
		Matcher matchOn = patternOn.matcher(input);

		while (matchOn.find()) {
			parameter = parameter.substring(matchOn.end());
		}

		// by
		Pattern patternBy = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_BY]);
		Matcher matchBy = patternBy.matcher(input);

		while (matchBy.find()) {
			parameter = parameter.substring(matchBy.end());
		}

		// till
		Pattern patternTill = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_TILL]);
		Matcher matchTill = patternTill.matcher(input);

		while (matchTill.find()) {
			parameter = parameter.substring(matchTill.end());
		}

		endDate = sdf.parse(parameter);

		return sdf.format(endDate);

	}

}