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

import org.jdmp.core.util.ObservableMap;
import org.ujmp.core.Matrix;

public interface HasVariableMap extends HasVariables {

	public ObservableMap<Variable> getVariables();

	public Matrix getMatrix(Object variableKey);

	public String getAsString(Object variableKey);

	public String getAllAsString(Object variableKey);

	public boolean getAsBoolean(Object variableKey);

	public byte getAsByte(Object variableKey);

	public char getAsChar(Object variableKey);

	public double getAsDouble(Object variableKey);

	public float getAsFloat(Object variableKey);

	public int getAsInt(Object variableKey);

	public long getAsLong(Object variableKey);

	public short getAsShort(Object variableKey);

	public Date getAsDate(Object variableKey);

	public BigDecimal getAsBigDecimal(Object variableKey);

	public BigInteger getAsBigInteger(Object variableKey);

	public void setMatrix(String variableKey, Matrix matrix);

	public void setObject(String variableKey, Object value);

	public void setVariables(ObservableMap<Variable> variables);

}
