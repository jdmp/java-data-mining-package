package org.jdmp.core.matrix.wrappers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.ListSelectionModel;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.interfaces.HasColumnSelectionModel;
import org.jdmp.matrix.interfaces.HasRowSelectionModel;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.util.collections.MatrixList;

public class MatrixToMatrixListWrapper extends MatrixList implements Wrapper<Matrix> {
	private static final long serialVersionUID = 7207765740833228177L;

	private Matrix matrix = null;

	private Map<Integer, Matrix> matrixList = new WeakHashMap<Integer, Matrix>();

	public MatrixToMatrixListWrapper(Matrix matrix) {
		this.matrix = matrix;
	}

	public boolean add(Matrix e) {
		return false;
	}

	public void add(int index, Matrix element) {
	}

	public boolean addAll(Collection<? extends Matrix> c) {
		return false;
	}

	public boolean addAll(int index, Collection<? extends Matrix> c) {
		return false;
	}

	public void clear() {
	}

	public boolean contains(Object o) {
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		return false;
	}

	public Matrix get(int index) {
		Matrix m = matrixList.get(index);
		if (m == null) {
			m = matrix.subMatrix(Ret.LINK, index, 0, 0, matrix.getColumnCount() - 1);
			matrixList.put(index, m);
		}
		return m;
	}

	public int indexOf(Object o) {
		return -1;
	}

	public boolean isEmpty() {
		return false;
	}

	public Iterator<Matrix> iterator() {
		return new Iterator<Matrix>() {

			int index = 0;

			public boolean hasNext() {
				return index < size();
			}

			public Matrix next() {
				return get(index++);
			}

			public void remove() {
			}
		};
	}

	public int lastIndexOf(Object o) {
		return 0;
	}

	public ListIterator<Matrix> listIterator() {
		return null;
	}

	public ListIterator<Matrix> listIterator(int index) {
		return null;
	}

	public boolean remove(Object o) {
		return false;
	}

	public Matrix remove(int index) {
		return null;
	}

	public boolean removeAll(Collection<?> c) {
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		return false;
	}

	public Matrix set(int index, Matrix element) {
		return null;
	}

	public int size() {
		return (int) matrix.getRowCount();
	}

	public List<Matrix> subList(int fromIndex, int toIndex) {
		return null;
	}

	public <T> T[] toArray(T[] a) {
		return null;
	}

	public void append(Matrix m) {
	}

	public Object[] toArray() {
		return null;
	}

	public ListSelectionModel getRowSelectionModel() {
		return ((HasRowSelectionModel) matrix.getGUIObject()).getRowSelectionModel();
	}

	public ListSelectionModel getColumnSelectionModel() {
		return ((HasColumnSelectionModel) matrix.getGUIObject()).getColumnSelectionModel();
	}

	public boolean isSynchronized() {
		return false;
	}

	public Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Matrix object) {
		this.matrix = object;
	}

	public int getMaxCount() {
		return (int) matrix.getRowCount();
	}

	public void setMaxCount(int maxCount) {
	}

}
