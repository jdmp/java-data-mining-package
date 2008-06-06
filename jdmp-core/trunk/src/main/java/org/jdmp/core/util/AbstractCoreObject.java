package org.jdmp.core.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.jdmp.matrix.Matrix;

public abstract class AbstractCoreObject implements CoreObject {

	protected static final Logger logger = Logger.getLogger(AbstractCoreObject.class.getName());

	public static final int X = Matrix.X;

	public static final int Y = Matrix.Y;

	public static final int Z = Matrix.Z;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private transient PropertyChangeSupport changeSupport = null;

	private transient EventListenerList listenerList = null;

	private String label = "";

	private String description = "";

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		String oldDescription = this.description;
		this.description = description;
		getProptertyChangeSupport().firePropertyChange("Description", oldDescription, description);
	}

	public final void addPropertyChangeListener(PropertyChangeListener l) {
		getProptertyChangeSupport().addPropertyChangeListener(l);
	}

	public final void removePropertyChangeListener(PropertyChangeListener l) {
		getProptertyChangeSupport().removePropertyChangeListener(l);
	}

	public final PropertyChangeSupport getProptertyChangeSupport() {
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		return changeSupport;
	}

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

	public final String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		String oldLabel = this.label;
		this.label = label;
		getProptertyChangeSupport().firePropertyChange("Label", oldLabel, label);
	}

	public final String getToolTipText() {
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
		s.append("<tr>");
		s.append("<td><b>Short Status:</b></td>");
		s.append("<td>" + getShortStatus() + "</td>");
		s.append("</tr>");
		s.append("<tr>");
		s.append("<td><b>Long Status:</b></td>");
		s.append("<td>" + getLongStatus() + "</td>");
		s.append("</tr>");
		s.append("</table>");
		s.append("</html>");
		return s.toString();
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

	public final boolean isInListenerList(Object listener) {
		for (Object o : getListenerList().getListenerList()) {
			if (o.equals(listener)) {
				return true;
			}
		}
		return false;
	}

	public void notifyGUIObject() {
	}

}
