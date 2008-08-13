package org.jdmp.jgroups;

import javax.swing.event.EventListenerList;

import org.jdmp.core.variable.AbstractVariable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.util.MatrixListToMatrixWrapper;

public class ReplicatedVariable extends AbstractVariable {
	private static final long serialVersionUID = -2486416251545919644L;

	private static int runningId = 0;

	private transient Matrix matrixListMatrix = null;

	private MatrixList matrixList = null;

	private transient EventListenerList listenerList = null;

	private String label = "";

	private String description = "";

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

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
