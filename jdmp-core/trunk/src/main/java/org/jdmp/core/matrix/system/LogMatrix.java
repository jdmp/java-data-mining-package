/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.core.matrix.system;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.AbstractDenseMatrix2D;

public class LogMatrix extends AbstractDenseMatrix2D {
	private static final long serialVersionUID = 4054005288748742516L;

	private static LogMatrix matrix = null;

	public static final LogMatrix getInstance() {
		if (matrix == null) {
			matrix = new LogMatrix();
			matrix.setLabel("Log");
		}
		return matrix;
	}

	private LogMatrix() {
		HandlerWrapper.getInstance().setMatrix(this);
	}

	public static class HandlerWrapper extends java.util.logging.Handler {

		private static HandlerWrapper handler = null;

		private final List<LogRecord> list = new ArrayList<LogRecord>();

		private Matrix matrix = null;

		private HandlerWrapper() {
		}

		public static HandlerWrapper getInstance() {
			if (handler == null) {
				handler = new HandlerWrapper();
			}
			return handler;
		}

		protected void setMatrix(Matrix matrix) {
			this.matrix = matrix;
		}

		@Override
		public void close() throws SecurityException {
		}

		@Override
		public void flush() {
		}

		@Override
		public void publish(LogRecord record) {
			record.setParameters(new Object[] { Runtime.getRuntime().freeMemory(),
					Runtime.getRuntime() });
			list.add(record);
			if (matrix != null) {
				matrix.notifyGUIObject();
			}
		}

		public List<LogRecord> getList() {
			return list;
		}

	}

	public long[] getSize() {
		return new long[] { HandlerWrapper.getInstance().getList().size(), 7 };
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	public Object getObject(long row, long column) {
		LogRecord record = HandlerWrapper.getInstance().getList().get((int) row);
		switch ((int) column) {
		case 0:
			return record.getMillis();
		case 1:
			return record.getLevel();
		case 2:
			return record.getMessage();
		case 3:
			return record.getSourceClassName();
		case 4:
			return record.getSourceMethodName();
		case 5:
			return record.getParameters()[0];
		case 6:
			return record.getThrown();
		}
		return null;
	}

	public void setObject(Object o, long row, long column) {
	}

}
