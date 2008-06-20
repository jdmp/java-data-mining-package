package org.jdmp.core.variable;

import javax.swing.event.EventListenerList;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.collections.MatrixList;

public class SingleMatrixVariable extends AbstractVariable {
	private static final long serialVersionUID = -566983410531753971L;

	public final EventListenerList getListenerList() {
		return null;
	}

	public final String getDescription() {
		return null;
	}

	public final void setDescription(String description) {
	}

	public String getLabel() {
		return null;
	}

	public final void setLabel(String label) {
	}

	public MatrixList getMatrixList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix getAsMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMemorySize(int size) {
		// TODO Auto-generated method stub

	}

	public void setSize(long... size) {
		// TODO Auto-generated method stub

	}

}
