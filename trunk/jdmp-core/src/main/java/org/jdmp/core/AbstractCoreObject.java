/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.jdmp.core;

import java.lang.reflect.Constructor;

import javax.swing.JFrame;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractCoreObject implements CoreObject {
	private static final long serialVersionUID = -4626483429334570721L;

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	protected transient GUIObject guiObject = null;

	private static long runningId = 0;

	private long coreObjectId = 0;

	static {
		try {
			runningId = 31 * System.nanoTime() + System.currentTimeMillis();
		} catch (Exception e) {
		}
	}

	public AbstractCoreObject() {
		coreObjectId = runningId++;
	}

	public final long getCoreObjectId() {
		return coreObjectId;
	}

	public final JFrame showGUI() {
		JFrame frame = getGUIObject().getFrame();
		frame.setVisible(true);
		return frame;
	}

	public abstract String toString();

	public final void fireValueChanged() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				if (this instanceof Module) {
					Class<?> c = Class.forName("org.jdmp.gui.module.ModuleGUIObject");
					con = c.getConstructor(new Class<?>[] { Module.class });
				} else if (this instanceof Variable) {
					Class<?> c = Class.forName("org.jdmp.gui.variable.VariableGUIObject");
					con = c.getConstructor(new Class<?>[] { Variable.class });
				} else if (this instanceof Sample) {
					Class<?> c = Class.forName("org.jdmp.gui.sample.SampleGUIObject");
					con = c.getConstructor(new Class<?>[] { Sample.class });
				} else if (this instanceof ListDataSet) {
					Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
					con = c.getConstructor(new Class<?>[] { ListDataSet.class });
				} else if (this instanceof Algorithm) {
					Class<?> c = Class.forName("org.jdmp.gui.algorithm.AlgorithmGUIObject");
					con = c.getConstructor(new Class<?>[] { Algorithm.class });
				} else {
					System.err.println("unknown object type: " + this.getClass());
				}
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
	}

	public CoreObject clone() {
		try {
			return (CoreObject) SerializationUtil.deserialize(SerializationUtil.serialize(this));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
