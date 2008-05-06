package org.jdmp.matrix.calculation.general.solving;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Princomp extends DoubleCalculation {
	private static final long serialVersionUID = -6137993493011004670L;

	private Matrix pca = null;

	public Princomp(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (pca == null) {

			Matrix[] usv;
			usv = getSource().svd();
			Matrix u = usv[0];
			Matrix s = usv[1];
			pca = u.mtimes(s);

		}
		return pca.getDouble(coordinates);
	}

}
