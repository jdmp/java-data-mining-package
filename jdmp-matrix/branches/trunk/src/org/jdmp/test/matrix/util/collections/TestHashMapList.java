package org.jdmp.test.matrix.util.collections;

import java.util.Map;

import org.jdmp.matrix.util.collections.HashMapList;

public class TestHashMapList extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new HashMapList<String, String>();
	}

}