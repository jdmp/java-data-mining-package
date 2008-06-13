package org.jdmp.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.jdmp.matrix.interfaces.Disposable;
import org.jdmp.matrix.interfaces.HasDescription;
import org.jdmp.matrix.interfaces.HasGUIObject;
import org.jdmp.matrix.interfaces.HasLabel;

public interface CoreObject extends Serializable, Disposable, Cloneable, HasGUIObject, HasLabel,
		HasDescription {

	public EventListenerList getListenerList();

	public void addPropertyChangeListener(PropertyChangeListener l);

	public void removePropertyChangeListener(PropertyChangeListener l);

	public PropertyChangeSupport getProptertyChangeSupport();

	public void fireValueChanged();

}
