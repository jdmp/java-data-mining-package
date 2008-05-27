package org.jdmp.core.matrix.system;

import java.util.TimerTask;

import org.jdmp.core.util.GlobalTimer;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class MatrixMemoryUsage extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -3863745960302379726L;

	private static Matrix matrix = null;

	private MatrixMemoryUsage() {
		GlobalTimer.getInstance().schedule(new TimerTask() {

			@Override
			public void run() {
				matrix.notifyGUIObject();

			}
		}, 1000, 1000);
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixMemoryUsage();
		}
		return matrix;
	}

	public double getDouble(long... coordinates) {
		switch ((int) coordinates[ROW]) {
		case 0:
			return Runtime.getRuntime().freeMemory();
		case 1:
			return Runtime.getRuntime().maxMemory();
		default:
			return Runtime.getRuntime().totalMemory();
		}
	}

	public long[] getSize() {
		return new long[] { 3, 1 };
	}

	public void setDouble(double value, long... coordinates) {
	}

	public boolean isReadOnly() {
		return true;
	}

}
