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

package org.jdmp.core.variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jdmp.core.util.AbstractObservableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractVariables extends AbstractObservableMap<Variable> implements
		Variables {
	private static final long serialVersionUID = 5598126665100040507L;

	public final BigInteger getAsBigInteger(String variableKey) {
		return MathUtil.getBigInteger(getObject(variableKey));
	}

	public final String getAsString(String variableKey) {
		return StringUtil.convert(getObject(variableKey));
	}

	public final boolean getAsBoolean(String variableKey) {
		return MathUtil.getBoolean(getObject(variableKey));
	}

	public final byte getAsByte(String variableKey) {
		return MathUtil.getByte(getObject(variableKey));
	}

	public final char getAsChar(String variableKey) {
		return MathUtil.getChar(getObject(variableKey));
	}

	public final double getAsDouble(String variableKey) {
		Object o = getObject(variableKey);
		if (o instanceof Matrix) {
			return ((Matrix) o).getMeanValue();
		}
		return MathUtil.getDouble(getObject(variableKey));
	}

	public final float getAsFloat(String variableKey) {
		return MathUtil.getFloat(getObject(variableKey));
	}

	public final int getAsInt(String variableKey) {
		return MathUtil.getInt(getObject(variableKey));
	}

	public final Object getAsObject(String variableKey) {
		return getObject(variableKey);
	}

	public abstract Object getObject(String variableKey);

	public abstract void setObject(String variableKey, Object object);

	public abstract Matrix getMatrix(String variableKey);

	public abstract void setMatrix(String variableKey, Matrix object);

	public abstract String getAllAsString(String variableKey);

	public final long getAsLong(String variableKey) {
		return MathUtil.getLong(getObject(variableKey));
	}

	public final short getAsShort(String variableKey) {
		return MathUtil.getShort(getObject(variableKey));
	}

	public final Date getAsDate(String variableKey) {
		return MathUtil.getDate(getObject(variableKey));
	}

	public final BigDecimal getAsBigDecimal(String variableKey) {
		return MathUtil.getBigDecimal(getObject(variableKey));
	}

}
