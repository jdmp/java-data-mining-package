package org.jdmp.matrix.util.collections;

import java.io.Serializable;
import java.util.Map;

import junit.framework.TestCase;

import org.jdmp.matrix.util.SerializationUtil;

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

	public void testSerialize() throws Exception {
		Map m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			Map m2 = (Map) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}
	}

}
