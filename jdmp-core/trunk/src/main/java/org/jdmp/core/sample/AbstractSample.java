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

package org.jdmp.core.sample;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractSample extends AbstractCoreObject implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	private transient GUIObject guiObject = null;

	public AbstractSample() {
		super();
	}

	@Override
	public abstract Sample clone();

	@Override
	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.sample.SampleGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Sample.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create sample gui object", e);
			}
		}
		return guiObject;
	}

	public Matrix getMatrix(Object variableKey) {
		Variable v = getVariables().get(variableKey);
		if (v != null) {
			return v.getMatrix();
		} else {
			return null;
		}
	}

	public void notifyGUIObject() {
		guiObject.fireValueChanged();
	}

}
