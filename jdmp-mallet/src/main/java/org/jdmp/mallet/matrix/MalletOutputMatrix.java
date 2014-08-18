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

package org.jdmp.mallet.matrix;

import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

import cc.mallet.types.LabelSequence;

public class MalletOutputMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<LabelSequence> {
	private static final long serialVersionUID = -3588963392576526020L;

	private LabelSequence labelSequence = null;

	private int index = 0;

	public MalletOutputMatrix(LabelSequence ls, int index) {
		super(1, ls.getAlphabet().size());
		this.labelSequence = ls;
		this.index = index;
	}

	public long[] getSize() {
		return new long[] { 1, labelSequence.getAlphabet().size() };
	}

	public LabelSequence getWrappedObject() {
		return labelSequence;
	}

	public void setWrappedObject(LabelSequence object) {
		this.labelSequence = object;
	}

	public double getDouble(long row, long column) {
		return labelSequence.getIndexAtPosition(index) == column ? 1.0 : 0.0;
	}

	public void setDouble(double value, long row, long column) {
	}

	public double getDouble(int row, int column) {
		return labelSequence.getIndexAtPosition(index) == column ? 1.0 : 0.0;
	}

	public void setDouble(double value, int row, int column) {
	}

}
