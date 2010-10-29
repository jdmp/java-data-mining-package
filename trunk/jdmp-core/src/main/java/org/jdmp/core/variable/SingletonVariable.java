package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.SingletonObservableList;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

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

	public synchronized Matrix getAsMatrix() {
		return getMatrix();
	}

	public final void setLabelObject(Object label) {
		this.label = StringUtil.format(label);
	}

	public final Object getLabelObject() {
		return label;
	}

	public synchronized long[] getSize() {
		Matrix m = getMatrix();
		return m == null ? Coordinates.ZERO2D : m.getSize();
	}

	public void setSize(long... size) {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ObservableList<Matrix> getMatrixList() {
		return matrixList;
	}
}
