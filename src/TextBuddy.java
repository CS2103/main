/*
 * ==============HEADER===============================================
 * ASSUMPTIONS:
 * Assume that if there is a text file with the same file name,
 * the program will open that file instead of creating a new file.
 * Assume that the program with disallow users from adding an empty line.
 * Assume that the line to be deleted is valid.
 *
 * NOTE:
 * This program saves the file after each command (modification) as I
 * have decided that this is the best way to prevent data loss in the
 * event of unexpected termination.
 * ====================================================================
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TextBuddy {	

	private static final String MSG_ADDED = "added to %1$s: \"%2$s\"%n";
	private static final String MSG_CLEARED = "all content deleted from %1$s%n";
	private static final String MSG_DELETED = "deleted from %1$s: \"%2$s\"%n";
	private static final String MSG_EMPTY = "%1$s is empty%n";
	private static final String MSG_EMPTY_INPUT = "cannot add an empty string%n";
	private static final String MSG_FILE_EXISTS = "File named %1$s already exists%n";	
	private static final String MSG_PROMPT = "command: ";
	private static final String MSG_WELCOME = "Welcome to TextBuddy. %1$s is ready for use%n";
	private static final String MSG_FILE_ERROR = "failed to create file: %1$s%n";
	
	private static final String MSG_INVALID_INPUT = "invalid input%n";
	private static final String MSG_INVALID_COMMAND = "invalid command%n";
	private static final String MSG_INVALID_INT = "invalid integer. enter an integer between 1 and %1$d%n";
	
	private static Path path = null;
	private static File file = null;
	private static List<String> lines = new ArrayList<String>();
	
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {		
		handleProgramArguments(args);	
		prepareFile(file);
		handleInputs();
	}
	
	// Process the program arguments for instantiation
	public static void handleProgramArguments(String[] filename) {
		file = new File(filename[0]);
		path = Paths.get(filename[0]);
	}
	
	// Open the text file if it exists and creates a new one if it does not
	public static void prepareFile(File file) {
		try {
			// Create the empty file with default permissions, etc.
			Files.createFile(path);
		} catch (FileAlreadyExistsException x) {
			// Another file exists with the same name, use it instead of creating a new file
			// System.err.format(MSG_FILE_EXISTS, file); // Commented out for consistency in regression testing
		} catch (IOException x) {
			// Some other sort of failure, such as permissions.
			System.err.format(MSG_FILE_ERROR, x);
		}		
		System.out.printf(MSG_WELCOME, path.toString());
	}
	
	// Loop to process user commands and execute the corresponding method, terminates on exit command
	public static void handleInputs()  {
		try {
			Files.newBufferedWriter(path, StandardOpenOption.WRITE);	// Set write
			while (true) {			
				System.out.printf(MSG_PROMPT);		// Prompts for a command
				switch (scanner.next()) {
				case "add" :
					write(scanner.nextLine().trim(), true);
					break;
				case "display" :
					display();
					break;
				case "delete" :
					delete(scanner.nextInt());				
					break;
				case "clear" :
					clear(true);
					break;
				case "exit" :
					exit();
					break;
				default :
					System.out.printf(MSG_INVALID_COMMAND);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.printf(MSG_INVALID_INPUT);			
		} finally {
			scanner.next();
			handleInputs();
		}
	}

	// Clear the text file
	public static void clear(boolean print) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(),false));
		writer.close();
		if (print) {
			System.out.printf(MSG_CLEARED, path.toString());
		}
	}

	// Remove a specified line from the text file
	public static void delete(int line) throws IOException {
		int i = 1;
		String deleted = null;
		lines = Files.readAllLines(path);							// Read all lines to an ArrayList
		if (lines.size() < line || 1 > line) {						// Exception handling:
			System.out.printf(MSG_INVALID_INT, lines.size());		// invalid line number
		} else {
			clear(false);											// Clear the file
			for (String elem : lines) {
				if (i != line) {
					write(elem, false);								// Rewriting the code
				} else {											// minus the deleted line
					deleted = elem;
				}
				i++;
			}
			System.out.printf(MSG_DELETED, path.toString(), deleted);
		}
	}

	// Print all lines of text in the text file to the console
	public static void display() throws IOException {
		int i = 1;													
		lines = Files.readAllLines(path);							// Read all lines to an ArrayList
		if (lines.size() == 0) {
			System.out.printf(MSG_EMPTY, path.toString());			// Exception handling:
		} else {													// empty file
			for (String elem : lines) {
				System.out.println(i++ + ". " + elem);
			}		
		}
	}

	// Add a new line of text to the text file
	public static void write(String stuff, boolean print) throws IOException {
		if (stuff.length() == 0) {
			System.out.printf(MSG_EMPTY_INPUT);
		} else {
			String toAdd = stuff + System.getProperty("line.separator");	// Adds a newline character
			byte data[] = toAdd.getBytes();									// Convert to a byte array for writing
			Files.write(path, data, StandardOpenOption.APPEND);				// Writes the byte array to file
			if (print) {
				System.out.printf(MSG_ADDED, path.toString(), stuff);
			}
		}
	}

	// Terminate the program
	public static void exit() {
		scanner.close();
		System.exit(0);
	}
}