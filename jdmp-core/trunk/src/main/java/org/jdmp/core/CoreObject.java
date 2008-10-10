package org.jdmp.core;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasGUIObject;
import org.ujmp.core.interfaces.HasLabel;

public interface CoreObject extends Serializable, Cloneable, HasGUIObject, HasLabel, HasDescription {

	public EventListenerList getListenerList();
	
	public void clear();

}
