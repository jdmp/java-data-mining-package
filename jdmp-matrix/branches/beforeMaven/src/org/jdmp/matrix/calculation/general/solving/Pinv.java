package org.jdmp.matrix.calculation.general.solving;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.JDMPSettings;

public class Pinv extends DoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix pinv = null;

	public Pinv(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (pinv == null) {

			Matrix[] ms = getSource().svd();
			Matrix u = ms[0];
			Matrix s = ms[1];
			Matrix v = ms[2];

			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				double d = s.getDouble(i, i);
				if (Math.abs(d) > JDMPSettings.getTolerance()) {
					s.setDouble(1.0 / d, i, i);
				}
			}

			pinv = v.mtimes(s.transpose()).mtimes(u.transpose());

		}
		return pinv.getDouble(coordinates);
	}

	@Override
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}

}
