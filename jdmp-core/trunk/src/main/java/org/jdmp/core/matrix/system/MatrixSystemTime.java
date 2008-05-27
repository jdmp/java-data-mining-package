package org.jdmp.core.matrix.system;

import java.util.TimerTask;

import org.jdmp.core.util.GlobalTimer;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class MatrixSystemTime extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 8552917654861598011L;

	private static Matrix matrix = null;

	private MatrixSystemTime() {
		GlobalTimer.getInstance().schedule(new TimerTask() {

			@Override
			public void run() {
				matrix.notifyGUIObject();

			}
		}, 1000, 1000);
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixSystemTime();
		}
		return matrix;
	}

	public long[] getSize() {
		return new long[] { 1, 1 };
	}

	public double getDouble(long... coordinates) {
		return System.currentTimeMillis();
	}

	public void setDouble(double value, long... coordinates) {
	}

	public boolean isReadOnly() {
		return true;
	}

}
