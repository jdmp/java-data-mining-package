package org.jdmp.core.variable;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.collections.DefaultMatrixList;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.exceptions.MatrixException;

public class DefaultVariable extends AbstractVariable {
	private static final long serialVersionUID = -7192491915167470355L;

	private MatrixList matrixList = null;

	public DefaultVariable() {
		super();
		matrixList = new DefaultMatrixList();
	}

	public DefaultVariable(int count) {
		this();
		matrixList = new DefaultMatrixList(count);
	}

	public DefaultVariable(MatrixList matrixList) {
		this();
		this.matrixList = matrixList;
		setSize(1, matrixList.getTraceCount());
	}

	public DefaultVariable(Matrix matrix) {
		this();
		matrixList = new DefaultMatrixList(1);
		addMatrix(matrix);
	}

	public DefaultVariable(String label, Matrix matrix) {
		this(matrix);
		setLabel(label);
	}

	public DefaultVariable(String label) {
		this();
		setLabel(label);
	}

	public DefaultVariable(String label, double value) {
		this();
		setLabel(label);
		addMatrix(MatrixFactory.linkToValue(value));
	}

	public DefaultVariable(String label, int memorySize) {
		this();
		setLabel(label);
		this.matrixList = new DefaultMatrixList(memorySize);
	}

	public DefaultVariable(String label, int rows, int columns) {
		this();
		setLabel(label);
		setSize(rows, columns);
		this.matrixList = new DefaultMatrixList((int) Math.ceil(1000000.0 / rows / columns));
	}

	public DefaultVariable(String label, int memorySize, int rows, int columns) {
		this();
		setSize(rows, columns);
		this.matrixList = new DefaultMatrixList(memorySize);
		setLabel(label);
	}

	public MatrixList getMatrixList() {
		return matrixList;
	}

	public void setMatrixList(DefaultMatrixList history) {
		this.matrixList = history;
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
