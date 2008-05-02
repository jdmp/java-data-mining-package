package org.jdmp.test.matrix.util.collections;

import java.util.Map;

import junit.framework.TestCase;

public abstract class AbstractMapTest extends TestCase {

	public abstract Map createMap() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testPutAndGet() throws Exception {
		Map m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		assertEquals(getLabel(), "test1", m.get("a"));
		assertEquals(getLabel(), "test2", m.get("b"));
	}

}
