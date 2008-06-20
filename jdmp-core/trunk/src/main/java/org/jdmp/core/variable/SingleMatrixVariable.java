package org.jdmp.core.variable;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.collections.AbstractMatrixList;
import org.jdmp.matrix.collections.MatrixList;

public class SingleMatrixVariable extends AbstractVariable {
	private static final long serialVersionUID = -566983410531753971L;

	private MatrixList matrixList = new SingleMatrixList();

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
		return matrixList;
	}

	public Matrix getAsMatrix() {
		return matrixList.get(0);
	}

	public long[] getSize() {
		Matrix m = getMatrix();
		return m == null ? new long[] { 0, 0 } : m.getSize();
	}

	public void setMemorySize(int size) {
	}

	public void setSize(long... size) {
	}

	class SingleMatrixList extends AbstractMatrixList {

		private Matrix matrix = null;

		public boolean add(Matrix matrix) {
			this.matrix = matrix;
			return true;
		}

		public void clear() {
			matrix = null;
		}

		public Matrix get(int i) {
			return matrix;
		}

		public ListSelectionModel getColumnSelectionModel() {
			return null;
		}

		public int getMaxSize() {
			return 1;
		}

		public ListSelectionModel getRowSelectionModel() {
			return null;
		}

		public int indexOf(Object o) {
			if (o.equals(matrix)) {
				return 0;
			} else {
				return -1;
			}
		}

		public boolean isEmpty() {
			return matrix == null;
		}

		public Matrix set(int index, Matrix m) {
			if (index == 0) {
				this.matrix = m;
			}
			return null;
		}

		public void setMaxCount(int maxCount) {
		}

		public int size() {
			return matrix == null ? 0 : 1;
		}

		public boolean addAll(Collection<? extends Matrix> c) {
			for (Matrix m : c) {
				add(m);
			}
			return true;
		}

		public boolean addAll(int index, Collection<? extends Matrix> c) {
			if (index == 0) {
				return addAll(c);
			} else {
				return false;
			}
		}

		public Iterator<Matrix> iterator() {
			return new Iterator<Matrix>() {

				int index = 0;

				public boolean hasNext() {
					return index == 0 && matrix != null;
				}

				public Matrix next() {
					index++;
					return matrix;
				}

				public void remove() {
				}
			};
		}
	}

}
