package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class ImputeKNN extends DoubleCalculation {
	private static final long serialVersionUID = -4923873199518001578L;

	private Matrix sourceCopy = null;

	public ImputeKNN(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public Matrix getNearestNeighbor(long... coordinates) throws MatrixException {
		double bestDistance = Double.MAX_VALUE;
		Matrix bestMatrix = null;
		Matrix toReplace = null;
		switch (getDimension()) {
		case ROW:
			toReplace = getSource().selectRows(Ret.LINK, coordinates[ROW]);
			for (long r = getSource().getRowCount() - 1; r != -1; r--) {
				if (r != coordinates[ROW]) {
					Matrix candidate = getSource().selectRows(Ret.LINK, r);
					if (!MathUtil.isNaNOrInfinite(candidate.getDouble(0, coordinates[COLUMN]))) {
						double distance = toReplace.euklideanDistanceTo(candidate, true);
						if (distance < bestDistance) {
							bestDistance = distance;
							bestMatrix = candidate;
						}
					}
				}
			}
			break;
		case COLUMN:
			toReplace = getSource().selectColumns(Ret.LINK, coordinates[COLUMN]);
			for (long c = getSource().getColumnCount() - 1; c != -1; c--) {
				if (c != coordinates[COLUMN]) {
					Matrix candidate = getSource().selectColumns(Ret.LINK, c);
					double distance = toReplace.euklideanDistanceTo(candidate, true);
					if (distance < bestDistance) {
						bestDistance = distance;
						bestMatrix = candidate;
					}
				}
			}
			break;
		}
		return bestMatrix;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (sourceCopy == null) {
			sourceCopy = getSource().clone();
		}
		double v = sourceCopy.getDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			switch (getDimension()) {
			case ROW:
				return getNearestNeighbor(coordinates).getDouble(0, coordinates[COLUMN]);
			case COLUMN:
				return getNearestNeighbor(coordinates).getDouble(coordinates[ROW], 0);
			}
		} else {
			return v;
		}
		return 0.0;
	}

}
