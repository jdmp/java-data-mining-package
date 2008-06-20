package org.jdmp.jgroups;

import org.jdmp.core.matrix.wrappers.MatrixListToMatrixWrapper;
import org.jdmp.core.variable.AbstractVariable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.coordinates.Coordinates;

public class ReplicatedVariable extends AbstractVariable {
	private static final long serialVersionUID = -2486416251545919644L;

	private static int runningId = 0;

	private transient Matrix matrixListMatrix = null;

	private MatrixList matrixList = null;

	private long[] size = new long[] { 0, 0 };

	public ReplicatedVariable() {
		this("Variable" + runningId++, 100, 0, 0);
	}

	public ReplicatedVariable(String label) {
		this(label, 100, 0, 0);
	}

	public ReplicatedVariable(int count) {
		this("Variable" + runningId++, count, 0, 0);
	}

	public ReplicatedVariable(Matrix matrix) {
		this("Variable" + runningId++, 1, matrix.getRowCount(), matrix.getColumnCount());
		addMatrix(matrix);
	}

	public ReplicatedVariable(String label, Matrix matrix) {
		this(label, 1, matrix.getRowCount(), matrix.getColumnCount());
		addMatrix(matrix);
	}

	public ReplicatedVariable(String label, int count) {
		this(label, count, 0, 0);
	}

	public ReplicatedVariable(String label, double value) {
		this(label, 1, 1, 1);
		addMatrix(MatrixFactory.linkToValue(value));
	}

	public ReplicatedVariable(String label, long rows, long columns) {
		this(label, (int) Math.ceil(1000000.0 / rows / columns), rows, columns);
	}

	public ReplicatedVariable(String label, int memorySize, long rows, long columns) {
		super();
		this.matrixList = new ReplicatedMatrixList(this, label, memorySize);
		setLabel(label);
		setSize(rows, columns);
	}

	public MatrixList getMatrixList() {
		return matrixList;
	}

	public void setMemorySize(int size) {
		matrixList.setMaxCount(size);
	}

	public final long[] getSize() {
		return size;
	}

	public final void setSize(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public Matrix getAsMatrix() {
		if (matrixListMatrix == null) {
			matrixListMatrix = new MatrixListToMatrixWrapper(this);
		}
		return matrixListMatrix;
	}

}
