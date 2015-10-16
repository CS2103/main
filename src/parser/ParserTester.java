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

		while(true) {
			String rawInput = sc.nextLine();
			System.out.println("Command: " + parser.getCommand(rawInput));
			System.out.println("Title: " + parser.getTitle(rawInput));
			System.out.println("Start: " + parser.getStart(rawInput));
			System.out.println("End: " + parser.getEnd(rawInput));

			System.out.println();
			System.err.println("Start Date: " + parser.getStartTime(rawInput));
			System.out.println(DayOfWeek.of(parser.getStartTime(rawInput).getDayOfWeek()).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
					+ ", " + parser.getStartTime(rawInput).getDayOfMonth() + " " + Month.of(parser.getStartTime(rawInput).getMonthOfYear())
					+ " " + parser.getStartTime(rawInput).getYear() + " " + parser.getStartTime(rawInput).toString().substring(11, 16));

			System.out.println();
			System.err.println("End Date: " + parser.getEndTime(rawInput));
			System.out.println(DayOfWeek.of(parser.getEndTime(rawInput).getDayOfWeek()).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
					+ ", " + parser.getEndTime(rawInput).getDayOfMonth() + " " + Month.of(parser.getEndTime(rawInput).getMonthOfYear())
					+ " " + parser.getEndTime(rawInput).getYear() + " " + parser.getEndTime(rawInput).toString().substring(11, 16));
		}
	}
}