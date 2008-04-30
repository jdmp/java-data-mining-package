package org.jdmp.matrix.stubs;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jdmp.matrix.interfaces.ListMatrix;

public abstract class AbstractListMatrix<A> extends AbstractDenseObjectMatrix2D<A> implements ListMatrix<A> {

	public abstract List<A> getList();

	public final long[] getSize() {
		return new long[] { size(), 1 };
	}

	public boolean add(A e) {
		boolean ret = getList().add(e);
		notifyGUIObject();
		return ret;
	}

	public void add(int index, A element) {
		getList().add(index, element);
		notifyGUIObject();
	}

	public boolean addAll(Collection<? extends A> c) {
		boolean ret = getList().addAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		boolean ret = getList().addAll(index, c);
		notifyGUIObject();
		return ret;
	}

	public boolean contains(Object o) {
		return getList().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getList().containsAll(c);
	}

	public A get(int index) {
		return (A) getList().get(index);
	}

	public int indexOf(Object o) {
		return getList().indexOf(o);
	}

	public boolean isEmpty() {
		return getList().isEmpty();
	}

	public Iterator<A> iterator() {
		return getList().iterator();
	}

	public int lastIndexOf(Object o) {
		return getList().lastIndexOf(o);
	}

	public ListIterator<A> listIterator() {
		return getList().listIterator();
	}

	public ListIterator<A> listIterator(int index) {
		return getList().listIterator();
	}

	public boolean remove(Object o) {
		boolean ret = getList().remove(o);
		notifyGUIObject();
		return ret;
	}

	public A remove(int index) {
		A a = getList().remove(index);
		notifyGUIObject();
		return a;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = getList().removeAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = getList().retainAll(c);
		notifyGUIObject();
		return ret;
	}

	public A set(int index, A element) {
		A a = getList().set(index, element);
		notifyGUIObject();
		return a;
	}

	public int size() {
		return getList().size();
	}

	public List<A> subList(int fromIndex, int toIndex) {
		return getList().subList(fromIndex, toIndex);
	}

	public A getObject(long... coordinates) {
		A a = getList().get((int) coordinates[ROW]);
		return a;
	}

	@SuppressWarnings("unchecked")
	public void setObject(Object value, long... coordinates) {
		getList().set((int) coordinates[ROW], (A) value);
		notifyGUIObject();
	}

	public Object[] toArray() {
		return getList().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return null;
	}

}
