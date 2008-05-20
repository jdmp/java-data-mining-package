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

	public Object getMatrixAnnotation() {
		return "Memory Usage";
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		if (axis == ROW) {
			switch (positionOnAxis) {
			case 0:
				return "Free Memory";
			case 1:
				return "Max Memory";
			case 2:
				return "Total Memory";
			}
		}
		return null;
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
	}

}
