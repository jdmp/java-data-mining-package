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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jdmp.core.util.ObservableMap;
import org.ujmp.core.Matrix;

public interface Variables extends ObservableMap<Variable> {

	public String getAllAsString(String variableKey);

	public boolean getAsBoolean(String variableKey);

	public byte getAsByte(String variableKey);

	public char getAsChar(String variableKey);

	public double getAsDouble(String variableKey);

	public float getAsFloat(String variableKey);

	public int getAsInt(String variableKey);

	public long getAsLong(String variableKey);

	public short getAsShort(String variableKey);

	public Date getAsDate(String variableKey);

	public BigDecimal getAsBigDecimal(String variableKey);

	public BigInteger getAsBigInteger(String variableKey);

	public Matrix getMatrix(String variableKey);

	public String getAsString(String variableKey);

	public Object getAsObject(String variableKey);

	public Object getObject(String variableKey);

	public void setMatrix(String variableKey, Matrix matrix);

	public void setObject(String variableKey, Object value);

}
