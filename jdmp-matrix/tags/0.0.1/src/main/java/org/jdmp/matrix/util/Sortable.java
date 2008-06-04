/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

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
