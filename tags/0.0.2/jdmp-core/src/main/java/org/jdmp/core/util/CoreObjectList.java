package org.jdmp.core.util;

import java.util.Collection;

import javax.swing.ListModel;

public interface CoreObjectList<V> extends ListModel, Iterable<V> {

	public int indexOf(V value);

	public V getElementAt(int index);

	public boolean isEmpty();

	public void clear();

	public Collection<V> toCollection();

}
