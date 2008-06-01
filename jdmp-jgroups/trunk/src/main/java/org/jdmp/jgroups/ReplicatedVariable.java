package org.jdmp.jgroups;

import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.collections.DefaultMatrixList;
import org.jdmp.matrix.util.collections.MatrixList;

public class ReplicatedVariable extends Variable {
	private static final long serialVersionUID = -2486416251545919644L;

	private static int runningId = 0;

	private MatrixList matrixList = null;

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
		addMatrix(MatrixFactory.fromValue(value));
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

	public final void convertIntToVector(int numberOfClasses) throws MatrixException {
		MatrixList ts = new DefaultMatrixList(matrixList);
		setSize(numberOfClasses, 1);
		for (int i = 0; i < ts.size(); i++) {
			matrixList.add(ts.get(i).convertIntToVector(numberOfClasses));
		}
	}

}
