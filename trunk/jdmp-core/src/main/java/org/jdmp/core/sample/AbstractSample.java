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

package org.jdmp.core.sample;

import java.lang.reflect.Constructor;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractSample extends AbstractMapMatrix<String, Object> implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	public AbstractSample() {
		super();
		setId("Sample" + getCoreObjectId());
	}

	public boolean isCorrect() {
		return getTargetClass() == getRecognizedClass();
	}

	public int getTargetClass() {
		return (int) getAsMatrix(TARGET).toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public int getRecognizedClass() {
		return (int) getAsMatrix(PREDICTED).toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public abstract Sample clone();

	public void setLabel(Object label) {
		put(Sample.LABEL, label);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getClass().getSimpleName());
		s.append(" [ ");
		for (String key : keySet()) {
			Matrix v = getAsMatrix(key);
			s.append(key + "=");
			s.append(StringUtil.format(v));
			s.append(" ");
		}
		s.append("]");
		return s.toString();
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				Class<?> c = Class.forName("org.jdmp.gui.sample.SampleGUIObject");
				con = c.getConstructor(new Class<?>[] { Sample.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
	}

	public final Object getAsObject(String id) {
		return get(id);
	}

	public final Matrix getAsMatrix(String id) {
		return MathUtil.getMatrix(get(id));
	}

	public final double getAsDouble(String id) {
		return MathUtil.getDouble(get(id));
	}

	public final String getAsString(String id) {
		return StringUtil.getString(get(id));
	}

	public final int getAsInt(String id) {
		return MathUtil.getInt(get(id));
	}

	public final long getAsLong(String id) {
		return MathUtil.getLong(get(id));
	}

	public final short getAsShort(String id) {
		return MathUtil.getShort(get(id));
	}

	public final float getAsFloat(String id) {
		return MathUtil.getFloat(get(id));
	}

	public final byte getAsByte(String id) {
		return MathUtil.getByte(get(id));
	}

	public final char getAsChar(String id) {
		return MathUtil.getChar(get(id));
	}

	public final double getWeight() {
		double weight = getAsDouble(WEIGHT);
		return weight == 0 ? 1 : weight;
	}

}
