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

	public final void notifyGUIObject() {
	}

	@Override
	public abstract String toString();

}
