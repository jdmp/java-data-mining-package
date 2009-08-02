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

package org.jdmp.core.module;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private static final long serialVersionUID = 4621466897617405575L;

	private ObservableMap<Algorithm> algorithms = null;

	private ObservableMap<Sample> samples = null;

	private ObservableMap<DataSet> dataSets = null;

	private ObservableMap<Variable> variables = null;

	private ObservableMap<Module> modules = null;

	@Override
	public final String getDescription() {
		return getAsString(Sample.DESCRIPTION);
	}

	@Override
	public final void setDescription(String description) {
		setObject(Sample.DESCRIPTION, description);
	}

	@Override
	public final String getLabel() {
		return getAsString(Sample.LABEL);
	}

	@Override
	public final void setLabel(String label) {
		setObject(Sample.LABEL, label);
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

	public final ObservableMap<Variable> getVariables() {
		if (variables == null) {
			variables = new DefaultObservableMap<Variable>();
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

	public final String getAllAsString(Object variableKey) {
		Variable v = getVariables().get(variableKey);
		if (v != null) {
			return StringUtil.getAllAsString(v.getMatrixList().toCollection());
		} else {
			return null;
		}
	}

	@Override
	public final void setId(String id) {
		setObject(Sample.ID, id);
	}

	public final String getId() {
		String id = getAsString(Sample.ID);
		if (id == null) {
			id = "Sample" + getCoreObjectId();
			setId(id);
		}
		return id;
	}

	public final String getAsString(Object variableKey) {
		return StringUtil.convert(getMatrix(variableKey));
	}

	public final boolean getAsBoolean(Object variableKey) {
		return MathUtil.getBoolean(getMatrix(variableKey));
	}

	public final byte getAsByte(Object variableKey) {
		return MathUtil.getByte(getMatrix(variableKey));
	}

	public final char getAsChar(Object variableKey) {
		return MathUtil.getChar(getMatrix(variableKey));
	}

	public final double getAsDouble(Object variableKey) {
		return MathUtil.getDouble(getMatrix(variableKey));
	}

	public final float getAsFloat(Object variableKey) {
		return MathUtil.getFloat(getMatrix(variableKey));
	}

	public final int getAsInt(Object variableKey) {
		return MathUtil.getInt(getMatrix(variableKey));
	}

	public final long getAsLong(Object variableKey) {
		return MathUtil.getLong(getMatrix(variableKey));
	}

	public final short getAsShort(Object variableKey) {
		return MathUtil.getShort(getMatrix(variableKey));
	}

	public final Date getAsDate(Object variableKey) {
		return MathUtil.getDate(getMatrix(variableKey));
	}

	public final BigDecimal getAsBigDecimal(Object variableKey) {
		return MathUtil.getBigDecimal(getMatrix(variableKey));
	}

	public final BigInteger getAsBigInteger(Object variableKey) {
		return MathUtil.getBigInteger(getMatrix(variableKey));
	}

	@Override
	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	public final void setObject(Object variableKey, Object value) {
		if (value == null) {
			setMatrix(variableKey, MatrixFactory.emptyMatrix());
		} else if (value instanceof Matrix) {
			setMatrix(variableKey, (Matrix) value);
		} else {
			setMatrix(variableKey, MatrixFactory.linkToValue(value));
		}
	}

	public final void setVariables(ObservableMap<Variable> variables) {
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
