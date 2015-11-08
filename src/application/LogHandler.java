//@@author A0121442X
package application;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHandler {
	Handler consoleHandler = new ConsoleHandler();
	private static final String FILE_PATH = System.getProperty("user.dir").toString() + "/log/";
	private static final String LOGGER_FILE = FILE_PATH + "TBALog.log";
	private static Logger logger = Logger.getLogger("Log");
	private static Handler fileHandler;

	public LogHandler() {
		File logFile = new File(FILE_PATH);
		if (logFile.exists()) {
			return;
		} else {
			logFile.mkdir();
		}

		SimpleFormatter loggerFormatter = new SimpleFormatter();

		try {
			fileHandler = new FileHandler(LOGGER_FILE);
			fileHandler.setFormatter(loggerFormatter);
			consoleHandler.setFormatter(loggerFormatter);
			logger.addHandler(fileHandler);
			logger.addHandler(consoleHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Logger error");
		}
		logger.isLoggable(Level.INFO);
	}

	public void log(Level level, String message) {
		logger.log(level, message);
	}
}