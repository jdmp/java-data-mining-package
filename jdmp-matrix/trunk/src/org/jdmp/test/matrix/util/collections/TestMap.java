package org.jdmp.test.matrix.util.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.jdmp.matrix.util.collections.HashMapList;
import org.jdmp.matrix.util.collections.SerializedObjectMap;
import org.jdmp.matrix.util.collections.SoftHashMap;
import org.jdmp.matrix.util.collections.SoftHashMapList;

public class TestMap extends TestCase {

	private List<Class<? extends Map>> implementations = new ArrayList<Class<? extends Map>>();

	public void setUp() {
		implementations.add(SerializedObjectMap.class);
		implementations.add(SoftHashMap.class);
		implementations.add(SoftHashMapList.class);
		implementations.add(HashMapList.class);
		//implementations.add(EhcacheMap.class);
	}

	private Map getMap(Class<? extends Map> c) {
		Map m = null;
		try {
			m = c.newInstance();
		} catch (Exception e) {
			System.err.println("missing Constructor() for " + c.getSimpleName());
			e.printStackTrace();
		}
		return m;
	}

	public void testPutAndGet() {
		for (Class<? extends Map> c : implementations) {
			Map m = getMap(c);
			m.put("a", "test1");
			m.put("b", "test2");
			assertEquals(c.getSimpleName(), "test1", m.get("a"));
			assertEquals(c.getSimpleName(), "test2", m.get("b"));
		}
	}

}
