package org.jdmp.core.matrix.system;

import java.util.TimerTask;

import org.jdmp.core.util.GlobalTimer;
import org.ujmp.core.Matrix;
import org.ujmp.core.matrices.stubs.AbstractDenseDoubleMatrix2D;

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

	public double getDouble(long row, long column) {
		return System.currentTimeMillis();
	}

	public void setDouble(double value, long row, long column) {
	}

	public boolean isReadOnly() {
		return true;
	}

}
