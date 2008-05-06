package org.jdmp.matrix.util;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class JDMPSignalHandler implements SignalHandler {

	private static final Logger logger = Logger
			.getLogger(JDMPSignalHandler.class.getName());

	private SignalHandler oldHandler = null;

	/**
	 * Registers handlers for the signals SIGCONT (18), SIGCHLD (17) and SIGUSR2
	 * (12). When one of these signals is sent to the application, the graphical
	 * user interface will be displayed.
	 */
	static {
		try {

			if ("Linux".equals(System.getProperty("os.name"))) {
				// Linux SEGV, ILL, FPE, BUS, SYS, CPU, FSZ, ABRT, INT, TERM,
				// HUP, USR1, QUIT, BREAK, TRAP, PIPE
				JDMPSignalHandler.install("USR2"); // 12
				JDMPSignalHandler.install("CHLD"); // 17
				JDMPSignalHandler.install("CONT"); // 18
			} else {
				// Windows SEGV, ILL, FPE, ABRT, INT, TERM, BREAK
				JDMPSignalHandler.install("SEGV");
				JDMPSignalHandler.install("ILL");
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "could not install signal handler", e);
		}
	}

	/**
	 * This metod does nothing. The actual work is done in the static part of
	 * this class.
	 * 
	 */
	public static void initialize() {
	}

	private static SignalHandler install(String signalName) {
		Signal diagSignal = new Signal(signalName);
		JDMPSignalHandler instance = new JDMPSignalHandler();
		instance.oldHandler = Signal.handle(diagSignal, instance);
		return instance;
	}

	public void handle(Signal signal) {
		logger.log(Level.INFO, "Signal handler called for signal " + signal);
		try {
			signalAction(signal);
			// Chain back to previous handler, if one exists
			if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
				oldHandler.handle(signal);
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "handle Signal handler failed" + e);
		}
	}

	public void signalAction(Signal signal) {
		logger.log(Level.INFO, "trying to open GUI");
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame");
			method.invoke(null);
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show GUI", e);
		}
	}

}
