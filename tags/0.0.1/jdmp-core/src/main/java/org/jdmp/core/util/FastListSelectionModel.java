package org.jdmp.core.util;

import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FastListSelectionModel implements ListSelectionModel {

	private int selectionMode = SINGLE_INTERVAL_SELECTION;

	private int minIndex = -1;

	private int maxIndex = -1;

	private boolean valueIsAdjusting = false;

	public FastListSelectionModel() {
	}

	protected EventListenerList listenerList = new EventListenerList();

	public void addListSelectionListener(ListSelectionListener l) {
		listenerList.add(ListSelectionListener.class, l);
	}

	public void removeListSelectionListener(ListSelectionListener l) {
		listenerList.remove(ListSelectionListener.class, l);
	}

	public void addSelectionInterval(int index0, int index1) {
		if (index1 > index0) {
			minIndex = Math.min(minIndex, index0);
			maxIndex = Math.max(maxIndex, index1);
		} else {
			minIndex = Math.min(minIndex, index1);
			maxIndex = Math.max(maxIndex, index0);
		}
		fireValueChanged();
	}

	protected void fireValueChanged(int firstIndex, int lastIndex) {
		fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
	}

	protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
		Object[] listeners = listenerList.getListenerList();
		ListSelectionEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListSelectionListener.class) {
				if (e == null) {
					e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
				}
				((ListSelectionListener) listeners[i + 1]).valueChanged(e);
			}
		}
	}

	public void clearSelection() {
		minIndex = -1;
		maxIndex = -1;
		fireValueChanged();
	}

	private void fireValueChanged() {
		fireValueChanged(0, Integer.MAX_VALUE);
	}

	public int getMinSelectionIndex() {
		return minIndex;
	}

	public int getAnchorSelectionIndex() {
		return minIndex;
	}

	public int getLeadSelectionIndex() {
		return maxIndex;
	}

	public int getMaxSelectionIndex() {
		return maxIndex;
	}

	public int getSelectionMode() {
		return selectionMode;
	}

	public boolean getValueIsAdjusting() {
		return valueIsAdjusting;
	}

	public void insertIndexInterval(int index, int length, boolean before) {
		minIndex = Math.min(minIndex, index);
		maxIndex = Math.max(maxIndex, index + length);
		fireValueChanged();
	}

	public boolean isSelectedIndex(int index) {
		return index >= minIndex && index <= maxIndex;
	}

	public boolean isSelectionEmpty() {
		return minIndex == -1 || maxIndex == -1;
	}

	public void removeIndexInterval(int index0, int index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged();
	}

	public void removeSelectionInterval(int index0, int index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged();
	}

	public void setAnchorSelectionIndex(int index) {
		minIndex = Math.min(minIndex, index);
		fireValueChanged();
	}

	public void setLeadSelectionIndex(int index) {
		maxIndex = Math.max(maxIndex, index);
		fireValueChanged();
	}

	public void setSelectionInterval(int index0, int index1) {
		if (index1 > index0) {
			minIndex = index0;
			maxIndex = index1;
		} else {
			minIndex = index1;
			maxIndex = index0;
		}
		fireValueChanged();
	}

	public void setSelectionMode(int selectionMode) {
	}

	public void setValueIsAdjusting(boolean valueIsAdjusting) {
		this.valueIsAdjusting = valueIsAdjusting;
	}

}
