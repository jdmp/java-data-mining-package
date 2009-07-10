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

package org.jdmp.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractCoreObject implements JDMPCoreObject {
	private static final long serialVersionUID = -4626483429334570721L;

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private transient GUIObject guiObject = null;

	public final JFrame showGUI() {
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.JDMPFrameManager");
			Method method = c.getMethod("showFrame", new Class[] { Object.class });
			return (JFrame) method.invoke(null, new Object[] { this });
		} catch (Exception e) {
			System.err.println("cannot show frame");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public abstract String toString();

	public final void notifyGUIObject() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public final GUIObject getGUIObject() {
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
				} else if (this instanceof DataSet) {
					Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
					con = c.getConstructor(new Class<?>[] { DataSet.class });
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

}
