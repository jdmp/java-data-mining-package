package org.jdmp.ehcache;

import java.util.Map;

import org.jdmp.matrix.util.collections.AbstractMapTest;

public class TestEhcacheMap extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new EhcacheMap<String, String>();
	}

}
