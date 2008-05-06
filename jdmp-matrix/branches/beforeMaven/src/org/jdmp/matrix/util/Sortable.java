package org.jdmp.matrix.util;

public class Sortable<C extends Comparable<C>, O> implements Comparable<Sortable<C, O>> {

	private C comparable = null;

	private O object = null;

	public Sortable(C comparable, O object) {
		this.comparable = comparable;
		this.object = object;
	}

	public C getComparable() {
		return comparable;
	}

	public void setComparable(C comparable) {
		this.comparable = comparable;
	}

	public O getObject() {
		return object;
	}

	public void setObject(O object) {
		this.object = object;
	}

	public String toString() {
		return "" + comparable + ": " + object;
	}

	public int compareTo(Sortable<C, O> s) {
		return comparable.compareTo(s.comparable);
	}
}
