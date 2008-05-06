package org.jdmp.matrix.implementations.misc;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdmp.matrix.stubs.AbstractTreeMatrix;
import org.jdmp.matrix.util.collections.ArrayIndexList;

public class ObjectTreeMatrix extends AbstractTreeMatrix {
	private static final long serialVersionUID = -7343649063964349539L;

	private List<Object> objects = new ArrayIndexList<Object>();

	private Object root = null;

	private Map<Object, List<Object>> childrenMap = new HashMap<Object, List<Object>>();

	public ObjectTreeMatrix(Object o) {
		root = new NameAndValue("Root", o);
		addSuperclass("Root", o);
		addFields("Root", o);
	}

	private void addSuperclass(String name, Object o) {
		Class superclass = o.getClass().getSuperclass();
		if (superclass != null && !Object.class.equals(superclass) && !Number.class.equals(superclass)) {

			addSuperclass("super", superclass);
			addFields("super", superclass);

			addChild(new NameAndValue(name, o), new NameAndValue("super", superclass));
		}
	}

	private void addFields(String name, Object o) {
		if (o == null) {
			return;
		}

		NameAndValue no = new NameAndValue(name, o);

		if (objects.contains(no)) {
			return;
		}

		objects.add(no);

		addSuperclass(name, o);

		if (o instanceof Long) {
			return;
		}
		if (o instanceof Integer) {
			return;
		}
		if (o instanceof String) {
			return;
		}
		if (o instanceof Boolean) {
			return;
		}
		if (o instanceof Float) {
			return;
		}
		if (o instanceof Short) {
			return;
		}
		if (o instanceof Byte) {
			return;
		}

		Field[] fields = o.getClass().getDeclaredFields();
		if (fields != null) {
			for (Field f : fields) {
				try {
					f.setAccessible(true);
					Object child = f.get(o);

					System.out.println(f.getName() + "=" + child);

					addFields(f.getName(), child);

					addChild(new NameAndValue(name, o), new NameAndValue(f.getName(), child));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Object> getChildren(Object o) {
		List<Object> children = childrenMap.get(o);
		if (children == null) {
			children = new LinkedList<Object>();
			childrenMap.put(o, children);
		}
		return children;
	}

	@Override
	public Collection<Object> getObjectList() {
		return objects;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public void setRoot(Object o) {
		root = o;
	}

}

class NameAndValue {

	private String name = null;

	private Object value = null;

	public NameAndValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String toString() {
		String s = name + " = " + value;
		if (s.length() > 50) {
			s = s.substring(0, 50) + "...";
		}
		return s;
	}

	public int hashCode() {
		return (name + " - " + value).hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof NameAndValue) {
			NameAndValue no = (NameAndValue) o;
			return (name + " - " + value).equals(no.name + " - " + no.value);
		} else {
			return false;
		}
	}

}
