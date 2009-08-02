package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.SingletonObservableList;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;

public class SingletonVariable extends AbstractVariable {
	private static final long serialVersionUID = -6210738091770692066L;

	private ObservableList<Matrix> matrixList = null;

	private String label = null;

	private String description = null;

	public SingletonVariable(Matrix matrix) {
		this.matrixList = new SingletonObservableList<Matrix>(matrix);
	}

	public Variable clone() {
		Variable v = new SingletonVariable(getMatrix());
		return v;
	}

	@Override
	public synchronized Matrix getAsMatrix() {
		return getMatrix();
	}

	@Override
	public synchronized long[] getSize() {
		Matrix m = getMatrix();
		return m == null ? Coordinates.ZERO2D : m.getSize();
	}

	@Override
	public void setSize(long... size) {
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public ObservableList<Matrix> getMatrixList() {
		return matrixList;
	}
}
