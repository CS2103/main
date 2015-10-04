package parser;



public class Parser {

<<<<<<< HEAD

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

		startDate = sdf.parse(parameter);

		startDate = sdf.parse(parameter.trim());

		startDate = sdf.parse(parameter.trim());
		


		return sdf.format(startDate);

=======
	static String extractFirstWord(String input) {
		input = input.split(" ")[0];
		return input.trim();
	}

	static String excludeFirstWord(String input) {
		input = input.substring(extractFirstWord(input).length());
		return input.trim();
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	}

	public static int getIndex(String input) {
		int index;
		input = input.split(" ")[1].trim();
		index = Integer.parseInt(input);
		return index;
	}
}
