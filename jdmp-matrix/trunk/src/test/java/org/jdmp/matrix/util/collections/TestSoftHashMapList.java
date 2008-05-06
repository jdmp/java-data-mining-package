package org.jdmp.matrix.util.collections;

import java.util.Map;

import org.jdmp.matrix.util.collections.SoftHashMapList;

public class TestSoftHashMapList extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new SoftHashMapList<String, String>();
	}

}