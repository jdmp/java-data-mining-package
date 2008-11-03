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

package org.jdmp.core.variable;

import org.jdmp.core.util.AbstractEvent;
import org.ujmp.core.Matrix;

public class VariableEvent extends AbstractEvent {
	private static final long serialVersionUID = -8349940514872800903L;

	public VariableEvent(Variable source, EventType type) {
		super(source, type);
	}


	public VariableEvent(Variable source, EventType type, int index, Matrix m) {
		super(source, type, index, m);
	}

	public Matrix getMatrix() {
		if (data.length == 2)
			return (Matrix) data[1];
		else
			return null;
	}

	public int getIndex() {
		if (data.length > 0)
			return (Integer) data[0];
		else
			return 0;
	}
}
