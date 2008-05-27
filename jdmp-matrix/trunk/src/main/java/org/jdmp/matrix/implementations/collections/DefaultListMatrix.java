package org.jdmp.matrix.implementations.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.stubs.AbstractListMatrix;

public class DefaultListMatrix<A> extends AbstractListMatrix<A> {
	private static final long serialVersionUID = -6381864884046078055L;

	private List<A> list = null;

	public DefaultListMatrix(Collection<A> list) {
		if (list instanceof List) {
			this.list = (List<A>) list;
		} else {
			list = new ArrayList<A>(list);
		}
	}

	public DefaultListMatrix() {
		list = new ArrayList<A>();
	}

	public DefaultListMatrix(A... objects) {
		list = Arrays.asList(objects);
	}

	@Override
	public List<A> getList() {
		return list;
	}

}
