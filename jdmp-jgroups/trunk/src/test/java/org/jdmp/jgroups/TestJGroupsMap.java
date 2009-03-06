package org.jdmp.jgroups;

import java.util.Map;

import org.ujmp.core.collections.AbstractMapTest;

public class TestJGroupsMap extends AbstractMapTest {

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> createMap() throws Exception {
		return new JGroupsMap();
	}

}
