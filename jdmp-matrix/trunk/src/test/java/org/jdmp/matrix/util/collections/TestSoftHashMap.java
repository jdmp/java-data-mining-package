package org.jdmp.matrix.util.collections;

import java.util.Map;

import org.jdmp.matrix.util.collections.SoftHashMap;

public class TestSoftHashMap extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new SoftHashMap<String, String>();
	}

}
