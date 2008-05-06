package org.jdmp.matrix.calculation.general.discretize;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface DiscretizeCalculations {

	public Matrix discretizeToColumns(long column) throws MatrixException;

}
