package application;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Constants {

	public enum COMMAND_TYPE { ADD, DELETE, SEARCH, EDIT, MARK, UNMARK, UNDO, REDO, SETPATH, INVALID };
	
	public static final String APP_NAME = "TBA";

	public static final String WELCOME_MESSAGE = "Welcome to TaskBuddyAwesome. \n"
			+ "To view available commands type HELP and hit enter.\n";
	// Limit for number of days before current day for which task without year
	// will be entered as overdue task
	public static final int DAYS_OFFSET = 7;

	public static final String LABEL_TITLE = "Title: ";
	public static final String LABEL_LOCATION = "Location: ";
	public static final String LABEL_START_TIME = "Start Time: ";
	public static final String LABEL_START = "Start: ";
	public static final String LABEL_END = "End: ";
	public static final String LABEL_END_TIME = "End Time: ";
	public static final String LABEL_START_DATE = "Start Date: ";
	public static final String LABEL_END_DATE = "Due Date: ";
	public static final String LABEL_PRIORITY = "Priority: ";

	public static final int REFRESH_TIME = 60 * 1000; // refers to the time in

	// Separators
	public static final String SEPARATOR = "\\\\";
	public static final String SEPARATOR_SLASH = "/";
	public static final String SEPARATOR_DOT = "\\.";
	public static final String SEPARATOR_TO = " to ";
	public static final String SEPARATOR_DASH = "-";

	// double quote
	public static final String DOUBLE_QUOTE = "\"";

	//dot
	public static final String DOT = ".";

	// Dictionaries
	public static final String[] DICTIONARY_ADD = { "add", "a ", "create" };
	public static final String[] DICTIONARY_DELETE = { "delete", "rm", "del" };
	public static final String[] DICTIONARY_SHOW = { "show", "display", "sh" };
	public static final String[] DICTIONARY_SEARCH = { "search", "find", "sr" };
	public static final String[] DICTIONARY_EDIT = { "edit", "change", "e", "modify" };
	public static final String[] DICTIONARY_MARK = {"mark", "m"};
	public static final String[] DICTIONARY_UNMARK = {"unmark", "um"};
	public static final String[] DICTIONARY_UNDO = { "undo", "un" };
	public static final String[] DICTIONARY_REDO = { "redo", "re" };
	public static final String[] DICTIONARY_VIEW_HOMESCREEN = { "home", "h" };
	public static final String[] DICTIONARY_SETPATH = {"setpath", "set"};
	public static final String[] DICTIONARY_HELP = { "help" };
	public static final String[] DICTIONARY_EXIT = { "exit", "ex", "quit" };
	public static final String[] DICTIONARY_PARAMETERS = { "location", "loc",
			"date", "startdate", "enddate", "sd", "ed", "time", "starttime",
			"endtime", "st", "et" , "from", "to"};

	// Months
	public static final String[] MONTHS_SHORT = { "jan", "feb", "mar", "apr",
			"may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
	public static final String[] MONTHS_LONG = { "january", "february",
			"march", "april", "may", "june", "july", "august", "september",
			"october", "november", "december" };
	public static final String[] MONTHS_1ST_LETTER_CAPS_SHORT = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec" };
	public static final String[] MONTHS_1ST_LETTER_CAPS_LONG = { "January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November",
	"December" };

	// Day Keywords
	public static final String[] DICTIONARY_EVERY_DAY = {"every day"};
	public static final String[] DICTIONARY_MON = {"mon", "monday"};
	public static final String[] DICTIONARY__TUE = {"tue", "tues", "tuesday"};
	public static final String[] DICTIONARY_WED = {"wed", "weds", "wednesday"};
	public static final String[] DICTIONARY_THU = {"thur", "thurs", "thursday"};
	public static final String[] DICTIONARY_FRI = {"fri", "friday"};
	public static final String[] DICTIONARY_SAT = {"sat", "saturday"};
	public static final String[] DICTIONARY_SUN = {"sun", "sunday"};
	public static final String[] DICTIONARY_TOMORROW = {"tomorrow", "tmr"};
	public static final String[] DICTIONARY_TODAY = {"today", "tdy"};

	// Commands
	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_SHOW = "show";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_UNDO = "undo";
	public static final String COMMAND_REDO = "redo";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_UNMARK = "unmark";
	public static final String COMMAND_VIEW_HOMESCREEN = "home";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_INVALID = "invalid command";
	public static final String COMMAND_ALERT = "alert";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_SETPATH = "setpath";


	// Array indices
	public static final int INDEX_DAY = 0;
	public static final int INDEX_TODAY = 0;
	public static final int INDEX_MONTH = 1;
	public static final int INDEX_YEAR = 2;
	public static final int INDEX_BEGIN = 0;
	public static final int INDEX_HOUR = 0;
	public static final int INDEX_MINUTE = 1;
	public static final int INDEX_START_TIME = 0;
	public static final int INDEX_END_TIME = 1;
	public static final int INDEX_START_DATE = 0;
	public static final int INDEX_END_DATE = 1;
	public static final int INDEX_SEPARATOR = 0;
	public static final int INDEX_1ST_WORD = 0;
	public static final int INDEX_2ND_WORD = 0;
	public static final int INDEX_3RD_WORD = 1;
	public static final int INDEX_4TH_WORD = 2;
	public static final int INDEX_5TH_WORD = 3;
	public static final int INDEX_1ST_LETTER = 0;
	public static final int INDEX_2ND_LETTER = 1;
	public static final int INDEX_2ND_PART = 1;
	public static final int INDEX_3RD_PART = 2;
	public static final int INDEX_START = 0;
	public static final int INDEX_END = 1;

	// year
	public static final int YEAR_2000 = 2000;

	// number of milliseconds in a day
	public static final int MILLISECONDS_IN_A_DAY = 1000 * 60 * 60 * 24;

	// parameters
	public static final String PARAMETER_TITLE = "title";
	public static final String PARAMETER_START = "start";
	public static final String PARAMETER_END = "end";
	public static final String PARAMETER_START_DATE = "startdate";
	public static final String PARAMETER_END_DATE = "enddate";
	public static final String PARAMETER_START_TIME = "starttime";
	public static final String PARAMETER_END_TIME = "endtime";
	public static final String PARAMETER_ST = "st";
	public static final String PARAMETER_ET = "et";


	// Array/String lengths
	public static final int LENGTH_DAY_MONTH_YEAR = 3;
	public static final int LENGTH_DAY_MONTH = 2;
	public static final int LENGTH_AM_PM = 2;
	public static final int LENGTH_HOUR = 1;
	public static final int LENGTH_HOUR_MINUTE = 2;
	public static final int LENGTH_24HOUR_FORMAT = 4;
	public static final int LENGTH_TIME_RANGE = 2;

	// time strings
	public static final String STRING_AM = "am";
	public static final String STRING_PM = "pm";
	public static final String STRING_2400 = "2400";
	public static final String STRING_0000 = "0000";

	// number of days in a week
	public static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;

	// 2 digits
	public static final String FORMAT_2_DIGITS = "00";

	// limits
	public static final int LIMIT_ZERO = 0;
	public static final int LIMIT_MIN_2DIGIT_YEAR = 10;
	public static final int LIMIT_MAX_2DIGIT_YEAR = 99;
	public static final int LIMIT_MIN_4DIGIT_YEAR = 2010;
	public static final int LIMIT_MAX_4DIGIT_YEAR = 2099;
	public static final int LIMIT_MIN_MINUTE = 0;
	public static final int LIMIT_MAX_MINUTE = 59;
	public static final int LIMIT_MIN_HR = 1;
	public static final int LIMIT_MAX_12HR = 12;
	public static final int LIMIT_MAX_24HR = 23;

	// Feedback Strings
	public static final String QUOTE = "\"";
	// public static final String LINEBREAK =
	// System.getProperty("line.separator");
	public static final String LINEBREAK = "\n";
	public static final String FEEDBACK_ADD_SUCCESS = " added successfully!";
	public static final String FEEDBACK_DEL_SUCCESS = " deleted successfully!";
	public static final String FEEDBACK_MASS_DEL_SUCCESS = "Mass delete performed successfully!";
	public static final String FEEDBACK_MASS_DEL_FAILURE = "Invalid task ID specified!";
	public static final String FEEDBACK_EDIT_SUCCESS = " was edited succesfully!";
	public static final String FEEDBACK_SHOW_SUCCESS = "Displaying search results for: ";
	public static final String FEEDBACK_SHOW_HITS_SINGLE = " task found!";
	public static final String FEEDBACK_SHOW_HITS_MULTI = " tasks found!";
	public static final String FEEDBACK_SHOW_FAILURE = "There are no tasks due on that day!";
	public static final String FEEDBACK_SHOW_OVERDUE_SUCCESS = "<html>Displaying overdue tasks!";
	public static final String FEEDBACK_SHOW_OVERDUE_FAILURE = "No overdue tasks were found!";
	public static final String FEEDBACK_SHOW_ARCHIVE_SUCCESS = "Displaying tasks in archive!";
	public static final String FEEDBACK_SHOW_ARCHIVE_FAILURE = "There are no tasks in your archive!";
	public static final String FEEDBACK_SHOW_WEEK_SUCCESS = "Displaying agenda for next 7 days!";
	public static final String FEEDBACK_SHOW_WEEK_FAILURE = "No tasks due in the next 7 days!";
	public static final String FEEDBACK_SHOW_MONTH_SUCCESS = "Displaying agenda for ";
	public static final String FEEDBACK_SHOW_MONTH_FAILURE = "No tasks due in ";
	public static final String FEEDBACK_SHOW_ALL_SUCCESS = "Displaying all tasks found in database!";
	public static final String FEEDBACK_SHOW_ALL_FAILURE = "No tasks found in database!";
	public static final String FEEDBACK_SEARCH_SUCCESS_SINGLE = "Search results for tasks with the keyword: ";
	public static final String FEEDBACK_SEARCH_SUCCESS_MULTI = "Search results for tasks with the keywords: ";
	public static final String FEEDBACK_SEARCH_FAILURE = "SEARCH FAILED";
	public static final String FEEDBACK_SEARCH_FAILURE_SINGLE = "No tasks were found with the keyword: ";
	public static final String FEEDBACK_SEARCH_FAILURE_MULTI = "No tasks were found with the keywords: ";
	public static final String FEEDBACK_DONE_SUCCESS = " has been completed!";
	public static final String FEEDBACK_DONE_FAILURE = "That task has already been completed!";
	public static final String FEEDBACK_MASS_DONE_SUCCESS = "Specified tasks have been marked as done!";
	public static final String FEEDBACK_MASS_DONE_FAILURE = "Invalid task ID specified!";
	public static final String FEEDBACK_NOT_DONE_SUCCESS = " is now pending completion!";
	public static final String FEEDBACK_NOT_DONE_FAILURE = "That task is already pending completion!";
	public static final String FEEDBACK_MASS_NOT_DONE_SUCCESS = "Specified tasks have been marked as pending!";
	public static final String FEEDBACK_MASS_NOT_DONE_FAILURE = "Invalid task ID specified!";
	public static final String FEEDBACK_UNDO_SUCCESS = "Last action has been undone!";
	public static final String FEEDBACK_UNDO_FAILURE = "Nothing to undo!";
	public static final String FEEDBACK_REDO_SUCCESS = "Redone!";
	public static final String FEEDBACK_REDO_FAILURE = "Nothing to redo!";
	public static final String FEEDBACK_VIEW_HOMESCREEN = "Displaying Home-Screen!";
	public static final String FEEDBACK_VIEW_TODAY = "Displaying agenda for this week";
	public static final String FEEDBACK_INVALID = "That is an invalid action!";
	public static final String FEEDBACK_INVALID_NUMBER_OF_QUOTES = "Invaild number of quotes";
	public static final String FEEDBACK_INVALID_PARAMETERS_FOR_EDIT = "Invalid parameters for edit command";
	public static final String FEEDBACK_SETPATH_SUCCESS= "New save location is: ";

	// Switch-case
	public static final int EMPTY_LIST = 0;
	public static final int LIST_SIZE_ONE = 1;
	public static final int WORD_COUNT_ONE = 1;

	// date formats
	public static String[] dateFormats = {"dd MMM yy", "d/M/yy", "d/M", "d-M-yy", "d MMM", "dd MMMM", "dd.MM.yy",
			"d-M", "dd.M"};

	// time formats
	public static String[] timeFormats = {"HHmm", "H mm", "HH:mm", "HH", "HH.mm", "H.mm", "H:mm", "H"};

	// date-time formats
	public static String[] dateTimeFormats = {"d MMM yyyy HHmm", "d/M/yy", "d/M", "d-M-y", "d MMM", "d MMMM", "dd.MM.yyyy",
			"d-M", "dd.M"};


	public static String[] dateRegex = {
			"(3[0-1]|2[0-9]|1[0-9]|[1-9])[\\s]\\D+[\\s][0-9]{2}(\\s|$)",				// 10 dec+ 2015
			"(3[0-1]|2[0-9]|1[0-9]|[1-9])[.|/|-](1[0-2]|[1-9])[.|/|-]\\d{1,4}",	// 10.12.2015 OR 10/12/2015 OR 10-12-2015
			"(3[0-1]|2[0-9]|1[0-9]|[1-9])[\\s]\\D+",							// 10 dec+
			"(3[0-1]|2[0-9]|(1[0-9])|[1-9])[.|/|-]((1[0-2]|[1-9]))"				// 10.12 OR 10/12
			//			"\b(0?[1-9]|[1-2][0-9]|3[0-1])[- /.](0?[1-9]|1[0-2])[- /.](19|20)?[0-9]{2}\b"
	};
	public static String[] timeRegex = {
			"[(2[0-3]|1[0-9]|[0-9])]{2}[[1-5]?[0-9]]{2}",						// 2345
			"(2[0-3]|1[0-9]|[0-9]):([1-5]?[0-9])"//,							// 23:45
			//"\\d{1,2}[^/][0-5]{1,2}\\d"
	};

	// show parameters (LOWERCASE)
	public static final String SHOW_OVERDUE = "overdue";
	public static final String SHOW_ARCHIVE = "archive";
	public static final String SHOW_WEEK = "week";
	public static final String SHOW_MONTH = "month";
	public static final String SHOW_ALL = "all";

	public static final int ONE_WEEK = 8;

	// not found
	public static int NOT_FOUND = -1;

	// keywords
	//public static final String[] TASK_START_DATE = {};
	//public static final String[] TASK_END_DATE = {};

	public static final String[] TASK_START_DATETIME = {" from ", " on ", " between "};
	public static final String[] TASK_END_DATETIME = {" by ", " until ", " till ", " before ", " to "};
	public static final String[] TASK_RECURRING = {"daily", "weekly", "monthly", "yearly"};

	//public static final String[] TASK_START = {"from"};
	//public static final String[] TASK_END = {"to"};


	// index of keywords
	public static final int INDEX_KEYWORD_AT = 0;
	public static final int INDEX_KEYWORD_ON = 1;
	public static final int INDEX_KEYWORD_BY = 2;
	public static final int INDEX_KEYWORD_FROM = 3;
	public static final int INDEX_KEYWORD_TILL = 4;

	// regex
	public static final String REGEX_ONE_OR_MORE_SPACES = "\\s+";
	public static final String REGEX_ZERO_OR_MORE_SPACES = "\\s*";
	public static final String REGEX_WORD_START = "\\b";
	public static final String REGEX_NON_DIGIT = "\\D";
	public static final String REGEX_WORD_END = "\\b";
	public static final String REGEX_AT_WITH_SPACES = "\\bat\\b\\s*";
	public static final String REGEX_AT_SPACE_WORD_START = "\\bat\\b\\s\\b";
	public static final String REGEX_WORD_END_1SPACE_WORD_START = "\\b\\s\\b";
	public static final String REGEX_WORD_END_SPACES_WORD_START = "\\b\\s*\\b";
	public static final String[] REGEX_KEYWORDS = { "\\bat\\b", "\\bon\\b", "\\bby\\b", "\\bfrom\\b", "\\btill\\b" };

	// space
	public static final String SPACE = " ";
	public static final char SPACE_CHAR = ' ';

	// comma
	public static final String COMMA = ",";

	// empty string
	public static final String EMPTY_STRING = "";

	// strings
	public static final String STRING_ZERO = "0";
	public static final String STRING_M_LOWERCASE = "m";

	// logging
	public static final String LOGGER_PARSER = "TBAParser";
	public static final String LOGGER_FILE_NAME = "C:\\ParserLogFile%g.txt";

	// error messages
	public static final String ERROR_MULTIPLE_LOCATION = "Error! More than one location.";
	public static final String ERROR_PROCESSING = "processing error";

	// below are all constants used in GUI
	final static Color DARK_ORANGE = new Color(253, 101, 0);
	final static Color PURPLE = new Color(204, 0, 204);
	final static Color BG = new Color(0, 129, 72);
	final static Color DARK_BLUE = new Color(3, 97, 148);
	final static Color MEDIUM_BLUE = new Color(82, 161, 204);

	final static Font TREBUCHET_14 = Font.font("Trebuchet MS", 14);
	final static Font TREBUCHET_BOLD_14 = Font.font("Trebuchet MS", FontWeight.BOLD, 14);
	final static Font TREBUCHET_BOLD_16 = Font.font("Trebuchet MS", FontWeight.BOLD, 16);
	final static Font TREBUCHET_16 = Font.font("Trebuchet MS", 16);
	final static Font CALIBRI_14 = Font.font("Calibri", 14);
	public final static Font CALIBRI_BOLD_14 = Font.font("Calibri", FontWeight.BOLD, 14);
	public final static Font CALIBRI_BOLD_16 = Font.font("Calibri", FontWeight.BOLD, 16);
	final static Font CALIBRI_16 = Font.font("Calibri", 16);
	final static Font TAHOMA_14 = Font.font("Tahoma", 14);

	final static String TODAY = "Today";
	final static String PROGRESS_BAR = "Progress Bar";

	final static String HEADER_OVERDUE = "Overdue Tasks";
	final static String HEADER_FLOATING = "Tasks With No date \n";
	final static String HEADER_UPCOMING = "Upcoming Tasks";

	final static String FORMAT_HEADER_OVERDUE = "overdue header";
	final static String FORMAT_HEADER_FLOATING = "floating header";
	final static String FORMAT_HEADER_NORMAL = "normal header";
	final static String FORMAT_HEADER_UPCOMING = "upcoming header";
	final static String FORMAT_TIME = "time";
	final static String FORMAT_DESCRIPTION = "description";
	final static String FORMAT_LOCATION = "location";
	final static String FORMAT_OVERDUE = "overdue";
	final static String FORMAT_NONE = "null";
	final static String FORMAT_TICK = "tick mark";
	final static String FORMAT_HEADER_DATE = "date header";
	final static String FORMAT_NUMBER = "numbering";
	final static String FORMAT_DONE = "done";
	final static String FORMAT_IS_JUST_ADDED = "is just added";

	final static String BULLET_TIME = "      \u25D5";
	final static String BULLET_LOCATION = "      @";
	final static String GREEN_TICK = "   \u2713";

	final static Border DISPLAY_PANEL_FOCUS_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createLineBorder(Color.BLACK));
	final static Border INPUT_TF_FOCUS_BORDER = BorderFactory.createLoweredBevelBorder();
	final static Border ALERT_BUTTON_FOCUS_BORDER = BorderFactory.createRaisedBevelBorder();

	final static int DISPLAY_IN_TP1 = 1; //upcoming TP
	final static int DISPLAY_IN_TP2 = 2; //no date TP
	final static int DISPLAY_IN_TP3 = 3; //overdue TP

	final static boolean IS_NEED_HEADER = true;

	final static int IMPOSSIBLE_ARRAYLIST_INDEX = -1;

	// Tray icon display messages
	final static String MSG_ALREADY_RUNNING = APP_NAME + " is Already Running!" ;
	final static String MSG_RESTORE = "CTRL + L to Restore";
	final static String MSG_ERROR = "Error occured";
	public static final String MSG_STILL_RUNNING = APP_NAME + " is still running. You can access it here.";
	public final static String MSG_ABOUT = APP_NAME + " 2015 \nCreated by Andy, Hung, Jun Ren, Xiaoming";
	public final static String MENU_ITEM_SHOW = "Show";
	public final static String MENU_ITEM_ABOUT = "About";
	public final static String MENU_ITEM_EXIT = "Exit";
	
}
