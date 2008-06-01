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

package org.jdmp.matrix.stubs;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.StringMatrix;
import org.jdmp.matrix.util.StringUtil;

public abstract class AbstractStringMatrix extends AbstractGenericMatrix<String> implements
		StringMatrix {

	public final String getObject(long... coordinates) throws MatrixException {
		return getString(coordinates);
	}

	public final void setObject(Object o, long... coordinates) throws MatrixException {
		setString(StringUtil.convert(o), coordinates);
	}

	public final double getDouble(long... coordinates) {
		try {
			return Double.parseDouble(getString(coordinates));
		} catch (Exception e) {
			return Double.NaN;
		}
	}

	public final void setDouble(double value, long... coordinates) throws MatrixException {
		setString("" + value, coordinates);
	}

	public final EntryType getEntryType() {
		return EntryType.STRING;
	}

}
