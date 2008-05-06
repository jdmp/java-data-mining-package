package org.jdmp.matrix.util.collections;

import java.util.Map;

import org.jdmp.matrix.util.collections.SerializedObjectMap;

public class TestSerializedObjectMap extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new SerializedObjectMap<String, String>();
	}

}
