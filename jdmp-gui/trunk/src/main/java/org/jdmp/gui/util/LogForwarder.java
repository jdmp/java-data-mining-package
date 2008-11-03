/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.gui.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class LogForwarder implements Appender {

	private static final Logger logger = Logger.getLogger(LogForwarder.class.getName());

	public void addFilter(Filter f) {
	}

	public void clearFilters() {
	}

	public void close() {
	}

	public void doAppend(LoggingEvent e) {
		Level level = Level.INFO;
		if (e.getLevel().equals(org.apache.log4j.Level.DEBUG)) {
			level = Level.FINE;
		} else if (e.getLevel().equals(org.apache.log4j.Level.ERROR)) {
			level = Level.SEVERE;
		} else if (e.getLevel().equals(org.apache.log4j.Level.FATAL)) {
			level = Level.SEVERE;
		} else if (e.getLevel().equals(org.apache.log4j.Level.WARN)) {
			level = Level.WARNING;
		}
		String sourceClass = e.getLocationInformation().getClassName();
		String sourceMethod = e.getLocationInformation().getMethodName();
		String msg = e.getRenderedMessage();
		Throwable thrown = null;
		if (e.getThrowableInformation() != null) {
			thrown = e.getThrowableInformation().getThrowable();
		}
		logger.logp(level, sourceClass, sourceMethod, msg, thrown);
	}

	public ErrorHandler getErrorHandler() {
		return null;
	}

	public Filter getFilter() {
		return null;
	}

	public Layout getLayout() {
		return null;
	}

	public String getName() {
		return "LogForwarder";
	}

	public boolean requiresLayout() {
		return false;
	}

	public void setErrorHandler(ErrorHandler eh) {
	}

	public void setLayout(Layout layout) {
	}

	public void setName(String name) {
	}

}