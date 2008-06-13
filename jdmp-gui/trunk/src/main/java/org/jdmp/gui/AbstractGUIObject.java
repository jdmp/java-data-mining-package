package org.jdmp.gui;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractGUIObject implements GUIObject {
	private static final long serialVersionUID = -2271465024665498798L;

	protected static final Logger logger = Logger.getLogger(AbstractGUIObject.class.getName());

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private transient EventListenerList listenerList = null;

	private String label = "";

	private String description = "";

	public AbstractGUIObject() {
	}

	public AbstractGUIObject(String label) {
		this();
		setLabel(label);
	}

	public AbstractGUIObject(String label, String description) {
		this(label);
		setDescription(description);
	}

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

	public void fireValueChanged() {
	}

	public final void showGUI() {
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame", new Class[] { Object.class });
			method.invoke(null, new Object[] { this });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show frame", e);
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		if ("".equals(label))
			return getClass().getSimpleName();
		else
			return getClass().getSimpleName() + " [" + label + "]";
	}

	public String getToolTipText() {
		StringBuffer s = new StringBuffer();
		s.append("<html>");
		s.append("<table>");
		s.append("<tr>");
		s.append("<td colspan=2><h3>" + getClass().getSimpleName() + "</h3></td>");
		s.append("</tr>");
		s.append("<tr>");
		s.append("<td><b>Label:</b></td>");
		s.append("<td>" + getLabel() + "</td>");
		s.append("</tr>");
		s.append("<td><b>Description:</b></td>");
		s.append("<td>" + getDescription() + "</td>");
		s.append("</tr>");
		s.append("</table>");
		s.append("</html>");
		return s.toString();
	}

}
