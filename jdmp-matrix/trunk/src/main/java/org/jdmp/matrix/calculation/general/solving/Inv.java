package org.jdmp.matrix.calculation.general.solving;

import java.lang.reflect.Constructor;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.implementations.misc.CommonsMathRealMatrix;

public class Inv extends DoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix inv = null;

	public Inv(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (inv == null) {
			try {

				Matrix m = getSource();

				try {
					Class<?> c = Class.forName("org.jdmp.mtj.MTJDenseDoubleMatrix2D");
					Constructor<?> con = c.getConstructor(Matrix.class);
					m = (Matrix) con.newInstance(m);
				} catch (ClassNotFoundException e) {
					m = new CommonsMathRealMatrix(m);
				}

				inv = m.inv();

			} catch (Exception e) {
				throw new MatrixException(e);
			}
		}
		return inv.getDouble(coordinates);
	}

	@Override
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}

}
