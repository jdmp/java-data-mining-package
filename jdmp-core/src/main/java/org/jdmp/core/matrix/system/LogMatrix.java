package org.jdmp.core.matrix.system;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.AbstractDenseObjectMatrix2D;

public class LogMatrix extends AbstractDenseObjectMatrix2D {
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
