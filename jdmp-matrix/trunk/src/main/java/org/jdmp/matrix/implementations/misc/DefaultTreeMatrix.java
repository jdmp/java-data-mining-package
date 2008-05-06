package org.jdmp.matrix.implementations.misc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdmp.matrix.stubs.AbstractTreeMatrix;
import org.jdmp.matrix.util.collections.ArrayIndexList;

public class DefaultTreeMatrix extends AbstractTreeMatrix {
	private static final long serialVersionUID = -6752285310555819432L;

	private List<Object> objects = new ArrayIndexList<Object>();

	private Object root = null;

	private Map<Object, List<Object>> childrenMap = new HashMap<Object, List<Object>>();

	@Override
	public List<Object> getChildren(Object o) {
		List<Object> children = childrenMap.get(o);
		if (children == null) {
			children = new LinkedList<Object>();
			childrenMap.put(o, children);
		}
		return children;
	}

	public List<Object> getObjectList() {
		return objects;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public void setRoot(Object o) {
		this.root = o;
		if (!objects.contains(o)) {
			objects.add(o);
		}
		notifyGUIObject();
	}

}
