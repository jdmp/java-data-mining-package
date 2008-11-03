/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.core.variable;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.ListSelectionModel;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.AbstractMatrixList;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.coordinates.Coordinates;

public class SingleMatrixVariable extends AbstractVariable {
	private static final long serialVersionUID = -566983410531753971L;

	private final MatrixList matrixList = new SingleMatrixList();

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
		return m == null ? Coordinates.ZERO2D : m.getSize();
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
