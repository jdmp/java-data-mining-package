/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.VariableMap;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private static final long serialVersionUID = 4621466897617405575L;

	private final VariableMap variables = new DefaultVariableMap();
	private final ObservableMap<Algorithm> algorithms = new DefaultObservableMap<Algorithm>();
	private final ObservableMap<Sample> samples = new DefaultObservableMap<Sample>();
	private final ObservableMap<ListDataSet> dataSets = new DefaultObservableMap<ListDataSet>();
	private final ObservableMap<Module> modules = new DefaultObservableMap<Module>();

	public final String getDescription() {
		return getVariableMap().getAsString(Sample.DESCRIPTION);
	}

	public final Object getLabelObject() {
		return getVariableMap().getAsObject(Sample.LABEL);
	}

	public final void setDescription(String description) {
		getVariableMap().setObject(Sample.DESCRIPTION, description);
	}

	public final String getLabel() {
		return getVariableMap().getAsString(Sample.LABEL);
	}

	public final void setLabel(Object label) {
		getVariableMap().setObject(Sample.LABEL, label);
	}

	public final ObservableMap<Module> getModuleMap() {
		return modules;
	}

	public final ObservableMap<ListDataSet> getDataSetMap() {
		return dataSets;
	}

	public final ObservableMap<Algorithm> getAlgorithmMap() {
		return algorithms;
	}

	public final VariableMap getVariableMap() {
		return variables;
	}

	public final ObservableMap<Sample> getSampleMap() {
		return samples;
	}

	public final void clear() {
		if (algorithms != null) {
			algorithms.clear();
		}
		if (dataSets != null) {
			dataSets.clear();
		}
		if (modules != null) {
			modules.clear();
		}
		if (samples != null) {
			samples.clear();
		}
		if (variables != null) {
			variables.clear();
		}
	}

	public final void setId(String id) {
		getVariableMap().setObject(Sample.ID, id);
	}

	public final String getId() {
		String id = getVariableMap().getAsString(Sample.ID);
		if (id == null) {
			id = "Module" + getUUID();
			setId(id);
		}
		return id;
	}

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

}
