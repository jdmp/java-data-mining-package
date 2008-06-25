package org.jdmp.gui;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jdmp.core.matrix.system.LogMatrix;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.util.GlobalConfiguration;

public class JDMP {
	private static final transient Logger logger = Logger.getLogger(JDMP.class.getName());

	public static void run(ObjectAction action) throws Exception {
		JDMP.main(new String[] { "--textmode" });
		Future<?> f = action.executeInBackground();
		try {
			f.get();
		} catch (Exception e) {
			logger.log(Level.WARNING, "error executing action " + action, e);
		}
	}

	public static void main(String[] args) throws Exception {
		Logger.getLogger("org.jdmp").setLevel(Level.ALL);
		Logger.getLogger("org.jdmp").addHandler(LogMatrix.HandlerWrapper.getInstance());
		GlobalConfiguration.getInstance().setSaveToFile(true);

		String configFile = null;

		Options options = new Options();
		CommandLine cmdLine = null;
		CommandLineParser parser = new PosixParser();

		Option helpOption = new Option("h", "help", false, "print this message");
		Option textOption = new Option("t", "textmode", false, "start in text mode");
		Option debugOption = new Option("d", "debug", false, "start in debug mode");
		Option fileOption = new Option("f", "file", true, "read configuration from file");

		options.addOption(helpOption);
		options.addOption(textOption);
		options.addOption(debugOption);
		options.addOption(fileOption);

		try {
			cmdLine = parser.parse(options, args);
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("JDMP", options);
			System.exit(0);
		}

		if (cmdLine.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("JDMP", options);
			System.exit(0);
		}

		if (cmdLine.hasOption("d")) {
			logger.setLevel(Level.ALL);
			logger.log(Level.FINE, "starting in debug mode");
		}

		if (cmdLine.hasOption("f")) {
			configFile = cmdLine.getOptionValue("f");
			logger.log(Level.FINE, "reading configuration from file " + configFile);
		}

		if (cmdLine.hasOption("t")) {
			logger.info("starting in text mode");
		} else {
			logger.info("starting in graphics mode");
		}

	}
}
