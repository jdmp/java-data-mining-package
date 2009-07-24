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

	protected final ObservableMap<Algorithm> algorithms = new DefaultObservableMap<Algorithm>();

	protected final ObservableMap<Sample> samples = new DefaultObservableMap<Sample>();

	protected final ObservableMap<DataSet> dataSets = new DefaultObservableMap<DataSet>();

	protected ObservableMap<Variable> variables = new DefaultObservableMap<Variable>();

	protected final ObservableMap<Module> modules = new DefaultObservableMap<Module>();

	public ObservableMap<Module> getModules() {
		return modules;
	}

	public final ObservableMap<DataSet> getDataSets() {
		return dataSets;
	}

	public final ObservableMap<Algorithm> getAlgorithms() {
		return algorithms;
	}

	public final ObservableMap<Variable> getVariables() {
		return variables;
	}

	public final ObservableMap<Sample> getSamples() {
		return samples;
	}

	public void clear() {
		algorithms.clear();
		dataSets.clear();
		modules.clear();
		samples.clear();
		variables.clear();
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

	public void setVariables(ObservableMap<Variable> variables) {
		this.variables = variables;
	}

}
