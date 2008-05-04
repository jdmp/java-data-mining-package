package org.jdmp.matrix.implementations.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractListMatrix;

public class DefaultListMatrix<A> extends AbstractListMatrix<A> {
	private static final long serialVersionUID = -6381864884046078055L;

	private List<A> list = null;

	private Object matrixAnnotation = null;

	public DefaultListMatrix(Collection<A> list) {
		if (list instanceof List) {
			this.list = (List<A>) list;
		} else {
			list = new ArrayList<A>(list);
		}
	}

	public DefaultListMatrix() {
		list = new ArrayList<A>();
	}

	public DefaultListMatrix(A... objects) {
		list = Arrays.asList(objects);
	}

	@Override
	public List<A> getList() {
		return list;
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object value) {
		matrixAnnotation = value;
	}

	public Iterable<long[]> allCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean contains(long... coordinates) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		// TODO Auto-generated method stub
		
	}

	public org.jdmp.matrix.Matrix.EntryType getEntryType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSparse() {
		// TODO Auto-generated method stub
		return false;
	}

}
