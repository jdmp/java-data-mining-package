package org.jdmp.matrix.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.interfaces.Wrapper;

public class CoordinateSetToLongWrapper implements Set<long[]>, Wrapper<Set<Coordinates>> {

	private Set<Coordinates> set = null;

	public CoordinateSetToLongWrapper(Set<Coordinates> set) {
		this.set = set;
	}

	public Set<Coordinates> getWrappedObject() {
		return set;
	}

	public void setWrappedObject(Set<Coordinates> object) {
		this.set = object;
	}

	public boolean add(long[] e) {
		return set.add(new Coordinates(e));
	}

	public boolean addAll(Collection<? extends long[]> c) {
		return false;
	}

	public void clear() {
		set.clear();
	}

	public boolean contains(Object o) {
		return set.contains(new Coordinates((long[]) o));
	}

	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<long[]> iterator() {
		return new LongIterator(this);
	}

	class LongIterator implements Iterator<long[]> {

		Iterator<Coordinates> it = null;

		public LongIterator(CoordinateSetToLongWrapper wrapper) {
			it = wrapper.set.iterator();
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public long[] next() {
			return it.next().dimensions;
		}

		public void remove() {
		}

	}

	public boolean remove(Object o) {
		return set.remove(new Coordinates((long[]) o));
	}

	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

}
