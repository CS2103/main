package application;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {
	Handler consoleHandler = new ConsoleHandler();

	public LogHandler() {
		Logger.getAnonymousLogger().addHandler(consoleHandler);
		Logger.getAnonymousLogger().setLevel(Level.FINEST);
	}
}
