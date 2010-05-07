/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.util.ObservableList;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractVariable extends AbstractCoreObject implements Variable {
	private static final long serialVersionUID = -3393106211967497877L;

	private String id = null;

	protected AbstractVariable() {
		super();
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getId() {
		if (id == null) {
			id = "Variable" + getCoreObjectId();
			setId(id);
		}
		return id;
	}

	public final int getMatrixCount() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getSize();
		}
	}

	public final Matrix getMatrix() {
		ObservableList<Matrix> list = getMatrixList();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.getElementAt(list.getSize() - 1);
		}
	}

	public final String getAsString() {
		return StringUtil.convert(getMatrix());
	}

	public final Matrix getMatrix(int index) {
		if (getMatrixList() == null) {
			return null;
		} else {
			return getMatrixList().getElementAt(index);
		}
	}

	public final void addMatrix(Matrix m) {
		if (m == null) {
			throw new RuntimeException("tried to add null Matrix");
		}

		if (getSize() == null) {
			setSize(Coordinates.copyOf(m.getSize()));
		}

		getMatrixList().add(m);
	}

	public final void clear() {
		if (getMatrixList() != null) {
			getMatrixList().clear();
		}
	}

	public final long getRowCount() {
		return getSize()[ROW];
	}

	public final long getColumnCount() {
		return getSize()[COLUMN];
	}

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	public abstract Variable clone();

}
