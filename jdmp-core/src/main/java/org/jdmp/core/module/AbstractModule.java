/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.DefaultVariables;
import org.jdmp.core.variable.Variables;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private static final long serialVersionUID = 4621466897617405575L;

	private ObservableMap<Algorithm> algorithms = null;

	private ObservableMap<Sample> samples = null;

	private ObservableMap<DataSet> dataSets = null;

	private Variables variables = null;

	private ObservableMap<Module> modules = null;

	public final String getDescription() {
		return getVariables().getAsString(Sample.DESCRIPTION);
	}

	public final void setLabelObject(Object label) {
		getVariables().setObject(Sample.LABEL, label);
	}

	public final Object getLabelObject() {
		return getVariables().getAsObject(Sample.LABEL);
	}

	public final void setDescription(String description) {
		getVariables().setObject(Sample.DESCRIPTION, description);
	}

	public final String getLabel() {
		return getVariables().getAsString(Sample.LABEL);
	}

	public final void setLabel(String label) {
		getVariables().setObject(Sample.LABEL, label);
	}

	public final ObservableMap<Module> getModules() {
		if (modules == null) {
			modules = new DefaultObservableMap<Module>();
		}
		return modules;
	}

	public final ObservableMap<DataSet> getDataSets() {
		if (dataSets == null) {
			dataSets = new DefaultObservableMap<DataSet>();
		}
		return dataSets;
	}

	public final ObservableMap<Algorithm> getAlgorithms() {
		if (algorithms == null) {
			algorithms = new DefaultObservableMap<Algorithm>();
		}
		return algorithms;
	}

	public final Variables getVariables() {
		if (variables == null) {
			variables = new DefaultVariables();
		}
		return variables;
	}

	public final ObservableMap<Sample> getSamples() {
		if (samples == null) {
			samples = new DefaultObservableMap<Sample>();
		}
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
		getVariables().setObject(Sample.ID, id);
	}

	public final String getId() {
		String id = getVariables().getAsString(Sample.ID);
		if (id == null) {
			id = "Module" + getCoreObjectId();
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

	public final void setVariables(Variables variables) {
		this.variables = variables;
	}

	public final void setModules(ObservableMap<Module> modules) {
		this.modules = modules;
	}

	public final void setAlgorithms(ObservableMap<Algorithm> algorithms) {
		this.algorithms = algorithms;
	}

	public final void setDataSets(ObservableMap<DataSet> dataSets) {
		this.dataSets = dataSets;
	}

	public final void setSamples(ObservableMap<Sample> samples) {
		this.samples = samples;
	}

}
