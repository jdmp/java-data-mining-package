package org.jdmp.ehcache;

import java.util.Map;

import org.ujmp.core.collections.AbstractMapTest;

public class TestEhcacheMap extends AbstractMapTest {

	@Override
	public Map createMap() throws Exception {
		return new EhcacheMap<String, String>();
	}

}
