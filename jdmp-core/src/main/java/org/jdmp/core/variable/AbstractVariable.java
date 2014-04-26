/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.lang.reflect.Constructor;

import org.jdmp.core.util.ObservableList;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractVariable extends DefaultListMatrix<Matrix> implements Variable {
	private static final long serialVersionUID = -3393106211967497877L;

	protected AbstractVariable() {
		super();
		setId("Variable" + getCoreObjectId());
	}

	public final int getInnerMatrixCount() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getSize();
		}
	}

	public final Matrix getLatestMatrix() {
		ObservableList<Matrix> list = getMatrixList();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.getElementAt(list.getSize() - 1);
		}
	}

	public final String getAsString() {
		return StringUtil.convert(getLatestMatrix());
	}

	public final Matrix getInnerMatrix(int index) {
		if (getMatrixList() == null) {
			return null;
		} else {
			return getMatrixList().getElementAt(index);
		}
	}

	public final void addInnerMatrix(Matrix m) {
		if (m == null) {
			throw new RuntimeException("tried to add null Matrix");
		}

		if (getInnerSize() == null) {
			setInnerSize(Coordinates.copyOf(m.getSize()));
		}

		getMatrixList().add(m);
	}

	public final long getInnerRowCount() {
		return getInnerSize()[ROW];
	}

	public final long getInnerColumnCount() {
		return getInnerSize()[COLUMN];
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				Class<?> c = Class.forName("org.jdmp.gui.variable.VariableGUIObject");
				con = c.getConstructor(new Class<?>[] { Variable.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
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
