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

package org.jdmp.core;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.ujmp.core.Matrix;

public abstract class AbstractCoreObject implements CoreObject {

	protected static final Logger logger = Logger.getLogger(AbstractCoreObject.class.getName());

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	public final JFrame showGUI() {
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.JDMPFrameManager");
			Method method = c.getMethod("showFrame", new Class[] { Object.class });
			return (JFrame) method.invoke(null, new Object[] { this });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show frame", e);
			return null;
		}
	}

	@Override
	public abstract String toString();

}
