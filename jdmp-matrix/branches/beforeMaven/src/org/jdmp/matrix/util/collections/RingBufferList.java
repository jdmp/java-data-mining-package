package org.jdmp.matrix.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RingBufferList<A> implements List<A> {

	private int start = -1;

	private int end = -1;

	private Object values[];

	public RingBufferList() {
		this(10);
	}

	public RingBufferList(int maximumSize) {
		values = new Object[maximumSize];
	}

	public int maxSize() {
		return values.length;
	}

	public boolean add(A a) {
		if (end >= 0) {
			end++;
			if (end >= values.length) {
				end = 0;
			}
			if (end == start) {
				start++;
			}
			if (start >= values.length) {
				start = 0;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[end] = a;
		return true;
	}

	public int size() {
		if (end < 0) {
			return 0;
		}
		return end < start ? values.length : end - start + 1;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			s.append(get(i));
			if (i < size() - 1) {
				s.append(", ");
			}
		}
		return s.toString();
	}

	public void addFirst(A a) {
		if (end >= 0) {
			start--;
			if (start < 0) {
				start = values.length - 1;
			}
			if (start == end) {
				end--;
			}
			if (end < 0) {
				end = values.length - 1;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[start] = a;
	}

	@SuppressWarnings("unchecked")
	public A get(int index) {
		return (A) values[(start + index) % values.length];
	}

	@SuppressWarnings("unchecked")
	public A set(int index, A a) {
		A old = (A) values[(start + index) % values.length];
		values[(start + index) % values.length] = a;
		return old;
	}

	public void clear() {
		start = -1;
		end = -1;
	}

	public void add(int index, A element) {
		new Exception("not implemented").printStackTrace();
	}

	public boolean addAll(Collection<? extends A> c) {
		for (A a : c) {
			add(a);
		}
		return false;
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public boolean contains(Object o) {
		for (int i = size(); --i >= 0;) {
			if (o.equals(get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public int indexOf(Object o) {
		for (int i = 0; i < size(); i++) {
			if (o.equals(get(i))) {
				return i;
			}
		}
		return -1;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterator<A> iterator() {
		return new RingBufferIterator();
	}

	private class RingBufferIterator implements Iterator<A> {

		int pos = 0;

		public RingBufferIterator() {
		}

		public boolean hasNext() {
			return pos < size();
		}

		public A next() {
			A a = get(pos);
			pos++;
			return a;
		}

		public void remove() {
			new Exception("not implemented").printStackTrace();
		}

	}

	public int lastIndexOf(Object o) {
		for (int i = size(); --i >= 0;) {
			if (o.equals(get(i))) {
				return i;
			}
		}
		return -1;
	}

	public ListIterator<A> listIterator() {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public ListIterator<A> listIterator(int index) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public boolean remove(Object o) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public A remove(int index) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public boolean removeAll(Collection<?> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public List<A> subList(int fromIndex, int toIndex) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public Object[] toArray() {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public <T> T[] toArray(T[] a) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

}