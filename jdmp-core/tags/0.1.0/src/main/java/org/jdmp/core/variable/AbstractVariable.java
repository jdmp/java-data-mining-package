/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractVariable extends AbstractCoreObject implements Variable {
	private static final long serialVersionUID = -3393106211967497877L;

	private transient GUIObject guiObject = null;

	protected AbstractVariable() {
		super();
	}

	public final int getMatrixCount() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getSize();
		}
	}

	public final Matrix getMatrix() {
		if (getMatrixList() == null) {
			return null;
		} else {
			return getMatrixList().getElementAt(getMatrixList().getSize() - 1);
		}
	}

	public final Matrix getMatrix(int index) {
		if (getMatrixList() == null)
			return null;
		return getMatrixList().getElementAt(index);
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

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.variable.VariableGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Variable.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create variable gui object", e);
			}
		}
		return guiObject;
	}

	@Override
	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	public void notifyGUIObject() {
		guiObject.fireValueChanged();
	}

}
