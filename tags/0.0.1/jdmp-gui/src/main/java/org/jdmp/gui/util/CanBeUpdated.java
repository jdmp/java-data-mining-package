package org.jdmp.gui.util;

public interface CanBeUpdated {

	public void addUpdateListener(UpdateListener l);

	public void removeUpdateListener(UpdateListener l);

	public void fireUpdated();

}
