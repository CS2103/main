package parser;



import java.util.Scanner;

public class ParserTester {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String rawInput = sc.nextLine();

		/*
		 add meet rongxiang from 1000 to 1200
		 add do homework by 1400
		 add eat porridge
		 add go to school on 14 sep
		 */

		String command = CommandParser.getCommand(rawInput);
		System.out.println(command);
		System.out.println("Task title: " + TaskParser.getTitle(rawInput));
		System.out.println("Start date: " + TaskParser.getStartDate(rawInput));
		System.out.println("End date: " + TaskParser.getEndDate(rawInput));

		sc.close();
	}

}
