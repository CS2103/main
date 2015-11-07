//@@author A0121442X
package parser;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class ParserTester {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Parser parser = new Parser();

		while (true) {
			String rawInput = sc.nextLine();
			System.out.println("Command: " + parser.getCommand(rawInput));
			System.out.println("Title: " + parser.getTitle(rawInput));
			System.out.println("Start: " + parser.getStart(rawInput));
			System.out.println("End: " + parser.getEnd(rawInput));

			System.out.println();
			System.err.println("Start Date: " + parser.getStartDateTime(rawInput));
			System.out.println(DayOfWeek.of(parser.getStartDateTime(rawInput).getDayOfWeek()).getDisplayName(
					TextStyle.FULL, Locale.ENGLISH) + ", " + parser.getStartDateTime(rawInput).getDayOfMonth() + " "
					+ Month.of(parser.getStartDateTime(rawInput).getMonthOfYear()) + " "
					+ parser.getStartDateTime(rawInput).getYear() + " "
					+ parser.getStartDateTime(rawInput).toString().substring(11, 16));

			System.out.println();
			System.err.println("End Date: " + parser.getEndDateTime(rawInput));
			System.out
					.println(DayOfWeek.of(parser.getEndDateTime(rawInput).getDayOfWeek()).getDisplayName(TextStyle.FULL,
							Locale.ENGLISH) + ", " + parser.getEndDateTime(rawInput).getDayOfMonth() + " "
					+ Month.of(parser.getEndDateTime(rawInput).getMonthOfYear()) + " "
					+ parser.getEndDateTime(rawInput).getYear() + " "
					+ parser.getEndDateTime(rawInput).toString().substring(11, 16));
		}
	}
}