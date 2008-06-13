package org.jdmp.core.util;

import javax.swing.ListModel;

public interface CoreObjectList<V> extends ListModel, Iterable<V> {

	public int indexOf(V value);

	public V getElementAt(int index);

	public boolean isEmpty();

}
