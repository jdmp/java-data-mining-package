package org.jdmp.matrix.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ArrayIndexList is like an ArrayList, but keeps track of the indices where
 * objects have been added. This improves the speed of indexOf() and contains()
 * 
 * @author Holger Arndt
 * 
 * @param <M>
 *            Type of the elements in the list
 */
public class ArrayIndexList<M> extends ArrayList<M> {
	private static final long serialVersionUID = 3657191905843442834L;

	private Map<M, Integer> indexMap = new HashMap<M, Integer>();

	@Override
	public void add(int index, M element) {
		new Exception("not implemented").printStackTrace();
	}

	@Override
	public boolean add(M e) {
		indexMap.put(e, size());
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends M> c) {
		for (M m : c) {
			add(m);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends M> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	@Override
	public void clear() {
		indexMap.clear();
		super.clear();
	}

	@Override
	public boolean contains(Object o) {
		return indexMap.containsKey(o);
	}

	@Override
	public int indexOf(Object o) {
		return indexMap.get(o);
	}

	@Override
	public int lastIndexOf(Object o) {

		return super.lastIndexOf(o);
	}

	@Override
	public M remove(int index) {
		M m = super.remove(index);
		indexMap.remove(m);
		return m;
	}

	@Override
	public boolean remove(Object o) {
		indexMap.remove(o);
		return super.remove(o);
	}

	@Override
	public M set(int index, M element) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

}
