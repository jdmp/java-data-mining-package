package org.jdmp.core;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;

public abstract class AbstractCoreObject implements CoreObject {

	protected static final Logger logger = Logger.getLogger(AbstractCoreObject.class.getName());

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	public final void showGUI() {
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame", new Class[] { Object.class });
			method.invoke(null, new Object[] { this });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show frame", e);
		}
	}

	public final void notifyGUIObject() {
	}

	public abstract String toString();

}
