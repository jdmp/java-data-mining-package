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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jdmp.core.util.DefaultObservableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class DefaultVariables extends DefaultObservableMap<Variable> implements Variables {
	private static final long serialVersionUID = -863822584023419772L;

	public final BigInteger getAsBigInteger(String variableKey) {
		return MathUtil.getBigInteger(getMatrix(variableKey));
	}

	public final Matrix getMatrix(String variableKey) {
		Variable v = get(variableKey);
		if (v != null) {
			return v.getMatrix();
		} else {
			return null;
		}
	}

	public void setMatrix(String variableKey, Matrix matrix) {
		Variable v = get(variableKey);
		if (v == null) {
			v = VariableFactory.labeledVariable(variableKey.toString());
			put(variableKey, v);
		}
		v.addMatrix(matrix);
	}

	public final void setObject(String variableKey, Object value) {
		if (value == null) {
			setMatrix(variableKey, MatrixFactory.emptyMatrix());
		} else if (value instanceof Matrix) {
			setMatrix(variableKey, (Matrix) value);
		} else {
			setMatrix(variableKey, MatrixFactory.linkToValue(value));
		}
	}

	public final String getAsString(String variableKey) {
		return StringUtil.convert(getMatrix(variableKey));
	}

	public final String getAllAsString(String variableKey) {
		Variable v = get(variableKey);
		if (v != null) {
			return StringUtil.getAllAsString(v.getMatrixList().toCollection());
		} else {
			return null;
		}
	}

	public final boolean getAsBoolean(String variableKey) {
		return MathUtil.getBoolean(getMatrix(variableKey));
	}

	public final byte getAsByte(String variableKey) {
		return MathUtil.getByte(getMatrix(variableKey));
	}

	public final char getAsChar(String variableKey) {
		return MathUtil.getChar(getMatrix(variableKey));
	}

	public final double getAsDouble(String variableKey) {
		return MathUtil.getDouble(getMatrix(variableKey));
	}

	public final float getAsFloat(String variableKey) {
		return MathUtil.getFloat(getMatrix(variableKey));
	}

	public final int getAsInt(String variableKey) {
		return MathUtil.getInt(getMatrix(variableKey));
	}

	public final Object getAsObject(String variableKey) {
		return MathUtil.getObject(getMatrix(variableKey));
	}

	public final long getAsLong(String variableKey) {
		return MathUtil.getLong(getMatrix(variableKey));
	}

	public final short getAsShort(String variableKey) {
		return MathUtil.getShort(getMatrix(variableKey));
	}

	public final Date getAsDate(String variableKey) {
		return MathUtil.getDate(getMatrix(variableKey));
	}

	public final BigDecimal getAsBigDecimal(String variableKey) {
		return MathUtil.getBigDecimal(getMatrix(variableKey));
	}

}
