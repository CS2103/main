package parser;



import java.util.Calendar;
import java.util.Scanner;

public class ParserTester {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String rawInput = sc.nextLine();
		Calendar cal = Calendar.getInstance();

		/*
		 add meet rongxiang from 1000 to 1200
		 add do homework by 1400
		 add eat porridge
		 add go to school on 14 sep
		 */

		String command = CommandParser.getCommand(rawInput);
		System.out.println(command);
		//		System.out.println("Task title: " + TaskParser.getTitle(rawInput));
		//		System.out.println("Start date: " + TaskParser.getStartDate(rawInput).Calendar.get(Calendar.DAY_OF_MONTH));
		//		System.out.println("End date: " + TaskParser.getEndDate(rawInput));
		//		System.out.println("End Time: " + TimeParser.getEndTime(rawInput));
		//		System.out.println("Index: " + TaskParser.getIndex(rawInput));
		//		System.out.println("Attribute: " + TaskParser.getAttribute(rawInput));
		//		System.out.println("Edited title: " + TaskParser.getEditTitle(rawInput));

		sc.close();
	}

}
