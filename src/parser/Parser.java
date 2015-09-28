package parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Constants;

public class Parser {

	private static String input;
	private static String command;

	// Constructors
	public Parser() {
		this("");
	}

	public Parser(String input) {
		this.input = input.toLowerCase();
		this.command = "";
	}

	// Mutators
	public static String getInput() {
		return input;
	}

	public String getCommand() {
		return command;
	}

	// Methods
	public static String getCommandName(String input) {
		String[] words;
		words = input.split(" ");
		command = words[0].trim();

		return command;

	}

	public static String getDescription(String input) {

		String parameter = input;

		// from
		Pattern patternFrom = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_FROM]);
		Matcher matchFrom = patternFrom.matcher(getInput());

		while (matchFrom.find()) {
			parameter = parameter.substring(0, matchFrom.end());
		}

		// on
		Pattern patternOn = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_ON]);
		Matcher matchOn = patternOn.matcher(getInput());

		while (matchOn.find()) {
			parameter = parameter.substring(0, matchOn.end());
		}

		// by
		Pattern patternBy = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_BY]);
		Matcher matchBy = patternBy.matcher(getInput());

		while (matchBy.find()) {
			parameter = parameter.substring(0, matchBy.end());
		}

		// till
		Pattern patternTill = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_TILL]);
		Matcher matchTill = patternTill.matcher(getInput());

		while (matchTill.find()) {
			parameter = parameter.substring(0, matchTill.end());
		}

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

	public Date getStartDate(String input) throws ParseException {

		Date startDate;
		String parameter = input;
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);

		// from
		Pattern patternFrom = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_FROM]);
		Matcher matchFrom = patternFrom.matcher(getInput());

		while (matchFrom.find()) {
			parameter = parameter.substring(matchFrom.end());
		}

		startDate = df.parse(parameter);

		return startDate;

	}

	public Date getEndDate(String input) throws ParseException {

		Date endDate;
		String parameter = input;
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK);

		// on
		Pattern patternOn = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_ON]);
		Matcher matchOn = patternOn.matcher(getInput());

		while (matchOn.find()) {
			parameter = parameter.substring(matchOn.end());
		}

		// by
		Pattern patternBy = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_BY]);
		Matcher matchBy = patternBy.matcher(getInput());

		while (matchBy.find()) {
			parameter = parameter.substring(matchBy.end());
		}

		// till
		Pattern patternTill = Pattern.compile(Constants.REGEX_KEYWORDS[Constants.INDEX_KEYWORD_TILL]);
		Matcher matchTill = patternTill.matcher(getInput());

		while (matchTill.find()) {
			parameter = parameter.substring(matchTill.end());
		}

		endDate = df.parse(parameter);

		return endDate;

	}

}
