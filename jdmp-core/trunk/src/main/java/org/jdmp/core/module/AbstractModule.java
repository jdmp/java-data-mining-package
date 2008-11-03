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

package org.jdmp.core.module;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private transient GUIObject guiObject = null;

	protected final ObservableList<Algorithm> algorithmList = new ObservableList<Algorithm>();

	protected final ObservableList<DataSet> dataSetList = new ObservableList<DataSet>();

	protected final ObservableMap<Variable> variableList = new ObservableMap<Variable>();

	protected final ObservableList<Module> moduleList = new ObservableList<Module>();

	public ObservableList<Module> getModuleList() {
		return moduleList;
	}

	public final ObservableList<DataSet> getDataSetList() {
		return dataSetList;
	}

	public final ObservableList<Algorithm> getAlgorithmList() {
		return algorithmList;
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableList;
	}

	public void clear() {
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.module.ModuleGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Module.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create module gui object", e);
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

}
